package org.springblade.modules.system.dto;

import org.springblade.modules.system.entity.Menu;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 菜单表数据传输对象实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
public class MenuDTO implements Serializable {
	private static final long serialVersionUID = 1L;
	private String alias;
	private String path;

}
