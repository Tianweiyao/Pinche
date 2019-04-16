package com.hodehtml.demo.controller;/**
 * created by pht on 2019/4/12 0012
 */

import com.hodehtml.demo.model.*;
import com.hodehtml.demo.service.UserInformationService;
import com.hodehtml.demo.utils.Code;
import com.hodehtml.demo.utils.LoginUtil;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author pht
 * @program demo
 * @date 2019/4/12 0012
 */
@Controller
@RequestMapping("UserInformation")
public class UserInformationController {

    Logger logger = LoggerFactory.getLogger(UserInformationController.class);

    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private UserInformationService userInformationService;

    @ApiOperation("插入人脸识别信息")
    @ResponseBody
    @RequestMapping(value = "/insertGrabFace", method = RequestMethod.GET)
    public Map<String, Object> insertGrabFaceRecognition(@RequestParam("json") String json) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            FaceRecognition grabFaceRecognition = new FaceRecognition();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject personObject = jsonObject.getJSONObject("GrabFaceRecognition");
                grabFaceRecognition.setUuid(UUID.randomUUID().toString().replace("-", ""));
                grabFaceRecognition.setUserId(personObject.getString("userId"));
                grabFaceRecognition.setIdName(personObject.getString("idName"));
                grabFaceRecognition.setFlagSex((byte) personObject.getInt("flagSex"));
                grabFaceRecognition.setIdNo(personObject.getString("idNo"));
                grabFaceRecognition.setDateBirthday(personObject.getString("birthday"));
                grabFaceRecognition.setAddrCard(personObject.getString("addrCard"));
                grabFaceRecognition.setBranchIssued(personObject.getString("branchIssued"));
                grabFaceRecognition.setStateId(personObject.getString("stateId"));
                grabFaceRecognition.setStartCard(personObject.getString("startCard"));
                grabFaceRecognition.setBeIdcard(personObject.getString("beIdcard"));
                grabFaceRecognition.setCreateTime(personObject.getString("createTime"));
                grabFaceRecognition.setResultAuth((byte) personObject.getInt("resultAuth"));
                grabFaceRecognition.setRetCode(personObject.getString("retCode"));
                grabFaceRecognition.setRetMsg(personObject.getString("retMsg"));
                grabFaceRecognition.setUrlPhotoget(personObject.getString("urlPhotoget"));
                grabFaceRecognition.setUrlBackcard(personObject.getString("urlBackcard"));
                grabFaceRecognition.setUrlFrontcard(personObject.getString("urlFrontcard"));
                /*grabFaceRecognition.setKjtAccount(personObject.getString("kjtAccount"));*/
            } catch (Exception e) {
                logger.info("异常==" + e);
            }
            userInformationService.insertFaceRecognition(grabFaceRecognition);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("插入个人手机通讯录")
    @ResponseBody
    @RequestMapping(value = "/insertGrabMobile", method = RequestMethod.GET)
    public Map<String, Object> insertGrabMobileContacts(@RequestParam("json") String json) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            GrabMobileContacts grabMobileContacts = new GrabMobileContacts();
            try {
                JSONObject jsonObject = new JSONObject(json);
                JSONObject personObject = jsonObject.getJSONObject("GrabMobileContacts");
                grabMobileContacts.setUuid(UUID.randomUUID().toString().replace("-", ""));
                grabMobileContacts.setContactName(personObject.getString("contactName"));
                grabMobileContacts.setContactPhone(personObject.getString("contactPhone"));
                grabMobileContacts.setCreateTime(personObject.getString("createTime"));
                grabMobileContacts.setUserId(personObject.getString("userId"));
            } catch (Exception e) {
                logger.info("异常==" + e);
            }
            userInformationService.insertGrabMobileContacts(grabMobileContacts);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("插入银行卡信息")
    @ResponseBody
    @RequestMapping(value = "/insertUserDebitBank", method = RequestMethod.GET)
    public Map<String, Object> insertUserDebitBank(@RequestParam("json") String json) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserDebitBank userDebitBank = new UserDebitBank();
            JSONObject jsonObject = new JSONObject(json);
            JSONObject personObject = jsonObject.getJSONObject("UserDebitBank");
            userDebitBank.setBankId(personObject.getInt("bankId"));
            userDebitBank.setCreateTime(personObject.getString("createTime"));
            userDebitBank.setDebitAccount(personObject.getString("debitAccount"));
            userDebitBank.setDebitCode(personObject.getString("debitCode"));
            userDebitBank.setDebitName(personObject.getString("debitName"));
            userDebitBank.setDebitPhone(personObject.getString("debitPhone"));
            userDebitBank.setDebitStatus(personObject.getInt("debitStatus"));
            userDebitBank.setIdNo(personObject.getString("idNo"));
            userDebitBank.setThirdOrderNo(personObject.getString("thirdOrderNo"));
            userDebitBank.setTokenId(personObject.getString("tokenId"));
            userDebitBank.setUserId(personObject.getString("userId"));
            userDebitBank.setDefaultStatus(personObject.getInt("defaultStatus"));
            userInformationService.insertUserDebitBank(userDebitBank);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("")
    @ResponseBody
    @RequestMapping(value = "/insertReptile", method = RequestMethod.POST)
    public Map<String, Object> insertReptile(@RequestBody Reptile reptile) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            userInformationService.insertReptile(reptile);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("插入个人信息")
    @ResponseBody
    @RequestMapping(value = "/insertUserInfo", method = RequestMethod.POST)
    public Map<String, Object> insertUserInfo(@RequestBody UserInfo userInfo) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            userInfo.setAuthStatus((byte) 0);
            userInfo.setPeriods((short) 2);
            userInformationService.insertUserInfo(userInfo);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("插入个人工作信息")
    @ResponseBody
    @RequestMapping(value = "/insertUserJob", method = RequestMethod.POST)
    public Map<String, Object> insertUserJob(@RequestBody UserJob userJob) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            userInformationService.insertUserJob(userJob);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("插入婚姻信息")
    @ResponseBody
    @RequestMapping(value = "/insertUserMarriage", method = RequestMethod.POST)
    public Map<String, Object> insertUserMarriage(@RequestBody UserMarriage userMarriage) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            userInformationService.insertUserMarriage(userMarriage);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("创建账单信息")
    @ResponseBody
    @RequestMapping(value = "/insertUserLoan", method = RequestMethod.POST)
    public Map<String, Object> insertUserLoan(@RequestBody UserLoan userLoan) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            userInformationService.insertUserLoan(userLoan);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }


    @ApiOperation("借款申请续期")
    @ResponseBody
    @RequestMapping(value = "/updateByPrimaryKey", method = RequestMethod.POST)
    public Map<String, Object> updateByPrimaryKey(@RequestBody UserLoan userLoan) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            userInformationService.updateByPrimaryKey(userLoan);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    /**
     * 定时代扣 每天12点执行一次
     *
     * @return
     */
    @Scheduled(cron = "0 0 12 * * ?")
    public Map<String, Object> daiFu() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {

        }
        return map;
    }

    /**
     * 还款
     */
    @ApiOperation("还款")
    @ResponseBody
    @RequestMapping(value = "/Repayment", method = RequestMethod.POST)
    public Map<String, Object> Repayment() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {

        }
        return map;
    }


}
