package org.springblade.modules.system.wrapper;

import org.springblade.common.cache.SysCache;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.vo.UserVO;

import java.util.List;
import java.util.Objects;

/**
 * 用户表包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO>  {

    public static UserWrapper build() {
        return new UserWrapper();
    }

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = Objects.requireNonNull(BeanUtil.copy(user, UserVO.class));
		List<String> roleName = SysCache.getRoleNames(user.getRoleId());
		String deptName = SysCache.getDeptName(user.getDeptId());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(deptName);
		return userVO;
	}

}
