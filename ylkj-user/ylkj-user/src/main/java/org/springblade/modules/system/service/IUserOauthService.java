package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.entity.UserOauth;
import org.springblade.modules.system.vo.UserOauthVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户第三方认证表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IUserOauthService extends IService<UserOauth> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param userOauth
	 * @return
	 */
	IPage<UserOauthVO> selectUserOauthPage(IPage<UserOauthVO> page, UserOauthVO userOauth);

}
