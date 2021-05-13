package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.DictCache;
import org.springblade.common.cache.SysCache;
import org.springblade.common.constant.CommonConstant;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.BladeConstant;
import org.springblade.core.tool.node.ForestNodeMerger;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.Menu;
import org.springblade.modules.system.vo.MenuVO;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 菜单表包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO>  {

    public static MenuWrapper build() {
        return new MenuWrapper();
    }

	@Override
	public MenuVO entityVO(Menu menu) {
//        MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
//        if (Func.equals(menu.getParentId(), CommonConstant.TOP_PARENT_ID)) {
//            menuVO.setParentName(CommonConstant.TOP_PARENT_NAME);
//        } else {
//            Menu parent = menuService.getById(menu.getParentId());
//            menuVO.setParentName(parent.getName());
//        }
//        R<String> d1 = dictClient.getValue("menu_category", Func.toInt(menuVO.getCategory()));
//        R<String> d2 = dictClient.getValue("button_func", Func.toInt(menuVO.getAction()));
//        R<String> d3 = dictClient.getValue("yes_no", Func.toInt(menuVO.getIsOpen()));
//        if (d1.isSuccess()) {
//            menuVO.setCategoryName(d1.getData());
//        }
//        if (d2.isSuccess()) {
//            menuVO.setActionName(d2.getData());
//        }
//        if (d3.isSuccess()) {
//            menuVO.setIsOpenName(d3.getData());
//        }
//        return menuVO;
        MenuVO menuVO = Objects.requireNonNull(BeanUtil.copy(menu, MenuVO.class));
        if (Func.equals(menu.getParentId(), BladeConstant.TOP_PARENT_ID)) {
            menuVO.setParentName(BladeConstant.TOP_PARENT_NAME);
        } else {
            Menu parent = SysCache.getMenu(menu.getParentId());
            menuVO.setParentName(parent.getName());
        }
        String category = DictCache.getValue("menu_category", Func.toInt(menuVO.getCategory()));
        String action = DictCache.getValue("button_func", Func.toInt(menuVO.getAction()));
        String open = DictCache.getValue("yes_no", Func.toInt(menuVO.getIsOpen()));
        menuVO.setCategoryName(category);
        menuVO.setActionName(action);
        menuVO.setIsOpenName(open);
        return menuVO;
	}

    public List<MenuVO> listNodeVO(List<Menu> list) {
        List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
        return ForestNodeMerger.merge(collect);
    }

    public List<MenuVO> listNodeLazyVO(List<MenuVO> list) {
        return ForestNodeMerger.merge(list);
    }
}
