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
package org.springblade.common.constant;

/**
 * 通用常量
 *
 * @author Chill
 */
public interface CommonConstant {

	String APPLICATION_SYSTEM_NAME = "lykj-user";
	/**
	 * 顶级父节点id
	 */
	Long TOP_PARENT_ID = 0L;
	/**
	 * 默认密码
	 */
	String DEFAULT_PASSWORD = "sjgl123456";

	/**
	 * 正常状态
	 */
	Integer STATUS_ENABLE = 0;

	/**
	 * 禁用状态
	 */
	Integer STATUS_DISABLED = 1;

	/**
	 * 默认角色 普通角色
	 */
	String DEFAULT_ROLE_ID = "1392389240846905346";

	/**
	 * 默认机构 无单位
	 */
	String DEFAULT_DEPT_ID = "1393106020741431298";

	/**
	 * 默认的用户类型 普通用户
	 */
	String DEFAULT_USERTYPE_ID="1392412543590424578";

}
