package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@ApiModel
@Data
@ToString
public class GrabMobileContacts {
    private Integer grabId;

    private String userId;

    private String createTime;
    @ApiModelProperty("姓名")
    private String contactName;
    @ApiModelProperty("电话")
    private String contactPhone;

    private String uuid;

}