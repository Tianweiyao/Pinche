package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel
public class UserBace {
    private Integer id;

    private String userId;
    @ApiModelProperty("门牌号")
    private String numberPlate;
    private String school;
    @ApiModelProperty("专业")
    private String major;
    @ApiModelProperty("学历")
    private String education;

}