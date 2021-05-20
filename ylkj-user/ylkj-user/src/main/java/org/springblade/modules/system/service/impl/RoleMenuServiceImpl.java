package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.system.entity.RoleMenu;
import org.springblade.modules.system.mapper.RoleMenuMapper;
import org.springblade.modules.system.service.IRoleMenuService;
import org.springframework.stereotype.Service;

/**
 * 角色菜单关联表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements IRoleMenuService {

}
