package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 用户信息
 */
@ApiModel
@Data
@ToString
public class UserInfo {
    private Integer infoId;
    private String userId;
    private String userName;
    private Byte userSex;
    private String cardNo;
    @ApiModelProperty("出生年份")
    private String userBirthYear;
    @ApiModelProperty("生日")
    private String userBirthday;
    private String province;
    private String city;
    private String area;
    @ApiModelProperty("现居省市区")
    private String livingAreaNo;
    @ApiModelProperty("现居详细地址")
    private String livingAddress;
    @ApiModelProperty("婚姻状况[0未婚;1已婚;2离异;3丧偶;4再婚;5复婚;]")
    private Byte maritalStatus;
    @ApiModelProperty("现居时长[0 3个月内;1 3-6个月;2 6-12个月;3 1-3年;4 3年以上;]")
    private Short livingTimes;
    private String userEmail;
    private Byte authStatus;
    private Byte authStep;
    private String createTime;
    private Integer lastUpdateTime;
    private String checkNo;
    private String machineRemark;
    private Integer loanTimes;
    private Integer checkStatus;
    private Byte distributionStatus;
    private Byte connectStatus;
    private Short income;
    private Short periods;
    private Short realPeriods;
    @ApiModelProperty("借款天数")
    private Integer loanDays;
    private String userJinji;

}