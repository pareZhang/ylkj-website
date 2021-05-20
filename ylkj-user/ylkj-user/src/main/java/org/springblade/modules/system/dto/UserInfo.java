package org.springblade.modules.system.dto;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springblade.common.constant.CommonConstant;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.system.entity.Dept;
import org.springblade.modules.system.entity.Role;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IDeptService;
import org.springblade.modules.system.service.IRoleService;
import org.springblade.modules.system.service.IUserService;

import java.io.Serializable;
import java.util.List;

@Data
public class UserInfo implements Serializable {
	private static final long serialVersionUID = 1L;

	private static IUserService userService;
	private static IDeptService deptService;
	private static IRoleService roleService;


	static {
		userService = SpringUtil.getBean(IUserService.class);
		deptService = SpringUtil.getBean(IDeptService.class);
		roleService = SpringUtil.getBean(IRoleService.class);
	}

	@ApiModelProperty(value = "用户")
	private UserDTO user;

	@ApiModelProperty(value = "角色集合")
	private List<RoleDTO> roles;

	@ApiModelProperty(value = "部门集合")
	private DeptDTO dept;

	public static UserInfo parse(Long userId){
		UserInfo userInfo = new UserInfo();
		User user = userService.getOne(Wrappers.<User>query().lambda().eq(User::getId,userId).eq(User::getStatus, CommonConstant.STATUS_ENABLE));
		if (null!=user) {
			userInfo.setUser(BeanUtil.copy(user, UserDTO.class));
		}
		Dept dept = deptService.getById(user.getDeptId());
		if(null!=dept) {
			userInfo.setDept(BeanUtil.copy(dept, DeptDTO.class));
		}
		List<Role> roleList = roleService.getRoles(user.getRoleId());
		if(null != roleList && roleList.size() > 0) {
			userInfo.setRoles(BeanUtil.copy(roleList, RoleDTO.class));
		}
		return  userInfo;
	}
}
