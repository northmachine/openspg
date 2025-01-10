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

package com.antgroup.openspg.server.infra.dao.mapper;

import com.antgroup.openspg.server.infra.dao.dataobject.PermissionDO;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * Permission mapper
 *
 * @author nanmu
 */
@Mapper
public interface PermissionMapper {
  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * kg_resource_permission
   *
   * @param record com.antgroup.openspg.server.infra.dao.dataobject.PermissionDO
   * @return int
   */
  int insert(PermissionDO record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * kg_resource_permission
   *
   * @param id java.lang.Long
   * @return int
   */
  int deleteByPrimaryKey(Long id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * kg_resource_permission
   *
   * @param ids List<Long>
   * @return int
   */
  int deleteByIds(@Param("ids") List<Long> ids);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * kg_resource_permission
   *
   * @param record com.antgroup.openspg.server.infra.dao.dataobject.PermissionDO
   * @return int
   */
  int updateByPrimaryKeySelective(PermissionDO record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * kg_resource_permission
   *
   * @param id java.lang.Long
   * @return com.antgroup.openspg.server.infra.dao.dataobject.PermissionDO
   */
  PermissionDO selectByPrimaryKey(Long id);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * kg_resource_permission
   *
   * @param record com.antgroup.openspg.server.infra.dao.dataobject.PermissionDO
   * @param start int
   * @param size int
   * @return List<KgUserResourceRolePO>
   */
  List<PermissionDO> selectByCondition(
      @Param("record") PermissionDO record, @Param("start") int start, @Param("size") int size);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * kg_resource_permission
   *
   * @param record com.antgroup.openspg.server.infra.dao.dataobject.PermissionDO
   * @return int
   */
  int selectCountByCondition(PermissionDO record);

  /**
   * This method was generated by MyBatis Generator. This method corresponds to the database table
   * kg_resource_permission
   *
   * @param ids List<Long>
   * @return List<KgUserResourceRolePO>
   */
  List<PermissionDO> selectByIds(@Param("ids") List<Long> ids);

  /**
   * Batch Insert Data
   *
   * @param records
   * @return
   */
  int batchInsert(@Param("records") List<PermissionDO> records);

  /**
   * update KgUserResourceRole
   *
   * @param record
   * @return
   */
  int updateResourceRole(@Param("record") PermissionDO record);

  /**
   * delete by resourceId and userNo
   *
   * @param record
   * @return
   */
  int deleteByResourceIdAndUserNo(@Param("record") PermissionDO record);

  /**
   * select all by condition
   *
   * @param record
   * @return
   */
  List<PermissionDO> selectAllByCondition(@Param("record") PermissionDO record);

  /**
   * select by userNo and resourceId and resourceTag for update
   *
   * @param userNo
   * @param resourceId
   * @param resourceTag
   * @return
   */
  PermissionDO selectUserTypeRoleForUpdate(
      @Param("userNo") String userNo,
      @Param("resourceId") Long resourceId,
      @Param("resourceTag") String resourceTag);

  /**
   * Query Records with Specified Permissions
   *
   * @param userId
   * @param roleIds
   * @param resourceTag
   * @return
   */
  List<PermissionDO> selectByRoleId(
      @Param("userId") String userId,
      @Param("roleIds") List<Long> roleIds,
      @Param("resourceTag") String resourceTag);

  /**
   * Delete Records Corresponding to the Resource
   *
   * @param resourceId
   * @param resourceTag
   * @return
   */
  int deleteByResourceId(
      @Param("resourceId") Long resourceId, @Param("resourceTag") String resourceTag);

  int deletePermission(PermissionDO record);

  /**
   * Batch Query Asset Authorization Information
   *
   * @param resourceIds
   * @param resourceTag
   * @return
   */
  List<PermissionDO> selectByResources(
      @Param("resourceIds") List<Long> resourceIds, @Param("resourceTag") String resourceTag);

  /**
   * select by userNo and resourceTag and roleIds
   *
   * @param userNo
   * @param roleName
   * @return
   */
  List<PermissionDO> getResourceIdByUserNoAndRoleName(
      @Param("userNo") String userNo,
      @Param("resourceTag") String resourceTag,
      @Param("roleName") String roleName);

  /**
   * select by userNo and resourceTag and roleIds
   *
   * @param resourceIds
   * @return
   */
  List<PermissionDO> getByResourceIds(
      @Param("resourceIds") List<Long> resourceIds, @Param("roleId") Long roleId);

  /**
   * delete by resourceIds
   *
   * @param resourceIds
   * @return
   */
  int deleteByResourceIds(@Param("resourceIds") List<Long> resourceIds);

  /**
   * select page
   *
   * @param userNo
   * @param roleId
   * @param resourceTag
   * @return
   */
  List<PermissionDO> selectLikeByUserNoAndRoleId(
      @Param("userNo") String userNo,
      @Param("roleId") Long roleId,
      @Param("resourceId") Long resourceId,
      @Param("resourceTag") String resourceTag,
      @Param("start") int start,
      @Param("size") int size);

  /**
   * the count of selectLikeByUserNoAndRoleId
   *
   * @param userNo
   * @param roleId
   * @param resourceId
   * @param resourceTag
   * @return
   */
  Integer selectLikeCountByUserNoAndRoleId(
      @Param("userNo") String userNo,
      @Param("roleId") Long roleId,
      @Param("resourceId") Long resourceId,
      @Param("resourceTag") String resourceTag);

  /**
   * get by resourceIds and resourceTag
   *
   * @param resourceIds
   * @param resourceTag
   * @return
   */
  List<PermissionDO> selectByResourceIdsAndResourceTag(
      @Param("resourceIds") List<Long> resourceIds, @Param("resourceTag") String resourceTag);

  /**
   * get user has permission permission
   *
   * @param resourceIds
   * @param userNo
   * @param roleId
   * @param resourceTag
   * @return
   */
  List<PermissionDO> getPermissionByUserRolesAndId(
      @Param("resourceIds") List<Long> resourceIds,
      @Param("userNo") String userNo,
      @Param("roleId") Long roleId,
      @Param("resourceTag") String resourceTag);

  /**
   * get by userNo and resourceTag
   *
   * @param userNo
   * @param resourceTag
   * @return
   */
  List<PermissionDO> getPermissionByUserNoAndResourceTag(
      @Param("userNo") String userNo, @Param("resourceTag") String resourceTag);
}