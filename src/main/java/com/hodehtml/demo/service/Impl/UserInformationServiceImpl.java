package com.hodehtml.demo.service.Impl;/**
 * created by pht on 2019/4/14 0014
 */

import com.hodehtml.demo.dao.*;
import com.hodehtml.demo.model.*;
import com.hodehtml.demo.dao.FaceRecognitionMapper;
import com.hodehtml.demo.service.UserInformationService;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author pht
 * @program demo
 * @date 2019/4/14 0014
 */
@Service("UserInformationService")
public class UserInformationServiceImpl implements UserInformationService {

    @Autowired
    private UserDebitBankMapper userDebitBankMapper;
    @Autowired
    private GrabMobileContactsMapper grabMobileContactsMapper;
    @Autowired
    private FaceRecognitionMapper faceRecognitionMapper;
    @Autowired
    private ReptileMapper reptileMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private UserJobMapper userJobMapper;
    @Autowired
    private UserMarriageMapper userMarriageMapper;
    @Autowired
    private UserLoanMapper userLoanMapper;
    @Autowired
    private UserBaceMapper userBaceMapper;
    @Autowired
    private UserContactsMapper userContactsMapper;

    @Override
    public void insertFaceRecognition(FaceRecognition faceRecognition) {

        faceRecognitionMapper.insert(faceRecognition);
    }


    @Override
    public void insertGrabMobileContacts(GrabMobileContacts record) {

        grabMobileContactsMapper.insert(record);
    }


    @Override
    public void insertUserDebitBank(UserDebitBank record) {

        userDebitBankMapper.insert(record);
    }


    @Override
    public void insertReptile(Reptile record) {

        reptileMapper.insert(record);
    }


    @Override
    public void insertUserInfo(UserInfo record) {

        userInfoMapper.insert(record);
    }


    @Override
    public void insertUserJob(UserJob record) {

        userJobMapper.insert(record);
    }


    @Override
    public void insertUserMarriage(UserMarriage record) {

        userMarriageMapper.insert(record);
    }

    /**
     * 创建账单信息
     *
     * @param userLoan
     */
    @Override
    public void insertUserLoan(UserLoan userLoan) {

        userLoanMapper.insert(userLoan);
    }

    /**
     * 借款申请续期
     *
     * @param record
     */
    @Override
    public void updateByPrimaryKey(UserLoan record) {

        userLoanMapper.updateByPrimaryKey(record);
    }

    @Override
    public UserInfo selectByUserId(String userId) {

     UserInfo userInfo = userInfoMapper.selectByUserId(userId);
     return userInfo;
    }

    @Override
    public void updateByPrimaryKeySelective(UserInfo record){

        userInfoMapper.updateByPrimaryKeySelective(record);
    };

    @Override
    public UserBace selectUserBace(String userId){

      UserBace userBace = userBaceMapper.selectByPrimaryKey(userId);
      return userBace;
    };

    @Override
    public void insertUserBace(UserBace userBace){

        userBaceMapper.insert(userBace);
    };

    @Override
    public UserJob selectByPrimaryKey(String UserId){

     UserJob userJob = userJobMapper.selectByPrimaryKey(UserId);
     return userJob;
    };

    @Override
    public UserMarriage selectUserMarriage(String UserId){

       UserMarriage userMarriage = userMarriageMapper.selectByPrimaryKey(UserId);
       return userMarriage;
    };

    @Override
    public UserContacts selectUserContacts( String UserId){

      UserContacts userContacts = userContactsMapper.selectByPrimaryKey(UserId);
      return userContacts;
    };

    @Override
    public void insertUserContacts(UserContacts record){

        userContactsMapper.insert(record);
    };

    @Override
    public void updateByPrimaryKey(UserInfo record){

        userInfoMapper.updateByPrimaryKey(record);
    };


}
