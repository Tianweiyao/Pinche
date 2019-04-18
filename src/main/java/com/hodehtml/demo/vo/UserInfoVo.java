package com.hodehtml.demo.vo;/**
 * created by pht on 2019/4/18 0018
 */

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.ToString;

/**
 * @program demo
 * @date 2019/4/18 0018
 * @author pht
 */
@Data
@ToString
@ApiModel
public class UserInfoVo {

    @ApiModelProperty("用户邮箱")
    private String UserEmail;
    @ApiModelProperty("用户微信")
    private String UserWeiXin;


}
