package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel
public class FullOrder {
    private Integer id;

    private String mobile;

    private String code;

    private String message;

    private String name;

    private String idcard;

    private String carrier;

    private String province;

    private String city;

    private String openTime;

    private String level;

    private String packageName;

    private Integer state;

    private Integer availableBalance;

    private String lastmodifytime;

}