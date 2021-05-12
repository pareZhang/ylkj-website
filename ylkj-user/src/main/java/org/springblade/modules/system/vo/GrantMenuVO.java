package org.springblade.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zjm
 * @since 2021-05-12 9:48
 */
@Data
public class GrantMenuVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "roleIds集合")
    private List<Long> roleIds;

    @ApiModelProperty(value = "menuIds集合")
    private List<Long> menuIds;

}
