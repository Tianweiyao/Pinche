package com.hodehtml.demo.model;


import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

@Data
@ToString
@ApiModel
public class UserContacts {
    private Integer id;
    private String userId;
    @ApiModelProperty("联系人名称")
    private String contactsName;
    @ApiModelProperty("联系人手机号")
    private String contactsMobile;
    @ApiModelProperty("1,直系;2,同事;3,朋友")
    private Integer type;

}