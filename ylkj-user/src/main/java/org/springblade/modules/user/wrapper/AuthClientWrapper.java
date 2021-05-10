package org.springblade.modules.user.wrapper;

import org.springblade.core.mp.support.BaseEntityWrapper;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.modules.user.entity.AuthClient;
import org.springblade.modules.user.vo.AuthClientVO;

/**
 * 客户端表包装类,返回视图层所需的字段
 *
 * @author zjm
 * @since 2021-05-10
 */
public class AuthClientWrapper extends BaseEntityWrapper<AuthClient, AuthClientVO>  {

    public static AuthClientWrapper build() {
        return new AuthClientWrapper();
    }

	@Override
	public AuthClientVO entityVO(AuthClient authClient) {
		AuthClientVO clientVO = BeanUtil.copy(authClient, AuthClientVO.class);

		return clientVO;
	}

}
