package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.system.entity.Usertype;
import org.springblade.modules.system.vo.UsertypeVO;
import org.springblade.modules.system.mapper.UsertypeMapper;
import org.springblade.modules.system.service.IUsertypeService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
public class UsertypeServiceImpl extends ServiceImpl<UsertypeMapper, Usertype> implements IUsertypeService {

	@Override
	public IPage<UsertypeVO> selectUsertypePage(IPage<UsertypeVO> page, UsertypeVO usertype) {
		return page.setRecords(baseMapper.selectUsertypePage(page, usertype));
	}

}
