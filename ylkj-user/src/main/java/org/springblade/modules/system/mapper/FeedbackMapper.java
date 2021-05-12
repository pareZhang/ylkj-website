package org.springblade.modules.system.mapper;

import org.springblade.modules.system.entity.Feedback;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.vo.FeedbackVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 意见反馈表 Mapper 接口
 *
 * @author zjm
 * @since 2021-05-10
 */
public interface FeedbackMapper extends BaseMapper<Feedback> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param feedback
	 * @return
	 */
	List<FeedbackVO> selectFeedbackPage(IPage page, FeedbackVO feedback);

	/**
	 * 查询指定用户的所有
	 * @param user
	 * @return
	 */
    List<FeedbackVO> getAll(User user);
}