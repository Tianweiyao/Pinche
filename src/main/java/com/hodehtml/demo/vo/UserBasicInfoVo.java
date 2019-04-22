package com.hodehtml.demo.vo;/**
 * created by pht on 2019/4/22 0022
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @program demo
 * @date 2019/4/22 0022
 * @author pht
 */
@Data
@ToString
@ApiModel
public class UserBasicInfoVo {

    private String name;
    private String idCard;
    private String nameFromCustom;
    private String idCardFromCustom;
    private String mobile;
    private String gender;
    private String age;
    private String constellation;
    private String email;
    private String address;
    private String native_place;
    private String live_address;
    private String work_address;

}
