package com.hodehtml.demo.controller;/**
 * created by pht on 2019/4/11 0011
 */

import com.hodehtml.demo.model.User;
import com.hodehtml.demo.model.UserInfo;
import com.hodehtml.demo.service.UserInformationService;
import com.hodehtml.demo.service.UserService;
import com.hodehtml.demo.utils.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.*;

/**
 * @author pht
 * @program demo
 * @date 2019/4/11 0011
 */
@Controller
@RequestMapping("User")
public class UserController extends BaseAction {

    Logger log = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;
    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private UserInformationService userInformationService;


    @ApiOperation("判断用户账号是否存在")
    @RequestMapping(value = "/exists", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> existsV100(@RequestParam("user_mobile") String userMobile) throws EduException {
        Map<String, Object> map = new HashMap<String, Object>();
        //用户手机号判断
        ValidatorUtils.isLoginMobile(userMobile);
        //判断注册手机号是否存在  1存在 2不存在
        if (userService.userMobileExists(userMobile)) {
            map.put("exists", "1");
        } else {
            map.put("exists", "2");
        }
        return map;
    }


    @ApiOperation("注册")
    @RequestMapping(value = "/exist", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> existV100(HttpServletRequest request,@RequestParam("user_mobile") String user_mobile,@RequestParam("valicode") String valicode,@RequestParam("user_password") String user_password) throws EduException {
        Map<String, Object> map = new HashMap<String, Object>();
        //用户信息校验
        ValidatorUtils.isLoginMobile(user_mobile);
        if (userService.userMobileExists(user_mobile)) {
            throw new EduException(200102);
        }
        //验证码校验
        if (!ValidatorUtils.isSms(valicode)) {
            throw new EduException(200108);
        }
        String value = JedisUtil.get(user_mobile);
        if (!value.equals(valicode)){
            throw new EduException(200108);
        }
        //登录密码
        String password = user_password;
        String password1 = password;
        if (!ValidatorUtils.isPassword(password)) {
            throw new EduException(200111);
        }
        String userId = UUID.randomUUID().toString().replace("-","");
        //判断是否若密码
        ValidatorUtils.isWeakPassword(password);
        User user = new User();
        user.setUser_mobile(user_mobile);
        user.setValicode(valicode);
        user.setUuid(userId);
        user.setUser_password(MD5.md5(password,"utf-8"));
        user.setToken(MD5.md5(user.getUser_mobile()+password+new Date(),"utf-8"));
        UserInfo userInfo = new UserInfo();
        userInfo.setUserId(userId);
        userInfo.setAuthStatus((byte)0);
        userInfo.setAuthStep((byte)0);
        userInfo.setCreateTime(new Date().toString());
        userInfo.setPeriods((short)2);
        userInfo.setRealPeriods((short)0);
        userInfo.setLoanDays(7);
        userInformationService.insertUserInfo(userInfo);
        log.info("注册参数信息-" + user.toString());
        userService.reg(user);
        user.setUser_password(password1);
        return loginV100(request,user_mobile,password1);
    }


    @ApiOperation("修改密码")
    @RequestMapping(value = "/iforgot", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> iforgotV100(@RequestBody User user) throws EduException {
        //用户信息校验
        Map<String, Object> map = new HashMap<String, Object>();
        String userMobile = user.getUser_mobile();
        ValidatorUtils.isLoginMobile(userMobile);
        if (!userService.userMobileExists(userMobile)) {
            throw new EduException(200104);
        }
        //验证码校验
        String valicode = user.getValicode();
        if (!ValidatorUtils.isSms(valicode)) {
            throw new EduException(200108);
        }
        String value = JedisUtil.get(user.getUser_mobile());
        if (!value.equals(user.getValicode())){
            throw new EduException(200108);
        }
        //登录密码
        String password = user.getUser_password();
        /*String realPwd = null;
        try {
            realPwd = DesUtils.decrypt(password, desKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" ifrogot pwd:" + password + " deskey:" + desKey, e);
            throw new EduException(110075);
        }*/
        if (!ValidatorUtils.isPassword(password)) {
            throw new EduException(200111);
        }
        user.setUser_password(MD5.md5(password,"utf-8"));
        user.setToken(MD5.md5(user.getUser_mobile()+password+new Date(),"utf-8"));
        log.info("修改密码参数信息-" + user.toString());
        userService.iforgot(user);
        map.put("message", "success");
        return map;
    }


    @ApiOperation("登录")
    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> loginV100(HttpServletRequest request,@RequestParam("user_mobile") String user_mobile,@RequestParam("user_password") String user_password) throws EduException {
        Map<String, Object> map = new HashMap<String, Object>();
        //用户信息校验
        String userMobile = user_mobile;
        ValidatorUtils.isLoginMobile(userMobile);
        HttpSession session = request.getSession();
        //登录密码
        String password = user_password;
        /*String realPwd = null;
        try {
            //登录密码解密
            realPwd = DesUtils.decrypt(password, desKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" login pwd:" + password + " deskey:" + desKey, e);
            throw new EduException(110075);
        }*/
        if (!ValidatorUtils.isPassword(password)) {
            throw new EduException(200111);
        }
        User user = new User();
        user.setUser_mobile(userMobile);
        user.setUser_password(MD5.md5(password,"utf-8"));
        User user1 = userService.login(user);
        if (user1 != null) {
            session.setAttribute("token", user1.getToken());
            session.setAttribute("user", user1);
            session.setMaxInactiveInterval(30 * 60);//20分钟失效
            loginUtil.LoginUtil(session);
            JedisUtil.addObject(user1.getToken(),user1);
            JedisUtil.disableTime(user1.getToken(),user1.toString(),6000);
            System.out.println("session" + session.getAttribute("token"));
            map.put("message", "登录成功");
            map.put("code", Code.successCode);
        } else if (user1 == null) {
            map.put("message", "手机号或密码不能正确");
            map.put("code", Code.failCode);
        }
        return map;
    }


    @ApiOperation("发送短信")
    @RequestMapping(value = "/smSsend", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> smSsend(@RequestParam("user_mobile") String user_mobile) {
        Boolean result = null;
        Map<String, Object> map = new HashMap<String, Object>();
        Double money = null;
        String title = "注册短信发送";
        String verifyCode = String
                .valueOf(new Random().nextInt(899999) + 100000);//生成短信验证码
        //手机号  可以多个手机号
        String content = "您的验证码是：" + verifyCode;
        result = SmsUtil.send(user_mobile, content, title); //发送短信
        System.out.println(result);
        if (result) {
            boolean result1 = JedisUtil.existsKey(user_mobile);
            if (result1) { //如果已存在
                JedisUtil.delKey(user_mobile);
                JedisUtil.addValue(user_mobile, verifyCode);
            } else {
                JedisUtil.addValue(user_mobile, verifyCode);
            }
            map.put("message", "success");
            map.put("code", Code.successCode);
        }else {
            map.put("message", "短信发送失败");
            map.put("code", Code.failCode);
        }
        return map;
    }




}
