package org.springblade.modules.system.dto;

import org.springblade.modules.system.entity.UserOauth;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户第三方认证表数据传输对象实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserOauthDTO extends UserOauth {
	private static final long serialVersionUID = 1L;

}
