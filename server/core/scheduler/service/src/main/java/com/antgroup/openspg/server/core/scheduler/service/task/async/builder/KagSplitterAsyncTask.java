/*
 * Copyright 2023 OpenSPG Authors
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied.
 */
package com.antgroup.openspg.server.core.scheduler.service.task.async.builder;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.antgroup.openspg.builder.model.record.ChunkRecord;
import com.antgroup.openspg.cloudext.interfaces.objectstorage.ObjectStorageClient;
import com.antgroup.openspg.cloudext.interfaces.objectstorage.ObjectStorageClientDriverManager;
import com.antgroup.openspg.common.constants.BuilderConstant;
import com.antgroup.openspg.common.util.CommonUtils;
import com.antgroup.openspg.common.util.StringUtils;
import com.antgroup.openspg.common.util.pemja.PemjaUtils;
import com.antgroup.openspg.common.util.pemja.model.PemjaConfig;
import com.antgroup.openspg.server.common.model.bulider.BuilderJob;
import com.antgroup.openspg.server.common.model.project.Project;
import com.antgroup.openspg.server.common.model.scheduler.SchedulerEnum;
import com.antgroup.openspg.server.common.service.builder.BuilderJobService;
import com.antgroup.openspg.server.common.service.config.DefaultValue;
import com.antgroup.openspg.server.common.service.project.ProjectService;
import com.antgroup.openspg.server.core.scheduler.model.service.SchedulerInstance;
import com.antgroup.openspg.server.core.scheduler.model.service.SchedulerJob;
import com.antgroup.openspg.server.core.scheduler.model.service.SchedulerTask;
import com.antgroup.openspg.server.core.scheduler.model.task.TaskExecuteContext;
import com.antgroup.openspg.server.core.scheduler.model.task.TaskExecuteDag;
import com.antgroup.openspg.server.core.scheduler.service.common.MemoryTaskServer;
import com.antgroup.openspg.server.core.scheduler.service.metadata.SchedulerTaskService;
import com.antgroup.openspg.server.core.scheduler.service.task.async.AsyncTaskExecuteTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component("kagSplitterAsyncTask")
public class KagSplitterAsyncTask extends AsyncTaskExecuteTemplate {

  @Autowired private DefaultValue value;

  @Autowired private BuilderJobService builderJobService;

  @Autowired private MemoryTaskServer memoryTaskServer;

  @Autowired private SchedulerTaskService taskService;

  @Autowired private ProjectService projectService;

  @Override
  public String submit(TaskExecuteContext context) {
    SchedulerInstance instance = context.getInstance();
    SchedulerTask task = context.getTask();
    String key =
        CommonUtils.getTaskStorageFileKey(
            task.getProjectId(), task.getInstanceId(), task.getId(), task.getType());
    SchedulerTask memoryTask = memoryTaskServer.getTask(key);
    if (memoryTask != null) {
      context.addTraceLog("Splitter task already exists; reuse it");
      return memoryTask.getNodeId();
    }

    List<TaskExecuteDag.Node> nodes =
        instance.getTaskDag().getRelatedNodes(task.getNodeId(), false);
    List<String> inputs = Lists.newArrayList();
    nodes.forEach(
        node -> {
          SchedulerTask preTask =
              taskService.queryByInstanceIdAndNodeId(task.getInstanceId(), node.getId());
          if (preTask != null && StringUtils.isNotBlank(preTask.getOutput())) {
            inputs.add(preTask.getOutput());
          }
        });
    String taskId =
        memoryTaskServer.submit(
            new SplitterTaskCallable(value, builderJobService, projectService, context, inputs),
            key);
    return taskId;
  }

  @Override
  public SchedulerEnum.TaskStatus getStatus(TaskExecuteContext context, String resource) {
    SchedulerTask task = memoryTaskServer.getTask(resource);
    SchedulerTask schedulerTask = context.getTask();
    if (task == null) {
      context.addTraceLog("Splitter task(%s) not found, resubmit", resource);
      submit(context);
      context.addTraceLog("Async task resubmit successful!");
      return SchedulerEnum.TaskStatus.RUNNING;
    }
    context.addTraceLog("Splitter task status is %s", task.getStatus());
    if (StringUtils.isNotBlank(task.getTraceLog())) {
      context.addTraceLog(
          "Splitter task traceLog:%s%s", System.getProperty("line.separator"), task.getTraceLog());
      task.setTraceLog("");
    }
    switch (task.getStatus()) {
      case RUNNING:
        break;
      case ERROR:
        int retryNum = 3;
        if (schedulerTask.getExecuteNum() % retryNum == 0) {
          context.addTraceLog("Splitter task(%s) status is ERROR, resubmit", resource);
          memoryTaskServer.stopTask(resource);
          submit(context);
          context.addTraceLog("Async task resubmit successful!");
          return SchedulerEnum.TaskStatus.RUNNING;
        }
        break;
      case FINISH:
        String fileKey =
            CommonUtils.getTaskStorageFileKey(
                schedulerTask.getProjectId(),
                schedulerTask.getInstanceId(),
                schedulerTask.getId(),
                schedulerTask.getType());
        memoryTaskServer.stopTask(resource);
        schedulerTask.setOutput(fileKey);
        break;
      default:
        context.addTraceLog("Splitter Task Status is %s. Do nothing", task.getStatus());
        break;
    }
    return task.getStatus();
  }

