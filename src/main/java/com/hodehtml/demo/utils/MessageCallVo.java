package com.hodehtml.demo.utils;/**
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
public class MessageCallVo {


    private String billMonth;
    private String code;
    private String message;
    private int totalsize;
    private String records;
    private String detailsid;
    private String time;
    private String peernumber;
    private String location;
    private String sendtype;
    private String msgtype;
    private String servicename;
    private int fee;



}
