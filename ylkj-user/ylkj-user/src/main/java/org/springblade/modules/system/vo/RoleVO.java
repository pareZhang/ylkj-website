package org.springblade.modules.system.vo;

import org.springblade.modules.system.entity.Role;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 角色表视图实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RoleVO对象", description = "角色表")
public class RoleVO extends Role {
	private static final long serialVersionUID = 1L;

}
