/*
 *      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
 *
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are met:
 *
 *  Redistributions of source code must retain the above copyright notice,
 *  this list of conditions and the following disclaimer.
 *  Redistributions in binary form must reproduce the above copyright
 *  notice, this list of conditions and the following disclaimer in the
 *  documentation and/or other materials provided with the distribution.
 *  Neither the name of the dreamlu.net developer nor the names of its
 *  contributors may be used to endorse or promote products derived from
 *  this software without specific prior written permission.
 *  Author: Chill 庄骞 (smallchill@163.com)
 */
package org.springblade.common.cache;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import org.springblade.common.constant.CommonConstant;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.modules.system.entity.*;
import org.springblade.modules.system.service.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 系统缓存
 *
 * @author Chill
 */
public class SysCache {
    private static final String DEPT_ID = "dept:id:";
    private static final String DEPT_NAME = "dept:name:";
    private static final String DEPT_NAME_ID = "deptName:id:";
    private static final String DEPT_NAMES_ID = "deptNames:id:";
    private static final String DEPT_CHILD_ID = "deptChild:id:";
    private static final String DEPT_CHILDIDS_ID = "deptChildIds:id:";
    private static final String ROLE_ID = "role:id:";
    private static final String ROLE_NAME = "role:name:";
    private static final String ROLE_NAME_ID = "roleName:id:";
    private static final String ROLE_NAMES_ID = "roleNames:id:";
    private static final String ROLE_ALIAS_ID = "roleAlias:id:";
    private static final String ROLE_ALIASES_ID = "roleAliases:id:";
    private static final String MENU_ID = "menu:id:";

    private static final IAuthClientService AUTH_CLIENT_SERVICE;
    private static final IDeptService deptService;
    private static final IRoleService roleService;
    private static final IUserService userService;
    private static final IMenuService menuService;


    static {
        AUTH_CLIENT_SERVICE = SpringUtil.getBean(IAuthClientService.class);
        deptService = SpringUtil.getBean(IDeptService.class);
        roleService = SpringUtil.getBean(IRoleService.class);
        userService = SpringUtil.getBean(IUserService.class);
        menuService = SpringUtil.getBean(IMenuService.class);
    }

    public static AuthClient getClient(String clientId) {
        return AUTH_CLIENT_SERVICE.getOne(Wrappers.<AuthClient>query().lambda().eq(AuthClient::getClientId, clientId).eq(AuthClient::getStatus, CommonConstant.STATUS_ENABLE));
    }

    /**
     * 获取部门
     *
     * @param id 主键
     */
    public static Dept getDept(Long id) {
        return CacheUtil.get(SYS_CACHE, DEPT_ID, id, () -> deptService.getById(id));
    }


    /**
     * 获取部门id
     *
     * @param deptNames 部门名
     */
    public static String getDeptId(String deptNames) {
        return CacheUtil.get(SYS_CACHE, DEPT_NAME, StringPool.DASH + deptNames, () -> deptService.getDeptId(deptNames));
    }


    /**
     * 获取部门名
     *
     * @param deptId 主键
     * @return 部门名
     */
    public static String getDeptName(String deptId) {
        return CacheUtil.get(SYS_CACHE, DEPT_NAME_ID, deptId, () -> deptService.getById(deptId).getDeptName());
    }

    /**
     * 获取子部门集合
     *
     * @param deptId 主键
     * @return 子部门
     */
    public static List<Dept> getDeptChild(Long deptId) {
        return CacheUtil.get(SYS_CACHE, DEPT_CHILD_ID, deptId, () -> deptService.getDeptChild(deptId));
    }

    /**
     * 获取子部门ID集合
     *
     * @param deptId 主键
     * @return 子部门ID
     */
    public static List<Long> getDeptChildIds(Long deptId) {
        if (deptId == null) {
            return null;
        }
        List<Long> deptIdList = CacheUtil.get(SYS_CACHE, DEPT_CHILDIDS_ID, deptId, List.class);
        if (deptIdList == null) {
            deptIdList = new ArrayList<>();
            List<Dept> deptChild = getDeptChild(deptId);
            if (deptChild != null) {
                List<Long> collect = deptChild.stream().map(Dept::getId).collect(Collectors.toList());
                deptIdList.addAll(collect);
            }
            deptIdList.add(deptId);
            CacheUtil.put(SYS_CACHE, DEPT_CHILDIDS_ID, deptId, deptIdList);
        }
        return deptIdList;
    }

    /**
     * 获取菜单
     *
     * @param id 主键
     * @return
     */
    public static Menu getMenu(Long id) {
        return CacheUtil.get(SYS_CACHE, MENU_ID, id, () -> menuService.getById(id));
    }

    /**
     * 获取角色
     *
     * @param id 主键
     * @return Role
     */
    public static Role getRole(Long id) {
        return CacheUtil.get(SYS_CACHE, ROLE_ID, id, () -> roleService.getById(id));
    }

    /**
     * 获取角色id
     *
     * @param roleNames 角色名
     */
    public static String getRoleIds(String roleNames) {
        return CacheUtil.get(SYS_CACHE, ROLE_NAME, StringPool.DASH + roleNames, () -> roleService.getRoleIds(roleNames));
    }

    /**
     * 获取角色名
     *
     * @param id 主键
     * @return 角色名
     */
    public static String getRoleName(Long id) {
        return CacheUtil.get(SYS_CACHE, ROLE_NAME_ID, id, () -> roleService.getById(id).getRoleName());
    }

    /**
     * 获取角色名集合
     *
     * @param roleIds 主键集合
     * @return 角色名
     */
    public static List<String> getRoleNames(String roleIds) {
        return CacheUtil.get(SYS_CACHE, ROLE_NAMES_ID, roleIds, () -> roleService.getRoleNames(roleIds));
    }


    /**
     * 获取角色别名
     *
     * @param id 主键
     * @return 角色别名
     */
    public static String getRoleAlias(Long id) {
        return CacheUtil.get(SYS_CACHE, ROLE_ALIAS_ID, id, () -> roleService.getById(id).getRoleAlias());
    }

    /**
     * 获取角色别名集合
     *
     * @param roleIds 主键集合
     * @return 角色别名
     */
    public static List<String> getRoleAliases(String roleIds) {
        return CacheUtil.get(SYS_CACHE, ROLE_ALIASES_ID, roleIds, () -> roleService.getRoleAliases(roleIds));
    }
}
