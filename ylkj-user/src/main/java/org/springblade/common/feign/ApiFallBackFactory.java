package org.springblade.common.feign;

import feign.hystrix.FallbackFactory;
import org.springblade.modules.user.dto.UserInfo;
import org.springblade.core.tool.api.R;

public class ApiFallBackFactory implements FallbackFactory<ApiClient> {
	@Override
	public ApiClient create(Throwable throwable) {
		return new ApiClient() {
			@Override
			public R<UserInfo> getUserInfo() {
				return R.fail("获取数据失败！");
			}
		};
	}
}
