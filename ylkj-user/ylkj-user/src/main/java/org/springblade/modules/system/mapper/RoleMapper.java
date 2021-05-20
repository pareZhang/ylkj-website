package org.springblade.modules.system.mapper;

import org.springblade.modules.system.entity.Role;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 角色表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface RoleMapper extends BaseMapper<Role> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param role
	 * @return
	 */
	List<Role> selectRolePage(IPage<Role> page, Role role);

	/**
	 * 获取角色别名
	 * @param ids
	 * @return
	 */
    List<String> getRoleAliases(Long[] ids);
	/**
	 * 获取角色名
	 * @param ids
	 * @return
	 */
	List<String> getRoleNames(Long[] ids);

	/**
	 * 根据id获取角色
	 * @param ids
	 * @return
	 */
	List<Role> getRoles(Long[] ids);

}
