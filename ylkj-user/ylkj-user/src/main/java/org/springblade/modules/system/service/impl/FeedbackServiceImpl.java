package org.springblade.modules.system.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springblade.modules.system.entity.Feedback;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.vo.FeedbackVO;
import org.springblade.modules.system.mapper.FeedbackMapper;
import org.springblade.modules.system.service.IFeedbackService;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

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
