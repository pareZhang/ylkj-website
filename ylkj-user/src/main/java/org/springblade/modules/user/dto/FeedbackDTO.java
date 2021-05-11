package org.springblade.modules.user.dto;

import org.springblade.modules.user.entity.Feedback;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 意见反馈表数据传输对象实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class FeedbackDTO extends Feedback {
	private static final long serialVersionUID = 1L;

}
