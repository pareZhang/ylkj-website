package org.springblade.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.secure.annotation.PreAuth;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.constant.RoleConstant;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.entity.Usertype;
import org.springblade.modules.system.vo.UsertypeVO;
import org.springblade.modules.system.wrapper.UsertypeWrapper;
import org.springblade.modules.system.service.IUsertypeService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 *  控制器
 *
 * @author zjm
 * @since 2021-05-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/usertype")
@Api(value = "用户类型", tags = "用户类型接口")
@PreAuth(RoleConstant.HAS_ROLE_ADMIN)
public class UsertypeController extends BladeController {

	private IUsertypeService usertypeService;

	/**
	* 详情 通过id来查
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入usertype")
	public R<UsertypeVO> detail(Usertype usertype) {
		Usertype detail = usertypeService.getOne(Condition.getQueryWrapper(usertype));
		return R.data(UsertypeWrapper.build().entityVO(detail));
	}

	/**
	* 分页 
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入usertype")
	public R<IPage<UsertypeVO>> list(Usertype usertype, Query query) {
		IPage<Usertype> pages = usertypeService.page(Condition.getPage(query), Condition.getQueryWrapper(usertype));
		return R.data(UsertypeWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入usertype")
	public R<IPage<UsertypeVO>> page(UsertypeVO usertype, Query query) {
		IPage<UsertypeVO> pages = usertypeService.selectUsertypePage(Condition.getPage(query), usertype);
		return R.data(pages);
	}

	/**
	* 新增或修改 
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入usertype")
	public R submit(@Valid @RequestBody Usertype usertype) {
		return R.status(usertypeService.saveOrUpdate(usertype));
	}

	
	/**
	* 删除
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(usertypeService.removeByIds(Func.toLongList(ids)));
	}

	
}
