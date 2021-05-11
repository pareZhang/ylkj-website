package org.springblade.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.user.entity.AuthClient;
import org.springblade.modules.user.mapper.AuthClientMapper;
import org.springblade.modules.user.service.IAuthClientService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 客户端表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
public class AuthClientServiceImpl extends ServiceImpl<AuthClientMapper, AuthClient> implements IAuthClientService {

	@Override
	public IPage<AuthClient> findPage(IPage<AuthClient> page, AuthClient authClient) {
		return page.setRecords(baseMapper.findPage(page,authClient));
	}
}
