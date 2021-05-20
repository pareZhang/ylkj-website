package org.springblade.modules.system.service;

import org.springblade.modules.system.entity.Pay;
import org.springblade.modules.system.vo.PayVO;
import org.springblade.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 支付信息表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IPayService extends BaseService<Pay> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param pay
	 * @return
	 */
	IPage<PayVO> selectPayPage(IPage<PayVO> page, PayVO pay);

}
