package org.springblade.modules.system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.io.Serializable;

/**
 * 菜单表实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@TableName("tb_menu")
@ApiModel(value = "Menu对象", description = "菜单表")
public class Menu implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 父级菜单
     */
    @ApiModelProperty(value = "父级菜单")
    private Long parentId;
    /**
     * 菜单编号
     */
    @ApiModelProperty(value = "菜单编号")
    private String code;
    /**
     * 菜单名称
     */
    @ApiModelProperty(value = "菜单名称")
    private String name;
    /**
     * 菜单别名
     */
    @ApiModelProperty(value = "菜单别名")
    private String alias;
    /**
     * 请求地址
     */
    @ApiModelProperty(value = "请求地址")
    private String path;
    /**
     * 菜单资源
     */
    @ApiModelProperty(value = "菜单资源")
    private String source;
    /**
     * 排序
     */
    @ApiModelProperty(value = "排序")
    private Integer sort;
    /**
     * 菜单类型
     */
    @ApiModelProperty(value = "菜单类型")
    private Integer category;
    /**
     * 操作按钮类型
     */
    @ApiModelProperty(value = "操作按钮类型")
    private Integer action;
    /**
     * 是否打开新页面
     */
    @ApiModelProperty(value = "是否打开新页面")
    private Integer isOpen;
    /**
     * 备注
     */
    @ApiModelProperty(value = "备注")
    private String remark;
    /**
     * 状态[0:未删除,1:删除]
     */
    @TableLogic
    @ApiModelProperty(value = "是否已删除")
    private Integer isDeleted;

}
