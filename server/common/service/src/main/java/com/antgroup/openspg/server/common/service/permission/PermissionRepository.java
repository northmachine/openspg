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

package com.antgroup.openspg.server.common.service.permission;

import com.antgroup.openspg.server.api.facade.Paged;
import com.antgroup.openspg.server.common.model.permission.Permission;
import java.util.List;

/** permission repository, contain PROJECT、SCHEMA、INDEX */
public interface PermissionRepository {

  /**
   * create permission
   *
   * @param permission
   * @return
   */
  Integer save(Permission permission);

  /**
   * update permission
   *
   * @param permission
   * @return
   */
  Integer update(Permission permission);

  /**
   * query all permission by resourceId
   *
   * @param resourceId
   * @param resourceTag
   * @param page
   * @param pageSize
   * @return
   */
  List<Permission> query(Long resourceId, String resourceTag, Integer page, Integer pageSize);

  /**
   * query by roleId nad the part of userNo
   *
   * @param userNo
   * @param roleId
   * @param resourceId
   * @param resourceTag
   * @param page
   * @param pageSize
   * @return
   */
  List<Permission> queryByUserNoAndRoleId(
      String userNo,
      Long roleId,
      Long resourceId,
      String resourceTag,
      Integer page,
      Integer pageSize);

  /**
   * query page
   *
   * @param userNo
   * @param roleId
   * @param resourceId
   * @param resourceTag
   * @param page
   * @param pageSize
   * @return
   */
  Paged<Permission> queryPage(
      String userNo,
      Long roleId,
      Long resourceId,
      String resourceTag,
      Integer page,
      Integer pageSize);

  /**
   * delete permission
   *
   * @param permission
   * @return
   */
  Integer delete(Permission permission);

  /**
   * get by resourceIds and resourceTag
   *
   * @param resourceIds
   * @param resourceTag
   * @return
   */
  List<Permission> selectByResourceIdsAndResourceTag(List<Long> resourceIds, String resourceTag);

  /**
   * get by userRoles and resourceIds
   *
   * @param resourceIds
   * @param userNo
   * @param roleType
   * @param resourceTag
   * @return
   */
  List<Permission> getPermissionByUserRolesAndId(
      List<Long> resourceIds, String userNo, String roleType, String resourceTag);

  /**
   * get by userNo and resourceTag
   *
   * @param userNo
   * @param resourceTag
   * @return
   */
  List<Permission> getPermissionByUserNoAndResourceTag(String userNo, String resourceTag);

  /**
   * get by id
   *
   * @param id
   * @return
   */
  Permission selectByPrimaryKey(Long id);
}
