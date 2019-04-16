package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 用户婚姻信息
 */
@ApiModel
@Data
@ToString
public class UserMarriage {
    private Integer marriageId;

    private String userId;
    @ApiModelProperty("婚姻情况[0未婚 1已婚;2离异;]")
    private Byte marriageStatus;
    @ApiModelProperty("配偶姓名")
    private String spouseName;
    @ApiModelProperty("配偶电话")
    private String spouseMobile;
    @ApiModelProperty("现居省市区编码")
    private String livingAreaNo;
    @ApiModelProperty("现居详细地址")
    private String livingAddress;

    private String createTime;

}