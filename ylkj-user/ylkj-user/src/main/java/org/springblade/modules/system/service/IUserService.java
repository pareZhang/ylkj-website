package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.Excel.UserExcel;
import org.springblade.modules.system.entity.Dept;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.vo.UserVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;
import java.util.Map;

/**
 * 用户表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IUserService extends IService<User> {

	/**
	 * 根据账号获取用户
	 *
	 * @param account
	 * @return
	 */
	User userByAccount(String account);
	/**
	 * 自定义分页用户列表
	 * @param page
	 * @param user
	 * @param deptId
	 * @return
	 */
	IPage<User> selectUserPage(IPage<User> page, User user, Long deptId);

	/**
	 * 人工审核
	 * 实名认证
	 * @param name
	 * @param idCard
	 * @return
	 */
	Map<String, Object> idCardCert(String name, String idCard);

	/**
	 * 新增或修改
	 * @param user
	 * @return
	 */
	boolean submit(User user);
	/**
	 * 删除用户
	 * @param userIds
	 * @return
	 */
	boolean removeUser(String userIds);

	/**
	 * 重置密码
	 * @param userIds
	 * @return
	 */
	boolean resetPassword(String userIds);

	/**
	 * 修改密码
	 * @param userId
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	boolean updatePassword(Long userId, String oldPassword, String newPassword, String newPassword1);

	/**
	 * 设置菜单权限
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	boolean grant(String userIds, String roleIds);

	/**
	 * 导入用户数据
	 * @param data
	 * @param isCovered
	 */
	void importUser(List<UserExcel> data, Boolean isCovered);

	/**
	 * 导出用户数据
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(QueryWrapper<User> queryWrapper);

	/**
	 * 第三方注册用户
	 * @param user
	 * @param oauthId
	 * @return
	 */
	boolean registerGuest(User user, Long oauthId);

	/**
	 * 登录认证
	 * @param account
	 * @param password
	 * @return
	 */
	User oauthLogin(String account, String password);

}
