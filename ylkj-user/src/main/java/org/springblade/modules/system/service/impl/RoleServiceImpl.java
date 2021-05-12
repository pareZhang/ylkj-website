package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Role;
import org.springblade.modules.system.entity.RoleMenu;
import org.springblade.modules.system.service.IRoleMenuService;
import org.springblade.modules.system.mapper.RoleMapper;
import org.springblade.modules.system.service.IRoleService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springframework.validation.annotation.Validated;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
@Validated
@AllArgsConstructor
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {
    IRoleMenuService roleMenuService;

    @Override
    public IPage<Role> selectRolePage(IPage<Role> page, Role role) {
        return page.setRecords(baseMapper.selectRolePage(page, role));
    }

    @Override
    public List<Role> getRoles(String roleIds) {
        return baseMapper.getRoles(Func.toLongArray(roleIds));
    }

    @Override
    public List<String> getRoleNames(String roleIds) {
        return baseMapper.getRoleNames(Func.toLongArray(roleIds));
    }

    @Override
    public String getRoleIds(String roleNames) {
        List<Role> roleList = baseMapper.selectList(Wrappers.<Role>query().lambda().in(Role::getRoleName, Func.toStrList(roleNames)));
        if (roleList != null && roleList.size() > 0) {
            return roleList.stream().map(role -> Func.toStr(role.getId())).distinct().collect(Collectors.joining(","));
        }
        return null;
    }

    @Override
    public List<String> getRoleAliases(String roleIds) {
        return baseMapper.getRoleAliases(Func.toLongArray(roleIds));
    }

    @Override
    public boolean submit(Role role) {

        //TODO 生产环境放开此段注释
//        //判断是否为超管 1 是:不能创建超管角色  2：不是直接修改或者新增
//        if (!AuthUtil.isAdministrator()) {
//            if (Func.toStr(role.getRoleAlias()).equals(RoleConstant.ADMINISTRATOR)) {
//                throw new ServiceException("没有权限直接创建超管角色");
//            }
//        }
        return saveOrUpdate(role);
    }

    @Override
    public boolean grant(List<Long> roleIds, List<Long> menuIds) {
        // 删除角色配置的菜单集合
        roleMenuService.remove(Wrappers.<RoleMenu>update().lambda().in(RoleMenu::getRoleId, roleIds));
        // 组装配置
        List<RoleMenu> roleMenus = new ArrayList<>();
        roleIds.forEach(roleId -> menuIds.forEach(menuId -> {
            RoleMenu roleMenu = new RoleMenu();
            roleMenu.setRoleId(roleId);
            roleMenu.setMenuId(menuId);
            roleMenus.add(roleMenu);
        }));
        // 新增配置
        return roleMenuService.saveBatch(roleMenus);
    }
}
