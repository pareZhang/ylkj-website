package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.system.entity.UserOauth;
import org.springblade.modules.system.vo.UserOauthVO;
import org.springblade.modules.system.mapper.UserOauthMapper;
import org.springblade.modules.system.service.IUserOauthService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 用户第三方认证表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
public class UserOauthServiceImpl extends ServiceImpl<UserOauthMapper, UserOauth> implements IUserOauthService {

	@Override
	public IPage<UserOauthVO> selectUserOauthPage(IPage<UserOauthVO> page, UserOauthVO userOauth) {
		return page.setRecords(baseMapper.selectUserOauthPage(page, userOauth));
	}

}
