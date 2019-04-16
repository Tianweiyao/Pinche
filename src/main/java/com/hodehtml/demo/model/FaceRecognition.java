package com.hodehtml.demo.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiOperation;
import lombok.Data;
import lombok.ToString;

@ApiModel
@Data
@ToString
public class FaceRecognition {

    private Integer id;
    private String userId;
    private String idName;
    private Byte flagSex;
    private String idNo;
    private String dateBirthday;
    @ApiModelProperty("证件地址")
    private String addrCard;
    @ApiModelProperty("签发机构")
    private String branchIssued;
    @ApiModelProperty("民族")
    private String stateId;
    @ApiModelProperty("证件有效期")
    private String startCard;
    @ApiModelProperty("相似度")
    private String beIdcard;
    private Byte resultAuth;
    private String retCode;
    private String retMsg;
    private String createTime;
    @ApiModelProperty("头像信息")
    private String urlPhotoget;
    @ApiModelProperty("正面照片")
    private String urlFrontcard;
    @ApiModelProperty("反面照片")
    private String urlBackcard;
    private String kjtAccount;
    private String uuid;


}