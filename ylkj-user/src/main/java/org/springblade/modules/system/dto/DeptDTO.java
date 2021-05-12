
package org.springblade.modules.system.dto;

import org.springblade.modules.system.entity.Dept;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 部门表数据传输对象实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class DeptDTO extends Dept {
	private static final long serialVersionUID = 1L;

}
