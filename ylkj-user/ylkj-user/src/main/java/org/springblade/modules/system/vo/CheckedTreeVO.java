package org.springblade.modules.system.vo;

import lombok.Data;

import java.util.List;

/**
 * @author zjm
 * @since 2021-05-13 10:56
 */
@Data
public class CheckedTreeVO {
    private List<String> menu;

    private List<String> dataScope;

    private List<String> apiScope;
}
