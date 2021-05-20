package org.springblade.modules.auth.exception;

import lombok.Getter;
import org.springblade.modules.auth.enums.OAuthLoginEnum;

/**
 * @author zjm
 * @since 2021-05-14 11:27
 */
public class OAuthLoginException extends RuntimeException{
    private static final long serialVersionUID = 1359767865161832954L;

    @Getter
    private final OAuthLoginEnum oAuthLoginEnum;

    public OAuthLoginException(OAuthLoginEnum oAuthLoginEnum) {
        super(oAuthLoginEnum.getError());
        this.oAuthLoginEnum = oAuthLoginEnum;
    }

    @Override
    public Throwable fillInStackTrace() {
        return this;
    }
}
