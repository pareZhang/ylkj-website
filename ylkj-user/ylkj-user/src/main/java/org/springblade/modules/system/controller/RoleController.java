package org.springblade.modules.system.controller;

import io.swagger.annotations.*;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.system.service.IRoleClientService;
import org.springblade.modules.system.vo.GrantClientVO;
import org.springblade.modules.system.vo.GrantMenuVO;
import org.springblade.modules.system.vo.GrantVO;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.entity.Role;
import org.springblade.modules.system.vo.RoleVO;
import org.springblade.modules.system.wrapper.RoleWrapper;
import org.springblade.modules.system.service.IRoleService;
import org.springblade.core.boot.ctrl.BladeController;
import springfox.documentation.annotations.ApiIgnore;

import java.util.List;
import java.util.Map;

import static org.springblade.core.cache.constant.CacheConstant.SYS_CACHE;

/**
 * 角色表 控制器
 *
 * @author zjm
 * @since 2021-05-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/role")
@Api(value = "角色表", tags = "角色表接口")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class RoleController extends BladeController {

	private IRoleService roleService;

	private IRoleClientService roleClientService;

	/**
	* 详情
	 * id查询
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入role")
	public R<RoleVO> detail(Role role) {
		Role detail = roleService.getOne(Condition.getQueryWrapper(role));
		return R.data(RoleWrapper.build().entityVO(detail));
	}

	/**
	 * 列表 角色表
	 */
	@GetMapping("/list")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "列表", notes = "传入role")
	public R<List<Role>> list(@ApiIgnore @RequestParam Map<String, Object> role) {
		return R.data(roleService.list(Condition.getQueryWrapper(role, Role.class)));
	}

	/**
	* 自定义分页 角色表
	*/
	@GetMapping("/page")
	@ApiImplicitParams({
			@ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
			@ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string")
	})
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入role")
	public R<IPage<Role>> page(RoleVO role, Query query) {
		IPage<Role> pages = roleService.selectRolePage(Condition.getPage(query), role);
		return R.data(pages);
	}

	/**
	* 新增或修改 角色表
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入role")
	public R submit(@Valid @RequestBody Role role) {
		//清除缓存
		CacheUtil.clear(SYS_CACHE);
		return R.status(roleService.submit(role));
	}

	/**
	* 删除 角色表
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		CacheUtil.clear(SYS_CACHE);
		return R.status(roleService.removeByIds(Func.toLongList(ids)));
	}

	//TODO 需要前后端联调测试
	/**
	 * 设置角色权限
	 */
	@PostMapping("/grantRole")
	@ApiOperation(value = "权限设置", notes = "传入roleId以及clientIds集合")
	public R grantRole(@RequestBody GrantClientVO grantVO) {
		CacheUtil.clear(SYS_CACHE);
		boolean temp = roleClientService.grant(grantVO.getRoleId(), grantVO.getClientIds());
		return R.status(temp);
	}
	//TODO 需要前后端联调测试
	/**
	 * 设置菜单权限
	 */
	@PostMapping("/grantMenu")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public R grantMenu(@RequestBody GrantMenuVO grantVO) {
		boolean temp = roleService.grant(grantVO.getRoleIds(), grantVO.getMenuIds());
		return R.status(temp);
	}
	/**
	 * 设置角色权限
	 */
	@PostMapping("/grant")
	@ApiOperation(value = "权限设置", notes = "传入roleId集合以及menuId集合")
	public R grant(@RequestBody GrantVO grantVO) {
		CacheUtil.clear(SYS_CACHE);
		boolean temp = roleService.grant(grantVO.getRoleId(), grantVO.getMenuIds(), grantVO.getDataScopeIds(), grantVO.getApiScopeIds());
		return R.status(temp);
	}
	
}
