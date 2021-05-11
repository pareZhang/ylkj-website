package org.springblade.modules.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.user.entity.Feedback;
import org.springblade.modules.user.vo.FeedbackVO;

/**
 * 意见反馈表包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class FeedbackWrapper extends BaseEntityWrapper<Feedback, FeedbackVO>  {

    public static FeedbackWrapper build() {
        return new FeedbackWrapper();
    }

	@Override
	public FeedbackVO entityVO(Feedback feedback) {

		FeedbackVO feedbackVO = BeanUtil.copy(feedback, FeedbackVO.class);

		return feedbackVO;
	}

}
