package org.springblade.modules.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import org.springblade.modules.user.entity.Feedback;
import org.springblade.modules.user.entity.User;
import org.springblade.modules.user.vo.FeedbackVO;
import org.springblade.core.mp.base.BaseService;
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
