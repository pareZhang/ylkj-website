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
package org.springblade.core.mp.config;

import com.baomidou.mybatisplus.core.injector.ISqlInjector;
import com.baomidou.mybatisplus.core.parser.ISqlParser;
import com.baomidou.mybatisplus.core.parser.ISqlParserFilter;
import lombok.AllArgsConstructor;
import org.mybatis.spring.annotation.MapperScan;
import org.springblade.core.launch.props.BladePropertySource;
import org.springblade.core.mp.injector.BladeSqlInjector;
import org.springblade.core.mp.intercept.QueryInterceptor;
import org.springblade.core.mp.plugins.BladePaginationInterceptor;
import org.springblade.core.mp.plugins.SqlLogInterceptor;
import org.springblade.core.mp.props.MybatisPlusProperties;
import org.springblade.core.tool.utils.ObjectUtil;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.Arrays;

/**
 * mybatis-plus 配置
 *
 * @author Chill
 */
@Configuration
@AllArgsConstructor
@MapperScan("org.springblade.**.mapper.**")
@EnableConfigurationProperties(MybatisPlusProperties.class)
@BladePropertySource(value = "classpath:/blade-mybatis.yml")
public class MybatisPlusConfiguration {

	/**
	 * 分页拦截器
	 */
	@Bean
	public BladePaginationInterceptor paginationInterceptor(ObjectProvider<QueryInterceptor[]> queryInterceptors,
															ObjectProvider<ISqlParser[]> sqlParsers,
															ObjectProvider<ISqlParserFilter> sqlParserFilter,
															MybatisPlusProperties mybatisPlusProperties) {
		BladePaginationInterceptor paginationInterceptor = new BladePaginationInterceptor();
		QueryInterceptor[] queryInterceptorArray = queryInterceptors.getIfAvailable();
		if (ObjectUtil.isNotEmpty(queryInterceptorArray)) {
			AnnotationAwareOrderComparator.sort(queryInterceptorArray);
			paginationInterceptor.setQueryInterceptors(queryInterceptorArray);
		}
		ISqlParser[] sqlParsersArray = sqlParsers.getIfAvailable();
		if (ObjectUtil.isNotEmpty(sqlParsersArray)) {
			paginationInterceptor.setSqlParserList(Arrays.asList(sqlParsersArray));
		}
		paginationInterceptor.setSqlParserFilter(sqlParserFilter.getIfAvailable());
		paginationInterceptor.setLimit(mybatisPlusProperties.getPageLimit());
		return paginationInterceptor;
	}

	/**
	 * sql 日志
	 */
	@Bean
	@ConditionalOnProperty(value = "blade.mybatis-plus.sql-log", matchIfMissing = true)
	public SqlLogInterceptor sqlLogInterceptor() {
		return new SqlLogInterceptor();
	}

	/**
	 * sql 注入
	 */
	@Bean
	public ISqlInjector sqlInjector() {
		return new BladeSqlInjector();
	}

}

