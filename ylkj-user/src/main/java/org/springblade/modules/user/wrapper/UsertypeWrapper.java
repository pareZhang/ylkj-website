package org.springblade.modules.user.wrapper;

import lombok.AllArgsConstructor;
import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.user.entity.Usertype;
import org.springblade.modules.user.vo.UsertypeVO;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class UsertypeWrapper extends BaseEntityWrapper<Usertype, UsertypeVO>  {

    public static UsertypeWrapper build() {
        return new UsertypeWrapper();
    }

	@Override
	public UsertypeVO entityVO(Usertype usertype) {
		UsertypeVO usertypeVO = BeanUtil.copy(usertype, UsertypeVO.class);

		return usertypeVO;
	}

}
