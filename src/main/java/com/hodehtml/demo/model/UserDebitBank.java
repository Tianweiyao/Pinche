package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * 银行卡
 */
@ToString
@Data
@ApiModel
public class UserDebitBank {
    private Integer userDebitId;
    @ApiModelProperty("银行标识")
    private Integer bankId;
    @ApiModelProperty("银行卡号")
    private String debitAccount;
    @ApiModelProperty("银行缩写")
    private String debitCode;
    @ApiModelProperty("持卡人姓名")
    private String debitName;
    @ApiModelProperty("银行预留手机号")
    private String debitPhone;
    @ApiModelProperty("0无效1绑卡成功2解绑")
    private Integer debitStatus;

    private String createTime;
    @ApiModelProperty("身份证号")
    private String idNo;

    private String userId;
    @ApiModelProperty("三方订单号")
    private String thirdOrderNo;
    @ApiModelProperty("0无效1默认(默认扣款账户)--废弃")
    private int defaultStatus;
    @ApiModelProperty("银行卡签约协议号")
    private String tokenId;

    private String uuid;

}