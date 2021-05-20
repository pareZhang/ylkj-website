package org.springblade.modules.system.vo;

import org.springblade.modules.system.entity.Crt;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * crt证书表视图实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "CrtVO对象", description = "crt证书表")
public class CrtVO extends Crt {
	private static final long serialVersionUID = 1L;

}
