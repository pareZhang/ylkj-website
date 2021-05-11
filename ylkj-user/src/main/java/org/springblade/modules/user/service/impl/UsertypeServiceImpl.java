package org.springblade.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springblade.modules.user.entity.Usertype;
import org.springblade.modules.user.vo.UsertypeVO;
import org.springblade.modules.user.mapper.UsertypeMapper;
import org.springblade.modules.user.service.IUsertypeService;
import org.springblade.core.mp.base.BaseServiceImpl;
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
