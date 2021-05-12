package org.springblade.modules.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.entity.Role;
import org.springblade.modules.system.vo.RoleVO;

/**
 * 角色表包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class RoleWrapper extends BaseEntityWrapper<Role, RoleVO>  {

    public static RoleWrapper build() {
        return new RoleWrapper();
    }

	@Override
	public RoleVO entityVO(Role role) {
		RoleVO roleVO = BeanUtil.copy(role, RoleVO.class);
		return roleVO;
	}

}
