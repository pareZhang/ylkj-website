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
package org.springblade.core.secure.registry;

import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * secure api放行配置
 *
 * @author Chill
 */
@Data
public class SecureRegistry {

	private boolean enabled = false;

	private final List<String> defaultExcludePatterns = new ArrayList<>();

	private final List<String> excludePatterns = new ArrayList<>();

	public SecureRegistry() {
		defaultExcludePatterns.add("/actuator/health/**");
		defaultExcludePatterns.add("/oauth/token");
		defaultExcludePatterns.add("/ylkj-api/wx-verify");
		defaultExcludePatterns.add("/oauth/captcha");
		defaultExcludePatterns.add("/oauth/verify-client");
		defaultExcludePatterns.add("/v2/api-docs/**");
		defaultExcludePatterns.add("/v2/api-docs-ext/**");
		defaultExcludePatterns.add("/auth/**");
		defaultExcludePatterns.add("/token/**");
		defaultExcludePatterns.add("/log/**");
		defaultExcludePatterns.add("/error/**");
		defaultExcludePatterns.add("/assets/**");
		defaultExcludePatterns.add("/webjars/**");
		defaultExcludePatterns.add("/swagger-resources/**");
		defaultExcludePatterns.add("/js/**");
		defaultExcludePatterns.add("/doc.html");
		defaultExcludePatterns.add("/blade-log/**");
		defaultExcludePatterns.add("/js/**");
	}

	/**
	 * 设置放行api
	 */
	public SecureRegistry excludePathPatterns(String... patterns) {
		return excludePathPatterns(Arrays.asList(patterns));
	}

	/**
	 * 设置放行api
	 */
	public SecureRegistry excludePathPatterns(List<String> patterns) {
		this.excludePatterns.addAll(patterns);
		return this;
	}

}
