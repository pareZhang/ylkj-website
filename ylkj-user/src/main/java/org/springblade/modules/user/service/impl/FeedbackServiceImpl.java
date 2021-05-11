package org.springblade.modules.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.modules.user.entity.Feedback;
import org.springblade.modules.user.entity.User;
import org.springblade.modules.user.vo.FeedbackVO;
import org.springblade.modules.user.mapper.FeedbackMapper;
import org.springblade.modules.user.service.IFeedbackService;
import org.springblade.core.mp.base.BaseServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * 意见反馈表 服务实现类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Service
@AllArgsConstructor
public class FeedbackServiceImpl extends ServiceImpl<FeedbackMapper, Feedback> implements IFeedbackService {

	private FeedbackMapper feedbackMapper;
	@Override
	public IPage<FeedbackVO> selectFeedbackPage(IPage<FeedbackVO> page, FeedbackVO feedback) {
		return page.setRecords(baseMapper.selectFeedbackPage(page, feedback));
	}

	@Override
	public List<FeedbackVO> getAll(User user) {
		return feedbackMapper.getAll(user);
	}

}
