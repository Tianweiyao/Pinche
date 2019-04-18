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
public class UserContactsVo {

    @ApiModelProperty("直属亲人")
    private String[] directlyUnder;
    @ApiModelProperty("朋友")
    private String[] friend;
    @ApiModelProperty("同事")
    private String[] colleague;


}
