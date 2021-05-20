package org.springblade.common.config;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springblade.core.secure.utils.AuthUtil;

/**
 * Feign 请求拦截器
 **/

public class FeignRequestInterceptor implements RequestInterceptor {
	@Override
	public void apply(RequestTemplate requestTemplate) {
		requestTemplate.header("access_token", AuthUtil.getToken());
	}
}
