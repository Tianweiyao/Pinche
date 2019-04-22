package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

@ApiModel
@Data
@ToString
public class MessageCalls {
    private Integer id;

    private String mobile;

    private String billmonth;

    private String code;

    private String message;

    private Integer totalsize;

    private String detailsid;

    private String time;

    private String peernumber;

    private String location;

    private String sendtype;

    private String msgtype;

    private String servicename;

    private Integer fee;

}