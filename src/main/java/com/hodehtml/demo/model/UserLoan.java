package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

import java.math.BigDecimal;

@ApiModel
@Data
@ToString
public class UserLoan {
    private String loanNo;

    private String orderId;

    private String userId;

    private Integer bankId;

    private BigDecimal loanMoney;

    private Short loanDays;

    private BigDecimal loanRate;

    private Integer loanTime;

    private Byte payStatus;

    private Integer payTime;

    private Short overdueDays;

    private BigDecimal totalInterest;

    private BigDecimal totalUninterest;

    private BigDecimal totalLatefee;

    private BigDecimal repaymentCapital;

    private BigDecimal repaymentInterest;

    private BigDecimal repaymentLatefee;

    private Integer repaymentSuccessTime;

    private Byte repayStatus;

    private String billNo;

    private Byte deviceType;

    private Byte partnerUseFlg;

    private Integer agencyId;

    private Byte signStatus;

    private Byte stageTimes;

    private Integer repayIssue;

    private Byte applyStatus;

    private String signRemark;

    private String signUrl;

    private String payEvdience;

    private Byte repayGrantStatus;

    private String signService;

    private String signEvid;

    private String signKey;

    private Integer signTime;

    private Integer oldLoanTime;

    private String applyNo;

}