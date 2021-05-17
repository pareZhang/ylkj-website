package org.springblade.modules.auth.utils;

import lombok.extern.slf4j.Slf4j;
import org.springblade.common.cache.CacheNames;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.DigestUtil;
import org.springblade.core.tool.utils.Func;
import org.springblade.core.tool.utils.SpringUtil;
import org.springblade.modules.auth.enums.OAuthLoginEnum;
import org.springblade.modules.auth.exception.OAuthLoginException;
import org.springblade.modules.system.entity.AuthClient;
import org.springblade.modules.system.entity.User;
import org.springblade.modules.system.service.IUserService;

import java.time.Duration;
import java.util.UUID;

/**
 * @author zjm
 * @since 2021-05-14 11:23
 * 认证工具类
 */
@Slf4j
public class TokenUtil {
    private static BladeRedis bladeRedis;
    private static IUserService userService;

    static {
        bladeRedis = SpringUtil.getBean(BladeRedis.class);
        userService = SpringUtil.getBean(IUserService.class);
    }

    public static Kv build(String account, String password, AuthClient client) {
        if (!Func.isNoneBlank(account, password)) {
            throw new OAuthLoginException(OAuthLoginEnum.MISSING_INFO);
        }
        User user = userService.oauthLogin(account, DigestUtil.hex(password));
        if (user == null) {
            throw new OAuthLoginException(OAuthLoginEnum.USER_PASSWORD_ERROR);
        }
        if (Func.isEmpty(user.getRoleId())) {
            throw new OAuthLoginException(OAuthLoginEnum.ROLE_NOT_CONFIGURED);
        }
        if (user.getStatus() == 1) {
            throw new OAuthLoginException(OAuthLoginEnum.INVALID_USER);
        }

        String cacheKey = CacheNames.USER_LOGIN_CACHE_KEY + user.getId();
        if (bladeRedis.exists(cacheKey)){
            try{
                Kv kv = bladeRedis.get(cacheKey);
                if (kv != null){
                    kv.set("timestamp", System.currentTimeMillis());
                    return kv;
                }
            }catch (Exception ignored){ }
        }
        String token = UUID.randomUUID().toString();
        bladeRedis.setEx(CacheNames.TOKEN_CACHE_KEY + token, user.getId(), Duration.ofSeconds(client.getTokenValidity()));
        Kv result = Kv.create().
                set("access_token", token).
                set("expires_in", client.getTokenValidity()).
                set("create_time", System.currentTimeMillis()).
                set("timestamp", System.currentTimeMillis());
        bladeRedis.setEx(cacheKey, result, Duration.ofSeconds(client.getTokenValidity()));
        return result;
    }

    public static Kv refreshToken(String reqToken, AuthClient client){
        String token = CacheNames.TOKEN_CACHE_KEY + reqToken;
        if (bladeRedis.exists(token)){
            bladeRedis.expire(token, Duration.ofSeconds(client.getTokenValidity()));
            long id = bladeRedis.get(token);
            bladeRedis.expire(CacheNames.USER_LOGIN_CACHE_KEY + id, Duration.ofSeconds(client.getTokenValidity()));
            return Kv.create().
                    set("access_token", reqToken).
                    set("expires_in", client.getTokenValidity()).
                    set("create_time", System.currentTimeMillis()).
                    set("timestamp", System.currentTimeMillis());
        }else {
            throw new OAuthLoginException(OAuthLoginEnum.INVALID_TOKEN);
        }
    }

    public static void logout(String reqToken){
        String token = CacheNames.TOKEN_CACHE_KEY + reqToken;
        if (bladeRedis.exists(token)){
            long id = bladeRedis.get(token);
            bladeRedis.del(token);
            bladeRedis.del(CacheNames.USER_LOGIN_CACHE_KEY + id);
        }
    }
}
