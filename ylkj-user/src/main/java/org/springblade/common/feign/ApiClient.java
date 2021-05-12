package org.springblade.common.feign;


import org.springblade.modules.system.dto.UserInfo;
import org.springblade.core.tool.api.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;


@FeignClient
public interface ApiClient {

	String USER_INFO = "/oauth/user-info";

	/**
	 * 获取用户信息
	 * @return
	 */
	@GetMapping(USER_INFO)
	R<UserInfo> getUserInfo();


}
