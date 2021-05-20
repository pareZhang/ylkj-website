package org.springblade.modules.system.entity;

import java.math.BigDecimal;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springblade.core.mp.base.BaseEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 支付信息表实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@TableName("tb_pay")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Pay对象", description = "支付信息表")
public class Pay extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    /**
     * 价格
     */
    @ApiModelProperty(value = "价格")
    private BigDecimal price;
    /**
     * 用户id
     */
    @ApiModelProperty(value = "用户id")
    private Long userId;
    /**
     * 支付类型（0:微信 1:支付宝 2:银联  ......）
     */
    @ApiModelProperty(value = "支付类型（0:微信 1:支付宝 2:银联  ......）")
    private Integer payType;
    /**
     * 消费类型
     */
    @ApiModelProperty(value = "消费类型")
    private String payFor;
    /**
     * 创建者
     */
    @ApiModelProperty(value = "创建者")
    private String createBy;


}
