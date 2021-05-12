package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.system.entity.Menu;
import org.springblade.modules.system.vo.MenuVO;
import org.springblade.modules.system.mapper.MenuMapper;
import org.springblade.modules.system.service.IMenuService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 菜单表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	@Override
	public IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu) {
		return page.setRecords(baseMapper.selectMenuPage(page, menu));
	}

}
