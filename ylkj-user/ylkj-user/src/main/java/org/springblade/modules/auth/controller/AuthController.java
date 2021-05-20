package org.springblade.modules.auth.controller;

import com.github.xiaoymin.knife4j.annotations.ApiSort;
import com.wf.captcha.SpecCaptcha;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springblade.common.cache.CacheNames;
import org.springblade.common.cache.SysCache;
import org.springblade.core.cache.constant.CacheConstant;
import org.springblade.core.cache.utils.CacheUtil;
import org.springblade.core.redis.cache.BladeRedis;
import org.springblade.core.secure.BladeUser;
import org.springblade.core.secure.utils.AuthUtil;
import org.springblade.core.tool.api.R;
import org.springblade.core.tool.support.Kv;
import org.springblade.core.tool.utils.BeanUtil;
import org.springblade.core.tool.utils.WebUtil;
import org.springblade.modules.auth.exception.OAuthLoginException;
import org.springblade.modules.auth.granter.TokenGranterBuilder;
import org.springblade.modules.auth.utils.TokenUtil;
import org.springblade.modules.system.dto.AuthClientDTO;
import org.springblade.modules.system.dto.UserInfo;
import org.springblade.modules.system.entity.AuthClient;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.time.Duration;
import java.util.UUID;

/**
 * @author zjm
 * @since 2021-05-14 10:57
 * 认证模块
 */
@RestController
@AllArgsConstructor
@ApiSort(1)
@Api(value = "用户授权认证", tags = "授权接口")
@RequestMapping("/oauth")
public class AuthController {

    private final BladeRedis redis;

    /**
     * token
     * @param account
     * @param password
     * @return
     */
    @PostMapping("/token")
    @ApiOperation(value = "获取认证token", notes = "账号:account,密码:password")
    public Kv token(@ApiParam(value = "账号", required = true) @RequestParam(required = false) String account,
                    @ApiParam(value = "密码", required = true) @RequestParam(required = false) String password) {
        String grantType = WebUtil.getRequest().getParameter("grant_type");
        String refreshToken = WebUtil.getRequest().getParameter("refresh_token");
        //得到要认证的客户端
        AuthClient client = AuthUtil.getClientByID();
        if (client == null) {
            return Kv.create().set("error_code", HttpServletResponse.SC_BAD_REQUEST).set("error_description", "无效的客户端编号");
        }
        try {
            return TokenGranterBuilder.getGranter(grantType).grant(
                    Kv.create().
                            set("account", account).set("password", password).
                            set("grantType", grantType).set("refreshToken", refreshToken),
                    client);
        } catch (OAuthLoginException oauthException) {
            return Kv.create().set("error_code", HttpServletResponse.SC_BAD_REQUEST).set("error_description", oauthException.getOAuthLoginEnum().getError());
        } catch (Throwable throwable) {
            return Kv.create().set("error_code", HttpServletResponse.SC_BAD_REQUEST).set("error_description", throwable.getMessage());
        }
    }

    /**
     * 客户端信息
     * @param clientId
     * @return
     */
    @GetMapping("/verify-client")
    @ApiOperation(value = "获取客户端信息")
    public R<AuthClientDTO> verifyClient(@ApiParam(value = "客户端编号", required = true) @RequestParam(required = false) String clientId) {
        AuthClient client = SysCache.getClient(clientId);
        if (client == null) {
            return R.fail("未找到客户端信息");
        }
        return R.data(BeanUtil.copy(client, AuthClientDTO.class));
    }

    /**
     * 登录用户的信息
     * @return
     */
    @GetMapping("/user-info")
    @ApiOperation(value = "获取当前登录用户详细信息")
    public R<UserInfo> getUserInfo() {
        BladeUser bladeUser = AuthUtil.getUser();
        if(bladeUser==null) {
            return R.fail(403,"TOKEN失效！");
        }
        return R.data(bladeUser.getUserInfo());
    }

    /**
     * 验证码
     * @return
     */
    @GetMapping("/captcha")
    @ApiOperation(value = "获取验证码")
    public Kv captcha() {
        SpecCaptcha specCaptcha = new SpecCaptcha(130, 48, 4);
        String key = UUID.randomUUID().toString();
        // 存入redis并设置过期时间为30分钟
        redis.setEx(CacheNames.CAPTCHA_KEY + key, specCaptcha.text().toLowerCase(), Duration.ofMinutes(30));
        // 将key和base64返回给前端
        return Kv.create().set("key", key).set("image", specCaptcha.toBase64());
    }

    @GetMapping("/oauth/logout")
    @ApiOperation(value = "退出登录")
    public Kv logout() {
        TokenUtil.logout(AuthUtil.getToken());
        return Kv.create().set("success", "true").set("msg", "success");
    }

    @GetMapping("/oauth/clear-cache")
    @ApiOperation(value = "清除缓存")
    public Kv clearCache() {
        CacheUtil.clear(CacheConstant.BIZ_CACHE);
        CacheUtil.clear(CacheConstant.MENU_CACHE);
        CacheUtil.clear(CacheConstant.USER_CACHE);
        CacheUtil.clear(CacheConstant.DICT_CACHE);
        CacheUtil.clear(CacheConstant.FLOW_CACHE);
        CacheUtil.clear(CacheConstant.SYS_CACHE);
        CacheUtil.clear(CacheConstant.RESOURCE_CACHE);
        CacheUtil.clear(CacheConstant.PARAM_CACHE);
        return Kv.create().set("success", "true").set("msg", "success");
    }
}
