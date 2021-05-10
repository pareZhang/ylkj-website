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
package org.springblade.core.secure;


import lombok.Data;
import org.springblade.modules.user.dto.RoleDTO;
import org.springblade.modules.user.dto.UserInfo;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.tool.utils.Func;

import java.io.Serializable;
import java.util.stream.Collectors;

/**
 * 用户实体
 *
 * @author Chill
 */
@Data
public class BladeUser implements Serializable {

	private static final long serialVersionUID = 1L;

	private UserInfo userInfo;

	public Long getUserId(){
		return userInfo.getUser().getId();
	}

	public Long getDeptId(){
		return userInfo.getDept().getId();
	}

	public String getRoleAlias(){
		return Func.join(userInfo.getRoles().stream().map(RoleDTO::getRoleAlias).collect(Collectors.toList()));
	}

	public String getName(){
		return userInfo.getUser().getName();
	}


	public static BladeUser parse(Long uid){
			if(Func.isEmpty(uid)) {
				throw new ServiceException("用户编号不能为空。");
			}
			BladeUser bladeUser = new BladeUser();
			bladeUser.setUserInfo(UserInfo.parse(uid));
			return bladeUser;
	}

}
