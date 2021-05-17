package org.springblade.modules.auth.granter;

import lombok.AllArgsConstructor;
import org.springblade.core.tool.support.Kv;
import org.springblade.modules.auth.utils.TokenUtil;
import org.springblade.modules.system.entity.AuthClient;
import org.springframework.stereotype.Component;

/**
 * @author zjm
 * @since 2021-05-14 11:20
 * PasswordTokenGranter
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter{

    public static final String GRANT_TYPE = "password";

    @Override
    public Kv grant(Kv args, AuthClient client) {
        return TokenUtil.build(args.getStr("account"), args.getStr("password"), client);
    }
}
