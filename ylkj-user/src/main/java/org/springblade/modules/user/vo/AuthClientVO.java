package org.springblade.modules.user.vo;

import org.springblade.modules.user.entity.AuthClient;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 客户端表视图实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "ClientVO对象", description = "客户端表")
public class AuthClientVO extends AuthClient {
	private static final long serialVersionUID = 1L;

}
