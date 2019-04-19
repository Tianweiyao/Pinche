package com.hodehtml.demo.vo;/**
 * created by pht on 2019/4/19 0019
 */

import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.ToString;

/**
 * @program demo
 * @date 2019/4/19 0019
 * @author pht
 */
@Data
@ToString
@ApiModel
public class Calls {

    private String details_id;
    private String time;
    private String peer_number;
    private String location;
    private String location_type;
    private String duration;
    private String dial_type;
    private int free;

}
