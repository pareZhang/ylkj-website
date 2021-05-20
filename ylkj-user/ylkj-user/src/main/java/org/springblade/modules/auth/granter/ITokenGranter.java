package org.springblade.modules.auth.granter;

import org.springblade.core.tool.support.Kv;
import org.springblade.modules.system.entity.AuthClient;

/**
 * @author zjm
 * @since 2021-05-14 11:19
 * 授权认证统一接口
 */
public interface ITokenGranter {
    /**
     * 获取用户信息
     *
     * @param args 授权参数
     * @return UserInfo
     */
    Kv grant(Kv args, AuthClient client);

}
