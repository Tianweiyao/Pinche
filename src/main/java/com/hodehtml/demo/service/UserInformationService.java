package com.hodehtml.demo.service;

import com.hodehtml.demo.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * created by pht on 2019/4/14 0014
 */
public interface UserInformationService {

    void insertFaceRecognition(FaceRecognition faceRecognition);

    void insertGrabMobileContacts(GrabMobileContacts record);

    void insertUserDebitBank(UserDebitBank record);

    void insertReptile(Reptile record);

    void insertUserInfo(UserInfo record);

    UserInfo selectByUserId(String userId);

    UserJob selectByPrimaryKey(String UserId);

    UserMarriage selectUserMarriage(String UserId);

    void insertUserJob(UserJob record);

    void insertUserMarriage(UserMarriage record);

    void insertUserLoan(UserLoan userLoan);

    void updateByPrimaryKey(UserLoan record);

    void updateByPrimaryKeySelective(UserInfo record);

    UserBace selectUserBace(String userId);

    void insertUserBace(UserBace userBace);

    UserContacts selectUserContacts( String UserId);

    void insertUserContacts(UserContacts record);

    void updateByPrimaryKey(UserInfo record);
}
