package org.springblade.modules.auth.granter;

import lombok.AllArgsConstructor;
import org.springblade.core.secure.exception.SecureException;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author zjm
 * @since 2021-05-14 11:17
 * TokenGranterBuilder
 */
@AllArgsConstructor
public class TokenGranterBuilder {
    /**
     * TokenGranter缓存池
     */
    private static Map<String, ITokenGranter> granterPool = new ConcurrentHashMap<>();

    static {
        granterPool.put(PasswordTokenGranter.GRANT_TYPE, SpringUtil.getBean(PasswordTokenGranter.class));
        granterPool.put(CaptchaTokenGranter.GRANT_TYPE, SpringUtil.getBean(CaptchaTokenGranter.class));
        granterPool.put(RefreshTokenGranter.GRANT_TYPE, SpringUtil.getBean(RefreshTokenGranter.class));
    }

    /**
     * 获取TokenGranter
     *
     * @param grantType 授权类型
     * @return ITokenGranter
     */
    public static ITokenGranter getGranter(String grantType) {
        ITokenGranter tokenGranter = granterPool.get(Func.toStr(grantType, PasswordTokenGranter.GRANT_TYPE));
        if (tokenGranter == null) {
            throw new SecureException("no grantType was found");
        } else {
            return granterPool.get(Func.toStr(grantType, PasswordTokenGranter.GRANT_TYPE));
        }
    }
}
