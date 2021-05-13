package org.springblade.modules.system.mapper;

import org.springblade.modules.system.dto.MenuDTO;
import org.springblade.modules.system.entity.Menu;
import org.springblade.modules.system.vo.MenuVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;
import java.util.Map;

/**
 * 菜单表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface MenuMapper extends BaseMapper<Menu> {

	/**
	 * 懒加载列表
	 * @param parentId
	 * @param menu
	 * @return
	 */
    List<MenuVO> lazyList(Long parentId, Map<String, Object> menu);

	/**
	 * 懒加载菜单列表
	 * @param parentId
	 * @param menu
	 * @return
	 */
	List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> menu);

	/**
	 * 所有菜单
	 * @return
	 */
	List<Menu> allMenu();

	/**
	 * 权限配置菜单
	 * @param roleId
	 * @return
	 */
	List<Menu> roleMenu(List<Long> roleId);

	/**
	 * 按钮树形结构
	 * @return
	 */
	List<Menu> allButtons();

	/**
	 * 按钮树形结构
	 * @param roleIds
	 * @return
	 */
	List<Menu> buttons(List<Long> roleIds);

	/**
	 * 树形结构
	 * @return
	 */
	List<MenuVO> tree();

	/**
	 * 授权树形结构
	 * @return
	 */
	List<MenuVO> grantTree();

	/**
	 * 根据角色授权树形结构
	 * @param roleIds
	 * @return
	 */
	List<MenuVO> grantTreeByRole(List<Long> roleIds);

	/**
	 * 数据权限授权树形结构
	 * @return
	 */
	List<MenuVO> grantDataScopeTree();

	/**
	 * 根据角色数据权限授权树形结构
	 * @param roleIds
	 * @return
	 */
	List<MenuVO> grantDataScopeTreeByRole(List<Long> roleIds);

	/**
	 * 接口权限授权树形结构
	 * @return
	 */
	List<MenuVO> grantApiScopeTree();

	/**
	 * 根据角色接口权限授权树形结构
	 * @param roleIds
	 * @return
	 */
	List<MenuVO> grantApiScopeTreeByRole(List<Long> roleIds);

	/**
	 * 获取配置的角色权限
	 * @param roleIds
	 * @return
	 */
	List<MenuDTO> authRoutes(List<Long> roleIds);
}
