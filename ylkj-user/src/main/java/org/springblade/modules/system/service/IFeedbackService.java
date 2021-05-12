package org.springblade.modules.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.system.entity.Feedback;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.vo.FeedbackVO;
import com.baomidou.mybatisplus.core.metadata.IPage;

import java.util.List;

/**
 * 意见反馈表 服务类
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface IFeedbackService extends IService<Feedback> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param feedback
	 * @return
	 */
	IPage<FeedbackVO> selectFeedbackPage(IPage<FeedbackVO> page, FeedbackVO feedback);

	/**
	 * 指定用户所有的反馈
	 * @param user
	 * @return
	 */
    List<FeedbackVO> getAll(User user);
}
