package org.springblade.modules.user.entity;

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
 * 客户端表实体类
 *
 * @author zjm
 * @since 2021-05-10
 */
@Data
@TableName("tb_client")
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "Client对象", description = "客户端表")
public class AuthClient extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 主键
     */
    @ApiModelProperty(value = "主键")
    @TableId(value = "id", type = IdType.ASSIGN_ID)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;
    private String name;
    private String provider;
    /**
     * 客户端id
     */
    @ApiModelProperty(value = "客户端id")
    private String clientId;
    /**
     * 客户端密钥
     */
    @ApiModelProperty(value = "客户端密钥")
    private String clientSecret;
    /**
     * 授权范围
     */
    @ApiModelProperty(value = "授权范围")
    private String scope;
    /**
     * 授权类型
     */
    @ApiModelProperty(value = "授权类型")
    private String grantType;
    /**
     * 回调地址
     */
    @ApiModelProperty(value = "回调地址")
    private String redirectUri;
    /**
     * 令牌过期秒数
     */
    @ApiModelProperty(value = "令牌过期秒数")
    private Integer tokenValidity;
    /**
     * 说明
     */
    @ApiModelProperty(value = "说明")
    private String remark;
    /**
     * 简介
     */
    @ApiModelProperty(value = "简介")
    private String introduction;


}
