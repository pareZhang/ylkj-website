package org.springblade.modules.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.entity.Pay;
import org.springblade.modules.system.vo.PayVO;

/**
 * 支付信息表包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class PayWrapper extends BaseEntityWrapper<Pay, PayVO>  {

    public static PayWrapper build() {
        return new PayWrapper();
    }

	@Override
	public PayVO entityVO(Pay pay) {
		PayVO payVO = BeanUtil.copy(pay, PayVO.class);

		return payVO;
	}

}
