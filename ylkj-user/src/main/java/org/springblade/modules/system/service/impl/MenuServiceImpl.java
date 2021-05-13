package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.dto.MenuDTO;
import org.springblade.modules.system.entity.Menu;
import org.springblade.modules.system.entity.RoleMenu;
import org.springblade.modules.system.entity.RoleScope;
import org.springblade.modules.system.service.IRoleMenuService;
import org.springblade.modules.system.service.IRoleScopeService;
import org.springblade.modules.system.vo.MenuVO;
import org.springblade.modules.system.mapper.MenuMapper;
import org.springblade.modules.system.service.IMenuService;
import org.springblade.modules.system.wrapper.MenuWrapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 菜单表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	private IRoleMenuService roleMenuService;
	private IRoleScopeService roleScopeService;
	@Override
	public List<MenuVO> lazyList(Long parentId, Map<String, Object> menu) {
		if (Func.isEmpty(Func.toStr(menu.get("parentId")))) {
			parentId = null;
		}
		return baseMapper.lazyList(parentId, menu);
	}

	@Override
	public List<MenuVO> lazyMenuList(Long parentId, Map<String, Object> menu) {
		if (Func.isEmpty(Func.toStr(menu.get("parentId")))) {
			parentId = null;
		}
		return baseMapper.lazyMenuList(parentId, menu);
	}

	@Override
	public boolean submit(Menu menu) {
		if (menu.getParentId() == null && menu.getId() == null) {
			menu.setParentId(BladeConstant.TOP_PARENT_ID);
		}
		menu.setIsDeleted(BladeConstant.DB_NOT_DELETED);
		return saveOrUpdate(menu);
	}

	@Override
	public boolean removeMenu(String ids) {
		//判断父节点
		Integer count = baseMapper.selectCount(Wrappers.<Menu>query().lambda().in(Menu::getParentId, Func.toLongList(ids)));
		if (count > 0) {
			throw new ServiceException("请先删除子节点!");
		}
		return removeByIds(Func.toLongList(ids));
	}

	/*@Override
	public List<MenuVO> routes(String roleId) {
		if (StringUtil.isBlank(roleId)) {
			return null;
		}
		List<Menu> allMenus = baseMapper.allMenu();
		List<Menu> roleMenus = baseMapper.roleMenu(Func.toLongList(roleId));
		List<Menu> routes = new LinkedList<>(roleMenus);
		roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
		routes.sort(Comparator.comparing(Menu::getSort));
		MenuWrapper menuWrapper = new MenuWrapper();
		List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), 1)).collect(Collectors.toList());
		return menuWrapper.listNodeVO(collect);
	}*/
	@Override
	public List<MenuVO> routes() {
		BladeUser user = AuthUtil.getUser();
		assert user != null;
		if (user.getRoleIds().size() < 1) {
			return null;
		}

		List<Menu> allMenus = baseMapper.allMenu();
		List<Menu> roleMenus = AuthUtil.isAdministrator() ? allMenus : baseMapper.roleMenu(AuthUtil.getRoleIds());
		return buildRoutes(allMenus, roleMenus);
	}
	private List<MenuVO> buildRoutes(List<Menu> allMenus, List<Menu> roleMenus) {
		List<Menu> routes = new LinkedList<>(roleMenus);
		roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
		routes.sort(Comparator.comparing(Menu::getSort));
		MenuWrapper menuWrapper = new MenuWrapper();
		List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), 1)).collect(Collectors.toList());
		return menuWrapper.listNodeVO(collect);
	}
	public void recursion(List<Menu> allMenus, List<Menu> routes, Menu roleMenu) {
		Optional<Menu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
		if (menu.isPresent() && !routes.contains(menu.get())) {
			routes.add(menu.get());
			recursion(allMenus, routes, menu.get());
		}
	}

	@Override
	public List<MenuVO> buttons() {
		List<Menu> buttons = AuthUtil.isAdministrator() ? baseMapper.allButtons() : baseMapper.buttons(AuthUtil.getRoleIds());
		MenuWrapper menuWrapper = new MenuWrapper();
		return menuWrapper.listNodeVO(buttons);
	}

	@Override
	public List<MenuVO> tree() {
		return ForestNodeMerger.merge(baseMapper.tree());
	}

	@Override
	public List<MenuVO> grantTree() {
		return ForestNodeMerger.merge(AuthUtil.isAdministrator() ? baseMapper.grantTree() : baseMapper.grantTreeByRole(AuthUtil.getRoleIds()));
	}

	@Override
	public List<MenuVO> grantDataScopeTree() {
		return ForestNodeMerger.merge(AuthUtil.isAdministrator() ? baseMapper.grantDataScopeTree() : baseMapper.grantDataScopeTreeByRole(AuthUtil.getRoleIds()));
	}

	@Override
	public List<MenuVO> grantApiScopeTree() {
		return ForestNodeMerger.merge(AuthUtil.isAdministrator() ? baseMapper.grantApiScopeTree() : baseMapper.grantApiScopeTreeByRole(AuthUtil.getRoleIds()));
	}

	@Override
	public List<String> roleTreeKeys(String roleIds) {
		List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, Func.toLongList(roleIds)));
		return roleMenus.stream().map(roleMenu -> Func.toStr(roleMenu.getMenuId())).collect(Collectors.toList());
	}

	@Override
	public List<String> dataScopeTreeKeys(String roleIds) {
		List<RoleScope> roleScopes = roleScopeService.list(Wrappers.<RoleScope>query().lambda().eq(RoleScope::getScopeCategory, 1).in(RoleScope::getRoleId, Func.toLongList(roleIds)));
		return roleScopes.stream().map(roleScope -> Func.toStr(roleScope.getScopeId())).collect(Collectors.toList());
	}

	@Override
	public List<String> apiScopeTreeKeys(String roleIds) {
		List<RoleScope> roleScopes = roleScopeService.list(Wrappers.<RoleScope>query().lambda().eq(RoleScope::getScopeCategory, 2).in(RoleScope::getRoleId, Func.toLongList(roleIds)));
		return roleScopes.stream().map(roleScope -> Func.toStr(roleScope.getScopeId())).collect(Collectors.toList());
	}

	@Override
	public List<Kv> authRoutes() {
		List<MenuDTO> routes = baseMapper.authRoutes(AuthUtil.getRoleIds());
		List<Kv> list = new ArrayList<>();
		routes.forEach(route -> list.add(Kv.create().set(route.getPath(), Kv.create().set("authority", Func.toStrArray(route.getAlias())))));
		return list;
	}

}
