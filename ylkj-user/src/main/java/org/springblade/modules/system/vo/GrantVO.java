package org.springblade.modules.system.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author zjm
 * @since 2021-05-13 11:31
 */
@Data
public class GrantVO implements Serializable {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "roleIds集合")
    private String roleId;

    @ApiModelProperty(value = "menuIds集合")
    private List<Long> menuIds;

    @ApiModelProperty(value = "dataScopeIds集合")
    private List<Long> dataScopeIds;

    @ApiModelProperty(value = "apiScopeIds集合")
    private List<Long> apiScopeIds;
}
