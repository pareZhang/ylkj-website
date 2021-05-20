package org.springblade.modules.auth.granter;

import lombok.AllArgsConstructor;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.log.exception.ServiceException;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.StringUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.modules.auth.utils.TokenUtil;
import org.springblade.modules.system.entity.AuthClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author zjm
 * @since 2021-05-14 11:34
 * CaptchaTokenGranter
 */
@AllArgsConstructor
@Component
public class CaptchaTokenGranter implements ITokenGranter{
    public static final String GRANT_TYPE = "captcha";

    private final BladeRedis bladeRedis;

    @Override
    public Kv grant(Kv args, AuthClient client) {
        HttpServletRequest request = WebUtil.getRequest();
        String redisCode = bladeRedis.get(CacheNames.CAPTCHA_KEY + request.getHeader("Captcha-Key"));
        String code = request.getHeader("Captcha-Code");
        if (code == null || !StringUtil.equalsIgnoreCase(redisCode, code)) {
            throw new ServiceException("验证码不正确");
        }
        return TokenUtil.build(args.getStr("account"), args.getStr("password"), client);
    }
}
