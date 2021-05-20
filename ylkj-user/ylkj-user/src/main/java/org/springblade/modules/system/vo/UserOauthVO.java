package org.springblade.modules.system.vo;

import org.springblade.modules.system.entity.UserOauth;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 用户第三方认证表视图实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "UserOauthVO对象", description = "用户第三方认证表")
public class UserOauthVO extends UserOauth {
	private static final long serialVersionUID = 1L;

}
