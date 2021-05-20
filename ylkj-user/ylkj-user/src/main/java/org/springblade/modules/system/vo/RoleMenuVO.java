package org.springblade.modules.system.vo;

import org.springblade.modules.system.entity.RoleMenu;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 角色菜单关联表视图实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "RoleMenuVO对象", description = "角色菜单关联表")
public class RoleMenuVO extends RoleMenu {
	private static final long serialVersionUID = 1L;

}
