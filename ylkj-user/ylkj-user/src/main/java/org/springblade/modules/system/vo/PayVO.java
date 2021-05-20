package org.springblade.modules.system.vo;

import org.springblade.modules.system.entity.Pay;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 支付信息表视图实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "PayVO对象", description = "支付信息表")
public class PayVO extends Pay {
	private static final long serialVersionUID = 1L;

}
