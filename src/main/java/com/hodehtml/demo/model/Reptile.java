package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * 运营商
 */
@ApiModel
@Data
@ToString
public class Reptile {
    private Integer id;

    private String userId;

    private String tradeNo;

    private String createTime;

    private Boolean grabType;

    private Boolean riskReportLogo;

    private String grabData;

}