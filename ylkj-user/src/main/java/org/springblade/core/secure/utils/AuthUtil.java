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
package org.springblade.core.secure.utils;

import org.springblade.common.cache.CacheNames;
import org.springblade.common.cache.SysCache;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.core.tool.utils.StringPool;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.modules.system.entity.AuthClient;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Auth工具类
 *
 * @author Chill
 */

public class AuthUtil {

	private static final BladeRedis bladeRedis;

	static {
		bladeRedis = SpringUtil.getBean(BladeRedis.class);
	}

	private static final String YL_USER_REQUEST_ATTR = "_YL_USER_REQUEST_ATTR_";

	private final static String TOKEN_REQUEST_NAME = "access_token";
	private final static String CLIENT_ID_REQUEST_NAME = "client_id";
	private final static String CLIENT_SECRET_REQUEST_NAME = "client_secret";

	/**
	 * 获取用户信息
	 *
	 * @return BladeUser
	 */
	public static BladeUser getUser() {
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		// 优先从 request 中获取
		Object bladeUser = request.getAttribute(YL_USER_REQUEST_ATTR);
		if (bladeUser == null) {
			bladeUser = getUser(request);
			if (bladeUser != null) {
				request.setAttribute(YL_USER_REQUEST_ATTR, bladeUser);
			}
		}
		return (BladeUser) bladeUser;
	}

	/**
	 * 获取用户信息
	 *
	 * @param request request
	 * @return BladeUser
	 */
	public static BladeUser getUser(HttpServletRequest request) {
		String token = request.getHeader(AuthUtil.TOKEN_REQUEST_NAME);
		if (token == null) {
			token = request.getParameter(AuthUtil.TOKEN_REQUEST_NAME);
		}
		return BladeUser.parse(bladeRedis.get(CacheNames.TOKEN_CACHE_KEY + token));
	}

	public static AuthClient getClientByID(){
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		String clientId = request.getHeader(AuthUtil.CLIENT_ID_REQUEST_NAME);
		if (clientId == null) {
			clientId = request.getParameter(AuthUtil.CLIENT_ID_REQUEST_NAME);
		}
		return SysCache.getClient(clientId);
	}

	public static AuthClient getClient(){
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		String clientId = request.getHeader(AuthUtil.CLIENT_ID_REQUEST_NAME);
		if (clientId == null) {
			clientId = request.getParameter(AuthUtil.CLIENT_ID_REQUEST_NAME);
		}
		AuthClient client = SysCache.getClient(clientId);
		if (client == null) return null;
		String clientSecret = request.getHeader(AuthUtil.CLIENT_SECRET_REQUEST_NAME);
		if (clientSecret == null) {
			clientSecret = request.getParameter(AuthUtil.CLIENT_SECRET_REQUEST_NAME);
		}
		if (!client.getClientSecret().equalsIgnoreCase(clientSecret)){
			return null;
		}
		return client;
	}

	public static String getToken(){
		HttpServletRequest request = WebUtil.getRequest();
		if (request == null) {
			return null;
		}
		String token = request.getHeader(AuthUtil.TOKEN_REQUEST_NAME);
		if (token == null) {
			token = request.getParameter(AuthUtil.TOKEN_REQUEST_NAME);
		}
		return token;
	}

	/**
	 * 是否为超管
	 *
	 * @return boolean
	 */
	public static boolean isAdministrator() {
		return StringUtil.containsAny(getUserRole(), RoleConstant.ADMINISTRATOR);
	}
	/**
	 * 获取用户id
	 *
	 * @return userId
	 */
	public static Long getUserId() {
		BladeUser user = getUser();
		return (null == user) ? -1 : user.getUserId();
	}

	/**
	 * 获取用户部门
	 *
	 * @return userName
	 */
	public static String getDeptId() {
		BladeUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getDeptId().toString();
	}

	/**
	 * 获取用户角色
	 *
	 * @return userName
	 */
	public static String getUserRole() {
		BladeUser user = getUser();
		return (null == user) ? StringPool.EMPTY : user.getRoleAlias();
	}
	public static List<Long> getRoleIds(){
		return getUser().getRoleIds();
	}

	public static String getUserAccount(HttpServletRequest request) {
		BladeUser user = getUser(request);
		return (null == user) ? StringPool.EMPTY : user.getName();
	}

	public static String getTenantId(){
		return StringPool.EMPTY;
	}

}
