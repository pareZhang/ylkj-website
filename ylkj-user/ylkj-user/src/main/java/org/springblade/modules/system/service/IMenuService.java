package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.core.tool.support.Kv;
import org.springblade.modules.system.entity.Menu;
import org.springblade.modules.system.vo.MenuVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 菜单表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IMenuService extends IService<Menu> {

	/**
	 * 懒加载列表
	 * @param parentId
	 * @param menu
	 * @return
	 */
	List<MenuVO> lazyList(Long parentId, Map<String, Object> menu);

	/**
	 *懒加载菜单列表
	 * @param parentId
	 * @param menu
	 * @return
	 */
	List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> menu);

	/**
	 * 新增或修改
	 * @param menu
	 * @return
	 */
	boolean submit(Menu menu);

	/**
	 * 删除菜单
	 * @param ids
	 * @return
	 */
	boolean removeMenu(String ids);
	/**
	 * 菜单树形结构
	 *
	 * @param roleId
	 * @return
	 */
	//List<MenuVO> routes(String roleId);
	/**
	 * 菜单树形结构
	 *
	 * @return
	 */
	List<MenuVO> routes();

	/**
	 * 按钮树形结构
	 * @return
	 */
	List<MenuVO> buttons();

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
	 * 数据权限授权树形结构
	 * @return
	 */
	List<MenuVO> grantDataScopeTree();

	/**
	 * 接口权限授权树形结构
	 * @return
	 */
	List<MenuVO> grantApiScopeTree();

	/**
	 * 默认选中节点
	 * @param roleIds
	 * @return
	 */
	List<String> roleTreeKeys(String roleIds);

	/**
	 * 默认选中节点
	 * @param roleIds
	 * @return
	 */
	List<String> dataScopeTreeKeys(String roleIds);

	/**
	 * 默认选中节点
	 * @param roleIds
	 * @return
	 */
	List<String> apiScopeTreeKeys(String roleIds);

	/**
	 * 获取配置的角色权限
	 * @return
	 */
	List<Kv> authRoutes();

}
