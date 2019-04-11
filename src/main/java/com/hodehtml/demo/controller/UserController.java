package com.hodehtml.demo.controller;/**
 * created by pht on 2019/4/11 0011
 */

import com.hodehtml.demo.utils.DesUtils;
import com.hodehtml.demo.utils.ValidatorUtils;
import com.hodehtml.demo.vo.EduException;
import com.hodehtml.demo.vo.ServiceCoreVO;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program demo
 * @date 2019/4/11 0011
 * @author pht
 */
@Controller
@RequestMapping("User")
public class UserController extends BaseAction {

    protected static final Log log = LogFactory.getLog(UserController.class);

    @Resource
    private UserService userService;
    @Resource
    private ActivityService activityService;
    @Resource
    private UserTokenService userTokenService;

    /**
     * 判断用户账号是否存在
     *
     * @return
     * @throws EduException
     */
    @RequestMapping("/exists")
    @ResponseBody
    public Map<String, Object> existsV100(@RequestParam("user_mobile") String userMobile) throws EduException {
        //用户手机号判断
        ValidatorUtils.isLoginMobile(userMobile);
        //判断注册手机号是否存在  1存在 2不存在
        if (userService.userMobileExists(userMobile)) {
            resobject.put("exists", "1");
        } else {
            resobject.put("exists", "2");
        }
        return resobject;
    }


    /**
     * 注册
     *
     * @return
     * @throws EduException
     */
    @RequestMapping("/exist")
    @ResponseBody
    public Map<String, Object> existV100() throws EduException {
        //用户信息校验
        String userMobile = getRequestContentValue("user_mobile");
        ValidatorUtils.isLoginMobile(userMobile);
        if (userService.userMobileExists(userMobile)) {
            throw new EduException(200102);
        }
        //验证码校验
        String valicode = getRequestContentValue("valicode");
        if (!ValidatorUtils.isSms(valicode)) {
            throw new EduException(200108);
        }
        //是否是代理人的二维码的用户
        String qrcodeKey = "";
        String qrcode_key = getRequestContentValue("qrcode_key");
        if (ValidatorUtils.isNotNull(qrcode_key)) {
            qrcodeKey = qrcode_key;
        }
        //登录密码
        String password = getRequestContentValue("user_password");
        String realPwd = null;
        try {
            //密码解密
            realPwd = DesUtils.decrypt(password, desKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" reg pwd:" + password + " deskey:" + desKey, e);
            throw new EduException(110075);
        }
        if (!ValidatorUtils.isPassword(realPwd)) {
            throw new EduException(200111);
        }
        //判断是否若密码
        ValidatorUtils.isWeakPassword(realPwd);

        //推荐人手机号
        String recommendMobile = getRequestContentValue("recommend_mobile");
        if (ValidatorUtils.isNotNull(recommendMobile) && !ValidatorUtils.isMobile(recommendMobile)) {
            throw new EduException(200110);
        }

        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("userMobile", userMobile);
        params.put("valicode", valicode);
        params.put("qrcodeKey", qrcodeKey);
        params.put("password", realPwd);
        params.put("recommendMobile", recommendMobile);
        params.put("devicePort", devicePort);
        params.put("trackid", trackid);
        log.info("注册参数信息-" + params.toString());
        userService.reg(params);
        return loginV100();
    }

    /**
     * 修改密码
     *
     * @return
     * @throws EduException
     */
    @RequestMapping("/iforgot")
    @ResponseBody
    public Map<String, Object> iforgotV100() throws EduException {
        //用户信息校验
        String userMobile = getRequestContentValue("user_mobile");
        ValidatorUtils.isLoginMobile(userMobile);
        if (!userService.userMobileExists(userMobile)) {
            throw new EduException(200104);
        }
        //验证码校验
        String valicode = getRequestContentValue("valicode");
        if (!ValidatorUtils.isSms(valicode)) {
            throw new EduException(200108);
        }

        //登录密码
        String password = getRequestContentValue("user_password");
        String realPwd = null;
        try {
            realPwd = DesUtils.decrypt(password, desKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" ifrogot pwd:" + password + " deskey:" + desKey, e);
            throw new EduException(110075);
        }
        if (!ValidatorUtils.isPassword(realPwd)) {
            throw new EduException(200111);
        }
        //判断是否若密码
        ValidatorUtils.isWeakPassword(realPwd);

        //真实操作
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("userMobile", userMobile);
        params.put("valicode", valicode);
        params.put("password", realPwd);
        log.info("修改密码参数信息-" + params.toString());
        userService.iforgot(params);
        resobject.put("message","success");
        return resobject;
    }


    /**
     * 登录
     *
     * @return
     * @throws EduException
     */
    @RequestMapping("/login")
    @ResponseBody
    public Map<String,Object> loginV100() throws EduException {
        //用户信息校验
        String userMobile = getRequestContentValue("user_mobile");
        ValidatorUtils.isLoginMobile(userMobile);

        //登录密码
        String password = getRequestContentValue("user_password");
        String realPwd = null;
        try {
            //登录密码解密
            realPwd = DesUtils.decrypt(password, desKey);
        } catch (Exception e) {
            e.printStackTrace();
            log.error(" login pwd:" + password + " deskey:" + desKey, e);
            throw new EduException(110075);
        }
        if (!ValidatorUtils.isPassword(realPwd)) {
            throw new EduException(200111);
        }
        Map<String, String> params = new LinkedHashMap<String, String>();
        params.put("userMobile", userMobile);
        params.put("password", realPwd);
        params.put("devicePort", devicePort);
        params.put("reqip", reqip);
        params.put("device_version", getRequestContentValue("device_version"));
        params.put("longitude", getRequestContentValue("longitude"));
        params.put("latitude", getRequestContentValue("latitude"));
        params.put("device_id", getRequestContentValue("device_id"));
        params.put("device_os", getRequestContentValue("device_os"));
        params.put("device_name", getRequestContentValue("device_name"));
        params.put("device_detail", getRequestContentValue("device_detail"));
        params.put("jiguang_id", getRequestContentValue("registration_id"));
        Map<String, Object> result = userService.login(params);
        resobject.putAll(result);
		/*try {
			//新增国庆活动
			params = new LinkedHashMap<String, String>();
			params.put("userId", result.get("user_id")+"");
			params.put("act_code", "guoqing2017");
			activityService.login(params);
		} catch (Exception e) {
			log.error("加入活动信息失败",e);
		}*/
        return resobject;
    }

}