  @Override
  public Boolean stop(TaskExecuteContext context, String resource) {
    return memoryTaskServer.stopTask(resource);
  }

  private static class SplitterTaskCallable extends MemoryTaskServer.MemoryTaskCallable<String> {

    private DefaultValue value;

    private BuilderJobService builderJobService;

    private ProjectService projectService;

    private ObjectStorageClient objectStorageClient;

    private TaskExecuteContext context;

    private List<String> inputs;

    public SplitterTaskCallable(
        DefaultValue value,
        BuilderJobService builderJobService,
        ProjectService projectService,
        TaskExecuteContext context,
        List<String> inputs) {
      this.value = value;
      this.builderJobService = builderJobService;
      this.projectService = projectService;
      this.objectStorageClient =
          ObjectStorageClientDriverManager.getClient(value.getObjectStorageUrl());
      this.context = context;
      this.inputs = inputs;
    }

    @Override
    public String call() throws Exception {
      List<ChunkRecord.Chunk> chunkList = Lists.newArrayList();
      addTraceLog("Start split document...");
      for (String input : inputs) {
        String data = objectStorageClient.getString(value.getBuilderBucketName(), input);
        List<ChunkRecord.Chunk> chunks =
            JSON.parseObject(data, new TypeReference<List<ChunkRecord.Chunk>>() {});
        chunkList.addAll(splitterChunk(context, chunks));
      }
      addTraceLog("Split document complete. number of paragraphs:%s", chunkList.size());
      SchedulerTask task = context.getTask();
      String fileKey =
          CommonUtils.getTaskStorageFileKey(
              task.getProjectId(), task.getInstanceId(), task.getId(), task.getType());
      objectStorageClient.saveString(
          value.getBuilderBucketName(), JSON.toJSONString(chunkList), fileKey);
      addTraceLog(
          "split result is stored bucket:%s file:%s", value.getBuilderBucketName(), fileKey);
      return fileKey;
    }

    public List<ChunkRecord.Chunk> splitterChunk(
        TaskExecuteContext context, List<ChunkRecord.Chunk> chunks) {
      List<ChunkRecord.Chunk> chunkList = Lists.newArrayList();
      JSONObject pyConfig = new JSONObject();
      Project project = projectService.queryById(context.getInstance().getProjectId());
      SchedulerJob job = context.getJob();
      BuilderJob builderJob = builderJobService.getById(Long.valueOf(job.getInvokerId()));
      JSONObject extension = JSON.parseObject(builderJob.getExtension());
      PemjaConfig pemjaConfig =
          com.antgroup.openspg.builder.core.physical.utils.CommonUtils.getSplitterConfig(
              pyConfig,
              value.getPythonExec(),
              value.getPythonPaths(),
              value.getSchemaUrlHost(),
              project,
              extension);
      addTraceLog("invoke split operator:%s", pemjaConfig.getClassName());
      for (ChunkRecord.Chunk chunk : chunks) {
        addTraceLog("invoke split chunk:%s", chunk.getName());
        Map map = new ObjectMapper().convertValue(chunk, Map.class);
        List<Object> result =
            (List<Object>)
                PemjaUtils.invoke(
                    pemjaConfig, BuilderConstant.SPLITTER_ABC, pyConfig.toJSONString(), map);
        List<ChunkRecord.Chunk> datas =
            JSON.parseObject(
                JSON.toJSONString(result), new TypeReference<List<ChunkRecord.Chunk>>() {});
        chunkList.addAll(datas);
        addTraceLog("invoke split chunk:%s size:%s succeed", chunk.getName(), chunkList.size());
      }
      addTraceLog("invoke split operator:%s succeed", pemjaConfig.getClassName());

      return chunkList;
    }
  }
}
