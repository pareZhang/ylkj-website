package org.springblade.modules.system.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springblade.modules.system.Excel.UserExcel;
import org.springblade.modules.system.entity.Dept;
import org.springblade.modules.system.vo.UserVO;
import java.util.List;
import org.springblade.modules.system.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * 用户表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface UserMapper extends BaseMapper<User> {
	/**
	 * 自定义分页
	 * @param page
	 * @param user
	 * @param deptIdList
	 * @return
	 */
	List<User> selectUserPage(IPage<User> page, @Param("user") User user, @Param("deptIdList") List<Long> deptIdList);

	/**
	 * 导出用户数据
	 * @param queryWrapper
	 * @return
	 */
	List<UserExcel> exportUser(@Param("ew")QueryWrapper<User> queryWrapper);

	/**
	 * 登录认证
	 * @param account
	 * @param password
	 * @return
	 */
    User login(String account, String password);

}
