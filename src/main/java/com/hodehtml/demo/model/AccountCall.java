package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel
public class AccountCall {
    private Integer id;

    private String mobile;

    private String billmonth;

    private String code;

    private String message;

    private Integer totalSize;

    private String detailsId;

    private String time;

    private String peerNumber;

    private String location;

    private String locationType;

    private String duration;

    private String dialType;

    private Integer fee;

}