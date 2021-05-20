package org.springblade.modules.system.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.system.entity.Crt;
import org.springblade.modules.system.vo.CrtVO;

/**
 * crt证书表包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class CrtWrapper extends BaseEntityWrapper<Crt, CrtVO>  {

    public static CrtWrapper build() {
        return new CrtWrapper();
    }

	@Override
	public CrtVO entityVO(Crt crt) {
		CrtVO crtVO = BeanUtil.copy(crt, CrtVO.class);

		return crtVO;
	}

}
