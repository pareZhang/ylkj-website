package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.entity.Role;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 角色表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IRoleService extends IService<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	IPage<Role> selectRolePage(IPage<Role> page, Role role);


	List<Role> getRoles(String roleIds);
	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleNames(String roleIds);

	/**
	 * 获取角色ID
	 *
	 * @param roleNames
	 * @return
	 */
	String getRoleIds(String roleNames);

	/**
	 * 获取角色名
	 *
	 * @param roleIds
	 * @return
	 */
	List<String> getRoleAliases(String roleIds);

	/**
	 * 新增或修改
	 * @param role
	 * @return
	 */
	boolean submit(Role role);

	/**
	 * 菜单权限配置
	 * @param roleIds
	 * @param menuIds
	 * @return
	 */
	boolean grant(List<Long> roleIds, List<Long> menuIds);
}
