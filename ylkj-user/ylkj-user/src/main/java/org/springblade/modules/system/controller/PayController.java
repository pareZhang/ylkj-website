package org.springblade.modules.system.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import javax.validation.Valid;

import org.springblade.core.mp.support.Condition;
import org.springblade.core.mp.support.Query;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.entity.Pay;
import org.springblade.modules.system.vo.PayVO;
import org.springblade.modules.system.wrapper.PayWrapper;
import org.springblade.modules.system.service.IPayService;
import org.springblade.core.boot.ctrl.BladeController;

/**
 * 支付信息表 控制器
 *
 * @author zjm
 * @since 2021-05-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/pay")
@Api(value = "支付信息表", tags = "支付信息表接口")
public class PayController extends BladeController {

	private IPayService payService;

	/**
	* 详情
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入pay")
	public R<PayVO> detail(Pay pay) {
		Pay detail = payService.getOne(Condition.getQueryWrapper(pay));
		return R.data(PayWrapper.build().entityVO(detail));
	}

	/**
	* 分页 支付信息表
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入pay")
	public R<IPage<PayVO>> list(Pay pay, Query query) {
		IPage<Pay> pages = payService.page(Condition.getPage(query), Condition.getQueryWrapper(pay));
		return R.data(PayWrapper.build().pageVO(pages));
	}

	/**
	* 自定义分页 支付信息表
	*/
	@GetMapping("/page")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入pay")
	public R<IPage<PayVO>> page(PayVO pay, Query query) {
		IPage<PayVO> pages = payService.selectPayPage(Condition.getPage(query), pay);
		return R.data(pages);
	}

	/**
	* 新增 支付信息表
	*/
	@PostMapping("/save")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入pay")
	public R save(@Valid @RequestBody Pay pay) {
		return R.status(payService.save(pay));
	}

	/**
	* 修改 支付信息表
	*/
	@PostMapping("/update")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入pay")
	public R update(@Valid @RequestBody Pay pay) {
		return R.status(payService.updateById(pay));
	}

	/**
	* 新增或修改 支付信息表
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入pay")
	public R submit(@Valid @RequestBody Pay pay) {
		return R.status(payService.saveOrUpdate(pay));
	}

	
	/**
	* 删除 支付信息表
	*/
	@PostMapping("/remove")
    @ApiOperationSupport(order = 7)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(payService.deleteLogic(Func.toLongList(ids)));
	}

	
}
