package org.springblade.modules.system.vo;

import org.springblade.modules.system.entity.Usertype;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 视图实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UsertypeVO对象", description = "UsertypeVO对象")
public class UsertypeVO extends Usertype {
	private static final long serialVersionUID = 1L;

}
