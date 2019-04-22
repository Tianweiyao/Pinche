package com.hodehtml.demo.controller;/**
 * created by pht on 2019/4/12 0012
 */

import com.hodehtml.demo.model.*;
import com.hodehtml.demo.service.UserInformationService;
import com.hodehtml.demo.utils.Code;
import com.hodehtml.demo.utils.LoginUtil;
import com.hodehtml.demo.vo.UserContactsVo;
import com.hodehtml.demo.vo.UserInfoVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
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
    @Autowired
    private RePayController rePayController;

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

    @ApiOperation("申请提额")
    @ResponseBody
    @RequestMapping(value = "/ApplicationRaise", method = RequestMethod.POST)
    public Map<String, Object> ApplicationRaise() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserInfo userInfo = new UserInfo();
            userInfo.setUserId(user.getUuid());
            userInfo.setRealPeriods((short) 1);
            userInfo.setAuthStatus((byte) 1);
            userInformationService.updateByPrimaryKeySelective(userInfo);
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
    @RequestMapping(value = "/insertReptile", method = RequestMethod.POST, consumes = "application/json")
    public Map<String, Object> insertReptile(@RequestBody Reptile reptile, HttpServletRequest request, HttpServletResponse response) {
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
            userJob.setUserId(user.getUuid());
            userInformationService.insertUserJob(userJob);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("查询个人工作信息")
    @ResponseBody
    @RequestMapping(value = "/SelectUserJob", method = RequestMethod.POST)
    public Map<String, Object> SelectUserJob() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserJob userJob = userInformationService.selectByPrimaryKey(user.getUuid());
            map.put("userJob", userJob);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("查询紧急联系人信息")
    @ResponseBody
    @RequestMapping(value = "/SelectUserContacts", method = RequestMethod.POST)
    public Map<String, Object> SelectUserContacts() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserContacts userContacts = userInformationService.selectUserContacts(user.getUuid());
            map.put("userContacts", userContacts);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("查入紧急联系人信息")
    @ResponseBody
    @RequestMapping(value = "/insertUserContacts", method = RequestMethod.POST)
    public Map<String, Object> insertUserContacts(@RequestBody UserContactsVo contactsVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            String[] directlyUnder = contactsVo.getDirectlyUnder();
            String[] friend = contactsVo.getFriend();
            String[] colleague = contactsVo.getColleague();
            if (directlyUnder.length > 0 && directlyUnder != null) {
                for (int i = 0; i < directlyUnder.length; i++) {
                    UserContacts userContacts = new UserContacts();
                    userContacts.setContactsMobile(directlyUnder[1]);
                    userContacts.setContactsName(directlyUnder[0]);
                    userContacts.setType(Integer.parseInt(directlyUnder[2]));
                    userContacts.setUserId(user.getUuid());
                    userInformationService.insertUserContacts(userContacts);
                }
            }
            if (friend.length > 0 && friend != null) {
                for (int i = 0; i < friend.length; i++) {
                    UserContacts userContacts = new UserContacts();
                    userContacts.setContactsMobile(friend[1]);
                    userContacts.setContactsName(friend[0]);
                    userContacts.setType(Integer.parseInt(friend[2]));
                    userContacts.setUserId(user.getUuid());
                    userInformationService.insertUserContacts(userContacts);
                }
            }
            if (colleague.length > 0 && colleague != null) {
                for (int i = 0; i < colleague.length; i++) {
                    UserContacts userContacts = new UserContacts();
                    userContacts.setContactsMobile(colleague[1]);
                    userContacts.setContactsName(colleague[0]);
                    userContacts.setType(Integer.parseInt(colleague[2]));
                    userContacts.setUserId(user.getUuid());
                    userInformationService.insertUserContacts(userContacts);
                }
            }
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }


    @ApiOperation("查询社交工具")
    @ResponseBody
    @RequestMapping(value = "/SelectUserInfoVo", method = RequestMethod.POST)
    public Map<String, Object> SelectUserInfoVo() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserInfo userInfo = userInformationService.selectByUserId(user.getUuid());
            UserInfoVo userInfoVo = new UserInfoVo();
            if (userInfo != null) {
                userInfoVo.setUserEmail(userInfo.getUserEmail());
                userInfoVo.setUserWeiXin(userInfo.getUserWeiXin());
            }
            map.put("userContacts", userInfoVo);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("查入社交工具")
    @ResponseBody
    @RequestMapping(value = "/insertUserInfoVo", method = RequestMethod.POST)
    public Map<String, Object> insertUserInfoVo(@RequestBody UserInfoVo userInfoVo) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserInfo userInfo = userInformationService.selectByUserId(user.getUuid());
            userInfo.setUserEmail(userInfoVo.getUserEmail());
            userInfo.setUserWeiXin(userInfoVo.getUserWeiXin());
            userInfo.setUserId(user.getUuid());
            userInformationService.updateByPrimaryKey(userInfo);
            map.put("userContacts", userInfoVo);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }


    @ApiOperation("查询个人信息")
    @ResponseBody
    @RequestMapping(value = "/selectInforMation", method = RequestMethod.POST)
    public Map<String, Object> selectInforMation() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserInfo userInfo = userInformationService.selectByUserId(user.getUuid());
            if (userInfo.getPeriods() == 0) {
                userInfo.setPeriods((short) 1500);
            }
            if (userInfo.getPeriods() == 1) {
                userInfo.setPeriods((short) 3000);
            }
            if (userInfo.getPeriods() == 2) {
                userInfo.setPeriods((short) 8000);
            }
            if (userInfo.getPeriods() == 3) {
                userInfo.setPeriods((short) 10000);
            }
            if (userInfo.getPeriods() == 4) {
                userInfo.setPeriods((short) 50000);
            }
            if (userInfo.getRealPeriods() == 0) {
                userInfo.setRealPeriods((short) 1500);
            }
            if (userInfo.getRealPeriods() == 1) {
                userInfo.setRealPeriods((short) 3000);
            }
            if (userInfo.getRealPeriods() == 2) {
                userInfo.setRealPeriods((short) 8000);
            }
            if (userInfo.getRealPeriods() == 3) {
                userInfo.setRealPeriods((short) 10000);
            }
            if (userInfo.getRealPeriods() == 4) {
                userInfo.setRealPeriods((short) 50000);
            }
            map.put("userInfo", userInfo);
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
            userMarriage.setUserId(user.getUuid());
            userInformationService.insertUserMarriage(userMarriage);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("查询婚姻信息")
    @ResponseBody
    @RequestMapping(value = "/SelectUserMarriage", method = RequestMethod.POST)
    public Map<String, Object> SelectUserMarriage() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserMarriage userMarriage = userInformationService.selectUserMarriage(user.getUuid());
            map.put("userMarriage", userMarriage);
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
            userLoan.setUserId(user.getUuid());
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
            userLoan.setUserId(user.getUuid());
            userInformationService.updateByPrimaryKey(userLoan);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("查询基础信息")
    @ResponseBody
    @RequestMapping(value = "/selectUserBace", method = RequestMethod.POST)
    public Map<String, Object> selectUserBace() {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserBace userBace = userInformationService.selectUserBace(user.getUuid());
            map.put("userBace", userBace);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("插入基础信息")
    @ResponseBody
    @RequestMapping(value = "/insertUserBace", method = RequestMethod.POST, consumes = "application/json")
    public Map<String, Object> insertUserBace(@RequestBody UserBace userBace) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            userBace.setUserId(user.getUuid());
            userInformationService.insertUserBace(userBace);
            map.put("message", "success");
            map.put("code", Code.successCode);
        }
        return map;
    }

    @ApiOperation("插入基础信息")
    @ResponseBody
    @RequestMapping(value = "/callBack", method = RequestMethod.GET, consumes = "application/json")
    public Map<String, Object> callBack(@RequestParam("json") String json) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            map.put("url", "json");
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
            List<UserLoan> list = userInformationService.selectUserLoan(user.getUuid());
            List<UserDebitBank> list1 = userInformationService.selectUserDebitBank(user.getUuid());
            if (list != null && list.size() > 0) {
                for (int i = 0; i < list.size(); i++) {
                    if (list1 != null && list1.size() > 0) {
                        for (int k = 0; k < list1.size(); k++) {
                            rePayController.Pay(user, list.get(i).getLoanMoney().precision(), list1.get(k).getUserDebitId(), user.getUser_mobile());
                        }
                    }
                }
            }
        }
        return map;
    }

    /**
     * 还款
     */
    @ApiOperation("还款")
    @ResponseBody
    @RequestMapping(value = "/Repayment", method = RequestMethod.GET)
    public Map<String, Object> Repayment(@RequestParam("count") int count, @RequestParam("id") Integer userDebitId) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            rePayController.Pay(user, count, userDebitId, user.getUser_mobile());
        }
        return map;
    }

}
