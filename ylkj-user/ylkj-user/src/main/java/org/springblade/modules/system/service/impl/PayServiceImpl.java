package org.springblade.modules.system.service.impl;

import org.springblade.modules.system.entity.Pay;
import org.springblade.modules.system.vo.PayVO;
import org.springblade.modules.system.mapper.PayMapper;
import org.springblade.modules.system.service.IPayService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 支付信息表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
public class PayServiceImpl extends BaseServiceImpl<PayMapper, Pay> implements IPayService {

	@Override
	public IPage<PayVO> selectPayPage(IPage<PayVO> page, PayVO pay) {
		return page.setRecords(baseMapper.selectPayPage(page, pay));
	}

}
