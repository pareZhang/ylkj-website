package org.springblade.modules.user.vo;

import org.springblade.modules.user.entity.Feedback;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

/**
 * 意见反馈表视图实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "FeedbackVO对象", description = "意见反馈表")
public class FeedbackVO extends Feedback {
	private static final long serialVersionUID = 1L;

}
