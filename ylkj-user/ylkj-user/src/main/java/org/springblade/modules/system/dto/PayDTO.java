package org.springblade.modules.system.dto;
import org.springblade.modules.system.entity.Pay;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 支付信息表数据传输对象实体类
 *
 * @author Blade
 * @since 2021-05-09
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PayDTO extends Pay {
	private static final long serialVersionUID = 1L;

}
