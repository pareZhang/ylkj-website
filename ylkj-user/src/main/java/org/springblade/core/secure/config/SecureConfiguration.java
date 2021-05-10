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
package org.springblade.core.secure.config;


import lombok.AllArgsConstructor;
import org.springblade.core.secure.handler.ISecureHandler;
import org.springblade.core.secure.props.BladeSecureProperties;
import org.springblade.core.secure.registry.SecureRegistry;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * 安全配置类
 *
 * @author Chill
 */
@Order
@Configuration
@AllArgsConstructor
@EnableConfigurationProperties({BladeSecureProperties.class})
public class SecureConfiguration implements WebMvcConfigurer {

	private final SecureRegistry secureRegistry;

	private final BladeSecureProperties secureProperties;

	private final ISecureHandler secureHandler;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		if (secureRegistry.isEnabled()) {
			registry.addInterceptor(secureHandler.tokenInterceptor())
				.excludePathPatterns(secureRegistry.getExcludePatterns())
				.excludePathPatterns(secureRegistry.getDefaultExcludePatterns())
				.excludePathPatterns(secureProperties.getSkipUrl());
		}
	}
	
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/cors/**")
			.allowedOrigins("*")
			.allowedHeaders("*")
			.allowedMethods("*")
			.maxAge(3600)
			.allowCredentials(true);
	}
}
