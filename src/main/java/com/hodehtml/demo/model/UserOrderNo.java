package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel
public class UserOrderNo {

    private Integer id;

    private String userId;

    private String orderId;

    private String authUrl;

    private String tradeNo;

}