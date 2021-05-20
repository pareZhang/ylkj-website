package org.springblade.modules.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import io.swagger.annotations.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.excel.util.ExcelUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.Sm4Util;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.DateUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.modules.system.Excel.UserExcel;
import org.springblade.modules.system.Excel.UserImporter;
import org.springblade.modules.system.entity.Dept;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.vo.UserVO;
import org.springblade.modules.system.wrapper.UserWrapper;
import org.springblade.modules.system.service.IUserService;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.USER_CACHE;

/**
 * 用户表 控制器
 *
 * @author zjm
 * @since 2021-05-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/user")
@Api(value = "用户表", tags = "用户表接口")
public class UserController {

	private IUserService userService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入user")
	public R<UserVO> detail(User user) {
		User detail = userService.getOne(Condition.getQueryWrapper(user));
		return R.data(UserWrapper.build().entityVO(detail));
	}

	/**
	 * 查询单条
	 */
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "查看详情", notes = "传入id")
	@GetMapping("/info")
	public R<UserVO> info(BladeUser user) {
		User detail = userService.getById(user.getUserId());
		return R.data(UserWrapper.build().entityVO(detail));
	}
	/**
	 * 用户列表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "列表", notes = "传入account和realName")
	public R<IPage<UserVO>> list(@ApiIgnore @RequestParam Map<String, Object> user, Query query) {
		return R.data(UserWrapper.build().pageVO(userService.page(Condition.getPage(query), Condition.getQueryWrapper(user, User.class))));
	}
	/**
	 * 用户列表
	 * 无分页list
	 */
	@GetMapping("/user-list")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "用户列表", notes = "传入user")
	public R<List<User>> userList(User user) {
		List<User> list = userService.list(Condition.getQueryWrapper(user));
		return R.data(list);
	}
	/**
	 * 自定义分页 用户列表
	 * 根据部门查询所有下属user
	 */
	@GetMapping("/page")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "account", value = "账号名", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "realName", value = "姓名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "列表", notes = "传入account和realName")
	public R<IPage<UserVO>> page(User user, Query query, Long deptId) {
		IPage<User> pages = userService.selectUserPage(Condition.getPage(query), user, deptId);
		return R.data(UserWrapper.build().pageVO(pages));
	}

	/**
	 * 新增或者修改 用户表
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增", notes = "传入user")
	public R submit(@Valid @RequestBody User user) {
		CacheUtil.clear(USER_CACHE);
		return R.status(userService.submit(user));
	}

	/**
	 * 实名认证
	 * @param realName
	 * @param idNum
	 * @return
	 */
	@GetMapping(value = "/idCardCert")
	@ApiOperationSupport(order = 7)
	@ApiOperation(value = "实名认证", notes = "实名认证接口")
	public Map<String, Object> idCardCert(@RequestParam String realName, @RequestParam String idNum) {
		return userService.idCardCert(realName, idNum);
	}

	/**
	 * 修改
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "修改", notes = "传入User")
	public R update(@Valid @RequestBody User user) {
		CacheUtil.clear(USER_CACHE);
		return R.status(userService.updateById(user));
	}

	/**
	 * 删除
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 9)
	@ApiOperation(value = "删除", notes = "传入id集合")
	public R remove(@RequestParam String ids) {
		CacheUtil.clear(USER_CACHE);
		return R.status(userService.removeUser(ids));
	}
	/**
	 * 重置密码
	 *  sjgl123456
	 */
	@PostMapping("/reset-password")
	@ApiOperationSupport(order = 10)
	@ApiOperation(value = "初始化密码", notes = "传入userId集合")
	public R resetPassword(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds) {
		boolean temp = userService.resetPassword(userIds);
		return R.status(temp);
	}
	/**
	 * 修改密码
	 *
	 * @param oldPassword
	 * @param newPassword
	 * @param newPassword1
	 * @return
	 */
	@PostMapping("/update-password")
	@ApiOperationSupport(order = 11)
	@ApiOperation(value = "修改密码", notes = "传入密码")
	public R updatePassword(BladeUser user, @ApiParam(value = "旧密码", required = true) @RequestParam String oldPassword,
							@ApiParam(value = "新密码", required = true) @RequestParam String newPassword,
							@ApiParam(value = "新密码", required = true) @RequestParam String newPassword1) {
		boolean temp = userService.updatePassword(user.getUserId(), oldPassword, newPassword, newPassword1);
		return R.status(temp);
	}
	/**
	 * 设置菜单权限
	 * 后台设置权限模块
	 * @param userIds
	 * @param roleIds
	 * @return
	 */
	@PostMapping("/grant")
	@ApiOperationSupport(order = 12)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public R grant(@ApiParam(value = "userId集合", required = true) @RequestParam String userIds,
				   @ApiParam(value = "roleId集合", required = true) @RequestParam String roleIds) {
		boolean temp = userService.grant(userIds, roleIds);
		return R.status(temp);
	}
	/**
	 * 导入用户
	 */
	@PostMapping("import-user")
	@ApiOperationSupport(order = 13)
	@ApiOperation(value = "导入用户", notes = "传入excel")
	public R importUser(MultipartFile file, Integer isCovered) {
		UserImporter userImporter = new UserImporter(userService, isCovered == 1);
		ExcelUtil.save(file, userImporter, UserExcel.class);
		return R.success("操作成功");
	}
	/**
	 * 导出用户
	 */
	@GetMapping("export-user")
	@ApiOperationSupport(order = 14)
	@ApiOperation(value = "导出用户", notes = "传入user")
	public void exportUser(@ApiIgnore @RequestParam Map<String, Object> user, HttpServletResponse response) {
		QueryWrapper<User> queryWrapper = Condition.getQueryWrapper(user, User.class);
		queryWrapper.lambda().eq(User::getStatus, 0);
		List<UserExcel> list = userService.exportUser(queryWrapper);
		ExcelUtil.export(response, "用户数据" + DateUtil.time(), "用户数据表", list, UserExcel.class);
	}
	/**
	 * 导出模板
	 */
	@GetMapping("export-template")
	@ApiOperationSupport(order = 15)
	@ApiOperation(value = "导出模板")
	public void exportUser(HttpServletResponse response) {
		List<UserExcel> list = new ArrayList<>();
		ExcelUtil.export(response, "用户数据模板", "用户数据表", list, UserExcel.class);
	}

	/**
	 * 下载证书
	 * @param key 用户自己输入的密钥
	 * @param id 用户id
	 * @param response
	 */
	@GetMapping("export-crt")
	@ApiOperationSupport(order = 16)
	@ApiOperation(value = "导出证书")
	public void exportCert(String key,String id,HttpServletResponse response){
		try {
			if(StringUtil.isEmpty(key)) {
				throw new Error("密钥不能为空");
			}
			if(StringUtil.isEmpty(id)) {
				throw new Error("用户编号不能为空");
			}
			BladeUser bladeUser = BladeUser.parse(Func.toLong(id));
			//组装
			key = "sjgl"+key+"crt";
			String code = Func.md5Hex(key);
			String params = JSONObject.toJSONString(bladeUser.getUserInfo());
			String str = Sm4Util.encryptEcb(code,params);
			Sm4Util.exportTxt(response,bladeUser.getName(),str);
		}catch (Exception e){
			throw new Error("导出失败");
		}
	}

	/**
	 * 第三方注册用户
	 */
	@PostMapping("/register-guest")
	@ApiOperationSupport(order = 17)
	@ApiOperation(value = "第三方注册用户", notes = "传入user")
	public R registerGuest(User user, Long oauthId) {
		return R.status(userService.registerGuest(user, oauthId));
	}

}
