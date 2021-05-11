package org.springblade.modules.user.dto;

import org.springblade.modules.user.entity.Usertype;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 数据传输对象实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UsertypeDTO extends Usertype {
	private static final long serialVersionUID = 1L;

}
