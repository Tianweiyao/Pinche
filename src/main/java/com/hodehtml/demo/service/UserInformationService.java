package com.hodehtml.demo.service;

import com.hodehtml.demo.model.*;

/**
 * created by pht on 2019/4/14 0014
 */
public interface UserInformationService {

    void insertFaceRecognition(FaceRecognition faceRecognition);

    void insertGrabMobileContacts(GrabMobileContacts record);

    void insertUserDebitBank(UserDebitBank record);

    void insertReptile(Reptile record);

    void insertUserInfo(UserInfo record);

    void insertUserJob(UserJob record);

    void insertUserMarriage(UserMarriage record);

    void insertUserLoan(UserLoan userLoan);

    void updateByPrimaryKey(UserLoan record);
}
