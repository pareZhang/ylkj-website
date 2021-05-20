package org.springblade.modules.system.dto;

import org.springblade.modules.system.entity.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户表数据传输对象实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends User {
	private static final long serialVersionUID = 1L;

}
