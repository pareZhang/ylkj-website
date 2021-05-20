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
import org.springblade.modules.system.entity.User;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestParam;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springblade.modules.system.entity.Feedback;
import org.springblade.modules.system.vo.FeedbackVO;
import org.springblade.modules.system.wrapper.FeedbackWrapper;
import org.springblade.modules.system.service.IFeedbackService;
import org.springblade.core.boot.ctrl.BladeController;

import java.util.List;


/**
 * 意见反馈表 控制器
 *
 * @author zjm
 * @since 2021-05-10
 */
@RestController
@AllArgsConstructor
@RequestMapping("/feedback")
@Api(value = "意见反馈表", tags = "意见反馈表接口")
public class FeedbackController extends BladeController {

	private IFeedbackService feedbackService;

	/**
	* 详情
	 * js是通过id来查询
	*/
	@GetMapping("/detail")
    @ApiOperationSupport(order = 1)
	@ApiOperation(value = "详情", notes = "传入feedback")
	public R<FeedbackVO> detail(Feedback feedback) {
		Feedback detail = feedbackService.getOne(Condition.getQueryWrapper(feedback));
		return R.data(FeedbackWrapper.build().entityVO(detail));
	}

	/**
	 * 查询指定用户的所有反馈
	 * @param user
	 * @return
	 */
	@GetMapping("/all")
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "指定用户所有的反馈",notes = "传入user")
	public R<List<FeedbackVO>> all(User user){
		List<FeedbackVO> list=feedbackService.getAll(user);
		return R.data(list);
	}

	/**
	* 分页 意见反馈表
	 * 查询所有
	*/
	@GetMapping("/list")
    @ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入feedback")
	public R<IPage<FeedbackVO>> list(Feedback feedback, Query query) {
		IPage<Feedback> pages = feedbackService.page(Condition.getPage(query), Condition.getQueryWrapper(feedback));
		return R.data(FeedbackWrapper.build().pageVO(pages));
	}

	/**
	* 新增或修改 意见反馈表
	*/
	@PostMapping("/submit")
    @ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增或修改", notes = "传入feedback")
	public R submit(@Valid @RequestBody Feedback feedback) {
		return R.status(feedbackService.saveOrUpdate(feedback));
	}


	/**
	 * 逻辑删除
	 * @param ids
	 * @return
	 */
	@PostMapping("/remove")
    @ApiOperationSupport(order = 5)
	@ApiOperation(value = "逻辑删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(feedbackService.removeByIds(Func.toLongList(ids)));
	}
}
