package org.springblade.modules.auth.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


/**
 * @author zjm
 * @since 2021-05-14 11:31
 */
@Getter
@AllArgsConstructor
public enum BladeUserEnum {
    /**
     * web
     */
    WEB("web", 1),

    /**
     * app
     */
    APP("app", 2),
    ;

    final String name;


    final int category;
}
