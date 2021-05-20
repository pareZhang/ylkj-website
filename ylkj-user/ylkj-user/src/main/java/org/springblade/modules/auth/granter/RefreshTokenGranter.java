package org.springblade.modules.auth.granter;

import lombok.AllArgsConstructor;
import org.springblade.core.launch.constant.TokenConstant;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.Func;
import org.springblade.modules.auth.utils.TokenUtil;
import org.springblade.modules.system.entity.AuthClient;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletResponse;

/**
 * @author zjm
 * @since 2021-05-14 11:37
 *RefreshTokenGranter
 */
@Component
@AllArgsConstructor
public class RefreshTokenGranter implements ITokenGranter{

    public static final String GRANT_TYPE = "refresh_token";

    @Override
    public Kv grant(Kv args, AuthClient client) {
        String grantType = args.getStr("grantType");
        String refreshToken = args.getStr("refreshToken");
        if (Func.isNoneBlank(grantType, refreshToken) && grantType.equals(TokenConstant.REFRESH_TOKEN)) {
            return TokenUtil.refreshToken(refreshToken,client);
        }
        return Kv.create().set("error_code", HttpServletResponse.SC_BAD_REQUEST).set("error_description", "错误的参数");
    }
}
