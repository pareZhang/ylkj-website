package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.system.entity.RoleScope;
import org.springblade.modules.system.mapper.RoleScopeMapper;
import org.springblade.modules.system.service.IRoleScopeService;
import org.springframework.stereotype.Service;

/**
 * @author zjm
 * @since 2021-05-13 11:51
 */
@Service
public class IRoleScopeServiceImpl extends ServiceImpl<RoleScopeMapper, RoleScope> implements IRoleScopeService {
}
