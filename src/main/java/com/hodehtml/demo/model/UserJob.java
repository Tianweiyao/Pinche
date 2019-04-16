package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 用户工作信息
 */
@ApiModel
@Data
@ToString
public class UserJob {
    private Integer jobId;

    private String userId;
    @ApiModelProperty("公司名称")
    private String companyName;
    private Byte monthIncome;
    @ApiModelProperty("单位省市区")
    private String companyAreaNo;
    @ApiModelProperty("单位详细地址")
    private String companyAddress;
    @ApiModelProperty("入职期限[0 3个月内;1 3-6个月;2 6-12个月;3 1-2年;4 2-3年;5 3年以上;]")
    private Byte inductionTimes;
    private String createTime;
    private Integer lastModifyTime;
    @ApiModelProperty("从事岗位")
    private String companyPost;

}