package com.hodehtml.demo.utils;/**
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
public class List {

  private String billMonth;
  private String code;
  private String message;
  private int total_size;



}
