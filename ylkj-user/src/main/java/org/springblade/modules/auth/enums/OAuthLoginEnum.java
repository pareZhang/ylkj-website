package org.springblade.modules.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author zjm
 * @since 2021-05-14 11:30
 */
@Getter
@AllArgsConstructor
public enum OAuthLoginEnum {
    USER_PASSWORD_ERROR(-10989, "用户名或密码不正确"),
    MISSING_INFO(-10990, "错误的用户信息"),
    INVALID_USER(-10991, "该用户已被禁用"),
    ROLE_NOT_CONFIGURED(-10992, "未配置有效的角色信息"),
    TOKEN_EXPIRE(-10993, "TOKEN已过期"),
    INVALID_TOKEN(-10994, "无效的TOKEN");

    final int code;
    final String error;
}
