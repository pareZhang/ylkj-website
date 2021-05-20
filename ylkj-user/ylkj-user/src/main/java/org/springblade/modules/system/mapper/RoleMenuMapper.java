package org.springblade.modules.system.mapper;

import org.springblade.modules.system.entity.RoleMenu;
import org.springblade.modules.system.vo.RoleMenuVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 角色菜单关联表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param roleMenu
	 * @return
	 */
	List<RoleMenuVO> selectRoleMenuPage(IPage page, RoleMenuVO roleMenu);

}
