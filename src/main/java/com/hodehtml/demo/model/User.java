package com.hodehtml.demo.model;/**
 * created by pht on 2019/4/11 0011
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @program demo
 * @date 2019/4/11 0011
 * @author pht
 */
@ToString
@Data
@ApiModel
public class User {

    @ApiModelProperty("uuid")
    private String uuid;
    @ApiModelProperty("手机号")
    private String user_mobile;
    @ApiModelProperty("验证码")
    private String valicode;
    @ApiModelProperty("密码")
    private String user_password;
    @ApiModelProperty("用户名")
    private String user_name;



}
