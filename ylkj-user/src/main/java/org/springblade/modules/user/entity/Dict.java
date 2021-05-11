package org.springblade.modules.user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 字典表实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@TableName("tb_dict")
@ApiModel(value = "Dict对象", description = "字典表")
public class Dict implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 父主键
     */
    @ApiModelProperty(value = "父主键")
    private Long parentId;
    /**
     * 字典码
     */
    @ApiModelProperty(value = "字典码")
    private String code;
    /**
     * 字典值
     */
    @ApiModelProperty(value = "字典值")
    private String dictKey;
    /**
     * 字典名称
     */
    @ApiModelProperty(value = "字典名称")
    private String dictValue;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 字典备注
     */
    @ApiModelProperty(value = "字典备注")
    private String remark;
    /**
     * 是否已封存
     */
    @ApiModelProperty(value = "是否已封存")
    private Integer isSealed;

    /**
     * 状态[1:正常]
     */
    @ApiModelProperty(value = "业务状态")
    private Integer status;

    /**
     * 状态[0:未删除,1:删除]
     */
    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;

}
