package com.hodehtml.demo.controller;/**
 * created by pht on 2019/4/17 0017
 */

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;

import com.google.gson.JsonObject;
import com.hodehtml.demo.dao.UserDebitBankMapper;
import com.hodehtml.demo.dao.UserInfoMapper;
import com.hodehtml.demo.model.*;
import com.hodehtml.demo.service.UserInformationService;
import com.hodehtml.demo.service.UserService;
import com.hodehtml.demo.utils.*;
import com.hodehtml.demo.vo.BasicinFocheckItems;
import com.hodehtml.demo.vo.Calls;
import com.hodehtml.demo.vo.EduException;
import com.hodehtml.demo.vo.UserBasicInfoVo;
import io.swagger.annotations.ApiOperation;
import io.swagger.models.auth.In;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author pht
 * @program demo
 * @date 2019/4/17 0017
 */
@Controller
@RequestMapping("RePay")
public class RePayController {

    private Logger logger = LoggerFactory.getLogger(RePayController.class);

    @Autowired
    private LoginUtil loginUtil;
    @Autowired
    private UserInformationService userInformationService;
    @Autowired
    private UserDebitBankMapper userDebitBankMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;
    @Autowired
    private YunYingShang yunYingShang;
    @Autowired
    private UserService userService;

    private final String ENCODEING = "UTF-8";

    public void requestPost(String url, List<NameValuePair> params) throws ClientProtocolException, IOException {
        CloseableHttpClient httpclient = HttpClientBuilder.create().build();

        HttpPost httppost = new HttpPost(url);
        httppost.setEntity(new UrlEncodedFormEntity(params, ENCODEING));

        CloseableHttpResponse response = httpclient.execute(httppost);
        System.out.println(response.toString());

        HttpEntity entity = response.getEntity();
        String jsonStr = EntityUtils.toString(entity, "utf-8");
        System.out.println(jsonStr);

        httppost.releaseConnection();
    }


    @ApiOperation("代付")
    @RequestMapping(value = "/pay", method = RequestMethod.GET)
    @ResponseBody
    public void Pay(@RequestParam("count") Integer count, @RequestParam("id") Integer userDebitId, @RequestParam("mobile") String
            mobile1) {
        Map<String, Object> map = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserDebitBank userDebitBank = userDebitBankMapper.selectByPrimaryKey(userDebitId);
            UserInfo userInfo = userInfoMapper.selectByUserId(user.getUuid());
            String ver = "1.0";
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String date = format.format(new Date());
            String merdt = date;
            String orderno = UUID.randomUUID().toString().replace("-", "");
            int bankno = userDebitBank.getBankId();
            String accntno = userDebitBank.getDebitAccount();
            String accntnm = userDebitBank.getDebitName();
            int amt = count;
            String entseq = "";
            String memo = "";
            String mobile = mobile1;
            String certtp = "";
            String certno = userInfo.getCardNo();
            try {
                String xml = "<?xml version=\"1.0\" encoding=\"utf-8\" standalone=\"yes\"?>" +
                        "<payforreq>" +
                        "<ver>1.00</ver>" +
                        "<merdt>" + merdt + "</merdt>" +
                        "<orderno>" + System.currentTimeMillis() + "</orderno>" +
                        "<bankno>" + bankno + "</bankno>" +
                        "<cityno>2900</cityno>" +
                        //"<branchnm>中国银行股份有限公司北京西单支行</branchnm>"+
                        "<accntno>" + accntno + "</accntno>" +
                        "<accntnm>" + accntnm + "</accntnm>" +
                        "<amt>" + amt + "</amt>" +
                        "<entseq>" + entseq + "</entseq>"
                        + "<memo>" + memo + "</memo>"
                        + "<mobile>" + mobile + "</mobile>"
                        + "<certtp>0</certtp>"
                        + "<certno>" + certno + "</certno>"
                        + "</incomeforreq>";
                String macSource = "0002900F0345178|123456|" + "payforreq" + "|" + xml;
                String mac = MD5.md5(macSource, "UTF-8").toUpperCase();
                String loginUrl = "https://fht-test.fuiou.com/fuMer/req.do";
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                params.add(new BasicNameValuePair("merid", "0002900F0345178"));
                params.add(new BasicNameValuePair("reqtype", "payforreq"));
                params.add(new BasicNameValuePair("xml", xml));
                params.add(new BasicNameValuePair("mac", mac));
                this.requestPost(loginUrl, params);
            } catch (ClientProtocolException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 查询用户下银行卡
     *
     * @return
     */
    @RequestMapping(value = "/selectBank", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectBank() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            String verion = "1.0";
            String mchntcd = "0002900F0096235";//商户代码
            String sign = verion + "|" + mchntcd + "|" + user.getUuid() + "|" + "5old71wihg2tqjug9kkpxnhx9hiujoqj";
            String url = "http://www-1.fuioupay.com:18670/mobile_pay/newpropay/bindQuery.pay";
            map.put("verion", verion);
            map.put("mchntcd", mchntcd);
            map.put("userId", user.getUuid());
            map.put("sign", MD5.md5(sign));
            String sql = "select bank_id,user_id,acct_name,bank_account,bank_name,bank_code,card_type,bank_status,bank_remark,bank_mobile from lm_user_bank where open_status=?";
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);
            String Result = HttpUtil.doPost(url, json, "utf-8");
            JSONObject json3 = JSONObject.fromObject(Result);
            Map<String, String> yopresponsemap = null;
            Iterator iterator = json3.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = json3.getString(key);
                yopresponsemap.put(key, value);
            }
            if (ValidatorUtils.isEmpty(yopresponsemap)) {
                throw new EduException(210050);
            }


            if (ValidatorUtils.isNull(yopresponsemap.get("RESPONSECODE"))) {
                throw new EduException(210050);
            }

            if (yopresponsemap.get("RESPONSECODE").equals("0000")) {
                resultMap.put("RESPONSECODE", "0000");
            } else {
                resultMap.put("RESPONSECODE", "");
            }
            List<UserDebitBank> list = userDebitBankMapper.selectByUserId(user.getUuid());
            if (ValidatorUtils.isNotEmpty(list)) {
                resultMap.put("bank_list", list);
            }
        }
        return resultMap;
    }


    /**
     * 富友发送短信请求
     *
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/getMessage", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> getMessage(@RequestParam("id") Integer userDebitId, @RequestParam("mobile") String
            mobile1) throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserDebitBank userDebitBank = userDebitBankMapper.selectByPrimaryKey(userDebitId);
            UserInfo userInfo = userInfoMapper.selectByUserId(user.getUuid());
            String version = "1.0";//版本
            String mchntcd = "0002900F0096235";//商户代码
            SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
            String tradeDate = format.format(new Date()); //当前日期
            String mchntssn = System.currentTimeMillis() + "";
            //银行卡
            String account = userDebitBank.getDebitName(); //银行卡账户名称
            String cardNo = userDebitBank.getDebitAccount(); //银行卡号
            String idType = "0";
            String mobileNo = mobile1;
            String idCard = userInfo.getCardNo();//身份证号
            String sign = version + "|" + mchntcd + "|" + user.getUuid() + "|" + tradeDate + "|" + mchntssn + "|" + account + "|" + cardNo + "|" + idType + "|" + mobileNo + "|" + idCard + "|" + "5old71wihg2tqjug9kkpxnhx9hiujoqj";
            //发送授权请求
            try {
                //鉴权类型
                map.put("version", version);
                map.put("mchntcd", mchntcd);
                map.put("userId", user.getUuid());
                map.put("tradeDate", tradeDate);
                map.put("mchntssn", mchntssn);
                map.put("account", account);
                map.put("cardNo", cardNo);
                map.put("idType", idType);
                map.put("mobileNo", mobileNo);
                map.put("idCard", idCard);
                map.put("sign", MD5.md5(sign));
                logger.info("发送短信请求" + map);
                String url = "http://www-1.fuioupay.com:18670/mobile_pay/newpropay/bindMsg.pay";
                net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);
                String Result = HttpUtil.doPost(url, json, "utf-8");
                JSONObject json3 = JSONObject.fromObject(Result);
                Map<String, String> yopresponsemap = null;
                Iterator iterator = json3.keys();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    String value = json3.getString(key);
                    yopresponsemap.put(key, value);
                }
                logger.info("&&&&&&&&&&&&&&&&&" + yopresponsemap);
                if (ValidatorUtils.isEmpty(yopresponsemap)) {
                    throw new EduException(210050);
                }
                if (ValidatorUtils.isNull(yopresponsemap.get("RESPONSECODE"))) {
                    throw new EduException(210050);
                }
                if (yopresponsemap.get("RESPONSECODE").equals("0000")) {
                    resultMap.put("RESPONSECODE", "0000");
                } else {
                    resultMap.put("RESPONSECODE", "");
                }
                return resultMap;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("短信请求发送异常^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", e);
                throw new EduException(210050);
            }
        }
        return resultMap;
    }

    /**
     * 富友卡支付接口
     *
     * @param
     * @return
     * @throws EduException
     */
    @RequestMapping(value = "/paymentBank", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> paymentBank(@RequestParam("amt") Integer amt1, @RequestParam("BACKURL") String BACKURL1) throws EduException {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            String version = "1.0";
            String userIp = "116.239.4.194"; //客户ip
            String mchntcd = "0002900F0096235"; //商户代码
            String type = "03";//交易类型
            String MCHNTORDERID = "2016021600001";//商户订单号
            String userId = user.getUuid();
            int amt = amt1;//交易金额
            String PROTOCOLNO = "14907763938986631634";//协议号
            String NEEDSENDMSG = "0";//是否发送短信固定值"0"
            String BACKURL = BACKURL1;//异步后台返回url
            String REM1 = "";
            String REM2 = "";
            String REM3 = "";
            String signIp = "MD5";//签名类型
            String sign = version + "|" + userIp + "|" + mchntcd + "|" + type + "|" + MCHNTORDERID + "|" + userId + "|" + amt + "|" + PROTOCOLNO + "|" + NEEDSENDMSG + "|" + BACKURL + "|" + signIp + "|" + "5old71wihg2tqjug9kkpxnhx9hiujoqj";
            map.put("version", version);
            map.put("userIp", userIp);
            map.put("mchntcd", mchntcd);
            map.put("type", type);
            map.put("MCHNTORDERID", MCHNTORDERID);
            map.put("userId", userId);
            map.put("amt", amt);
            map.put("PROTOCOLNO", PROTOCOLNO);
            map.put("NEEDSENDMSG", NEEDSENDMSG);
            map.put("BACKURL", BACKURL);
            map.put("signIp", signIp);
            map.put("sign", MD5.md5(sign));
            logger.info("支付请求" + map);
            String url = "http://www-1.fuioupay.com:18670/mobile_pay/newpropay/order.pay";
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);
            String Result = HttpUtil.doPost(url, json, "utf-8");
            JSONObject json3 = JSONObject.fromObject(Result);
            Map<String, String> yopresponsemap = null;
            Iterator iterator = json3.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = json3.getString(key);
                yopresponsemap.put(key, value);
            }
            logger.info("&&&&&&&&&&&&&&&&&" + yopresponsemap);
            if (ValidatorUtils.isEmpty(yopresponsemap)) {
                throw new EduException(210050);
            }
            if (ValidatorUtils.isNull(yopresponsemap.get("RESPONSECODE"))) {
                throw new EduException(210050);
            }
            if (yopresponsemap.get("RESPONSECODE").equals("0000")) {
                resultMap.put("RESPONSECODE", "0000");
                resultMap.put("RESPONSEMSG", yopresponsemap.get("RESPONSEMSG"));
            } else {
                resultMap.put("RESPONSECODE", "");
                resultMap.put("RESPONSEMSG", "失败");
            }
        }
        return resultMap;

    }

    /**
     * 富友订单结果查询
     */
    @RequestMapping(value = "/selectResult", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, Object> selectResult() throws EduException {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            String version = "3.0";
            String mchntcd = "0002900F0006944";
            String mchntorderid = "2016021600001";
            String sign = version + "|" + mchntcd + "|" + mchntorderid + "|" + "5old71wihg2tqjug9kkpxnhx9hiujoqj";
            map.put("version", version);
            map.put("mchntcd", mchntcd);
            map.put("mchntorderid", mchntorderid);
            map.put("sign", MD5.md5(sign));
            logger.info("发送短信请求" + map);
            String url = "http://www-1.fuioupay.com:18670/mobile_pay/checkInfo/checkResult.pay";
            net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);
            String Result = HttpUtil.doPost(url, json, "utf-8");
            JSONObject json3 = JSONObject.fromObject(Result);
            Map<String, String> yopresponsemap = null;
            Iterator iterator = json3.keys();
            while (iterator.hasNext()) {
                String key = (String) iterator.next();
                String value = json3.getString(key);
                yopresponsemap.put(key, value);
            }
            logger.info("&&&&&&&&&&&&&&&&&" + yopresponsemap);
            if (ValidatorUtils.isEmpty(yopresponsemap)) {
                throw new EduException(210050);
            }
            if (ValidatorUtils.isNull(yopresponsemap.get("RESPONSECODE"))) {
                throw new EduException(210050);
            }
            if (yopresponsemap.get("RESPONSECODE").equals("0000")) {
                resultMap.put("RESPONSECODE", "0000");
            } else {
                resultMap.put("RESPONSECODE", "");
            }
        }
        return resultMap;
    }


    /**
     * 富友银行卡绑定
     *
     * @param
     * @return
     * @throws EduException
     */
    @RequestMapping(value = "/bankAdd", method = RequestMethod.GET)
    @ResponseBody
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> bankAdd(@RequestParam("id") Integer userDebitId, @RequestParam("mobile") String
            mobile1) throws EduException {
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> resultMap = new LinkedHashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            UserDebitBank userDebitBank = userDebitBankMapper.selectByPrimaryKey(userDebitId);
            UserInfo userInfo = userInfoMapper.selectByUserId(user.getUuid());
            String version = "1.0";//版本
            HttpUtil httpUtil = new HttpUtil();
            String mchntcd = "0002900F0096235";//商户代码
            String userId = user.getUuid();
            String mchntssn = System.currentTimeMillis() + "";
            String account = userDebitBank.getDebitName(); //银行卡账户名称
            String cardNo = userDebitBank.getDebitAccount(); //银行卡号
            String idType = "0";
            String mobileNo = mobile1;
            String msgCode = "000000";
            String idCard = userInfo.getCardNo();//身份证号
            String sign = version + "|" + mchntcd + "|" + userId + "|" + System.currentTimeMillis() + "|" + mchntssn + "|" + account + "|" + cardNo + "|" + idType + "|" + mobileNo + "|" + idCard + "|" + "5old71wihg2tqjug9kkpxnhx9hiujoqj";
            try {  //发送授权请求
                map.put("version", version);
                map.put("mchntcd", mchntcd);
                map.put("userId", userId.toString());
                map.put("tradeDate", System.currentTimeMillis());
                map.put("mchntssn", mchntssn);
                map.put("account", account);
                map.put("cardNo", cardNo);
                map.put("msgCode", msgCode);
                map.put("idType", idType);
                map.put("mobileNo", mobileNo);
                map.put("idCard", idCard);
                map.put("sign", MD5.md5(sign));
                logger.info("富有绑卡请求" + map);
                JSONArray jsonArray = null;
                String url = "http://www-1.fuioupay.com:18670/mobile_pay/newpropay/bindCommit.pay";
                net.sf.json.JSONObject json = net.sf.json.JSONObject.fromObject(map);
                String Result = HttpUtil.doPost(url, json, "utf-8");
                JSONObject json3 = JSONObject.fromObject(Result);
                Map<String, String> yopresponsemap = null;
                /*jsonArray = JSONArray.fromObject(json3.getString("data"));*/
                Iterator iterator = json3.keys();
                while (iterator.hasNext()) {
                    String key = (String) iterator.next();
                    String value = json3.getString(key);
                    yopresponsemap.put(key, value);
                }
                logger.info("&&&&&&&&&&&&&&&&&" + yopresponsemap);
                if (ValidatorUtils.isEmpty(yopresponsemap)) {
                    throw new EduException(210050);
                }
                if (ValidatorUtils.isNull(yopresponsemap.get("RESPONSECODE"))) {
                    throw new EduException(210050);
                }
                //代表成功
                if ("0000".equals(yopresponsemap.get("RESPONSECODE"))) {
                    resultMap.put("RESPONSEMSG", yopresponsemap.get("RESPONSEMSG"));
                    resultMap.put("RESPONSECODE", "0000");
                } else {
                    throw new EduException(210050);
                }
                return resultMap;
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("统一鉴权绑卡请求异常^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^", e);
                throw new EduException(210050);
            }
        }
        return resultMap;
    }


    //运营商h5授权
    public Map<String,Object> H5Page() throws EduException{
        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map1 = new HashMap<String, Object>();
        User user = loginUtil.verification();
        if (user == null) {
            map.put("message", "请重新登陆");
            map.put("code", Code.reLoginCode);
        } else {
            String optType = "AUTH";
            UserInfo userInfo = userInformationService.selectByUserId(user.getUuid());
            String idCard = userInfo.getCardNo();
            String realName = userInfo.getUserName();
            String callBackUrl = "http://122.235.202.133:8080/UserInformation/kJTAccreditReturnUrl";
            map1.put("optType",optType);
            map1.put("idCard",idCard);
            map1.put("realName",realName);
            map1.put("callBackUrl",callBackUrl);
            Map<String, Object> map5 = yunYingShang.Send(map1);
            String message = map5.get("message").toString();//返回信息
            String orderId = map5.get("orderId").toString();//订单id
            Map<String, Object> map3 = GsonUtil.fronJson2Map(message);
            String authUrl = map3.get("authUrl").toString();
            String tradeNo = map3.get("tradeNo").toString();
            UserOrderNo userOrderNo = new UserOrderNo();
            userOrderNo.setAuthUrl(authUrl);
            userOrderNo.setOrderId(orderId);
            userOrderNo.setTradeNo(tradeNo);
            userOrderNo.setUserId(user.getUuid());
            userInformationService.insertUserOrderNo(userOrderNo);
        }
        return map;
    }

    /**
     * 获取账号账单
     * @throws EduException
     */
    public void  selectOrder() throws EduException{
        User user = loginUtil.verification();
        Map<String, Object> map1 = new HashMap<String, Object>();
        String optType = "QUERY_ZIP_MOBILE_BILL";
        UserOrderNo userOrderNo = userInformationService.selectUserOrderNo(user.getUuid());
        String tradeNo = userOrderNo.getTradeNo();
        String mobile = user.getUser_mobile();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        String month = date;
        map1.put("optType",optType);
        map1.put("tradeNo",tradeNo);
        map1.put("mobile",mobile);
        Map<String, Object> map5 = yunYingShang.Send(map1);
        String message = map5.get("message").toString();//返回信息
        String orderId = map5.get("orderId").toString();//订单id
        Map<String, Object> map3 = GsonUtil.fronJson2Map(message);
        JSONObject jsonObject = JSONObject.fromObject(map3.get("bills").toString());
        UserOrderBill userOrderBill = (UserOrderBill)JSONObject.toBean(jsonObject);
        userInformationService.insertUserOrderBill(userOrderBill);
    }

    /**
     * 获取账号通话
     * @throws EduException
     */
    public Map<String,Object> accountCall()throws EduException{
        User user = loginUtil.verification();
        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String,Object> map2 = new HashMap<>();
        String optType = "QUERY_ZIP_MOBILE_CALL";
        UserOrderNo userOrderNo = userInformationService.selectUserOrderNo(user.getUuid());
        String tradeNo = userOrderNo.getTradeNo();
        String mobile = user.getUser_mobile();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String date = format.format(new Date());
        String month = date;
        map1.put("optType",optType);
        map1.put("tradeNo",tradeNo);
        map1.put("mobile",mobile);
        Map<String, Object> map5 = yunYingShang.Send(map1);
        String message = map5.get("message").toString();//返回信息
        String orderId = map5.get("orderId").toString();//订单id
        Map<String, Object> map3 = GsonUtil.fronJson2Map(message);
        JSONObject jsonObject = JSONObject.fromObject(map3.get("list.calls").toString());
        JSONObject jsonObject1 = JSONObject.fromObject(map3.get("list").toString());
        Calls calls = (Calls)JSONObject.toBean(jsonObject);
        Lists list = (Lists)JSONObject.toBean(jsonObject1);
        AccountCall accountCall = new AccountCall();
        accountCall.setBillmonth(list.getBillMonth());
        accountCall.setCode(list.getCode());
        accountCall.setDetailsId(calls.getDetails_id());
        accountCall.setMessage(list.getMessage());
        accountCall.setDialType(calls.getDial_type());
        accountCall.setDuration(calls.getDuration());
        accountCall.setFee(calls.getFree());
        accountCall.setLocation(calls.getLocation());
        accountCall.setLocationType(calls.getLocation_type());
        accountCall.setMobile(map3.get("mobile").toString());
        accountCall.setPeerNumber(calls.getPeer_number());
        accountCall.setTime(calls.getTime());
        accountCall.setTotalSize(list.getTotal_size());
        userInformationService.insertAccountCall(accountCall);
        map2.put("message","success");
        map2.put("Code",Code.successCode);
        return map2;
    }

    /**
     * 获取短信通话
     * @throws EduException
     */
    public Map<String,Object> messageCall() throws EduException{
        User user = loginUtil.verification();
        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String,Object> map2 = new HashMap<>();
        String optType = "QUERY_ZIP_MOBILE_SMS";
        UserInfo userInfo = userInformationService.selectByUserId(user.getUuid());
        UserOrderNo userOrderNo = userInformationService.selectUserOrderNo(user.getUuid());
        String idCard = userInfo.getCardNo();
        String realName = userInfo.getUserName();
        String callBackUrl = "";
        map1.put("optType",optType);
        map1.put("idCard",idCard);
        map1.put("realName",realName);
        map1.put("callBackUrl",callBackUrl);
        Map<String, Object> map5 = yunYingShang.Send(map1);
        String message = map5.get("message").toString();//返回信息
        String orderId = map5.get("orderId").toString();//订单id
        Map<String, Object> map3 = GsonUtil.fronJson2Map(message);
        JSONObject jsonObject1 = JSONObject.fromObject(map3.get("list").toString());
        MessageCallVo messageCallVo = (MessageCallVo)JSONObject.toBean(jsonObject1);
        JSONObject jsonObject2 = JSONObject.fromObject(messageCallVo.getRecords());
        MessageCalls messageCall = (MessageCalls)JSONObject.toBean(jsonObject2);
        messageCall.setBillmonth(messageCallVo.getBillMonth());
        messageCall.setCode(messageCallVo.getCode());
        messageCall.setMessage(messageCallVo.getMessage());
        messageCall.setTotalsize(messageCallVo.getTotalsize());
        userService.insert(messageCall);
        map2.put("message","success");
        map2.put("Code",Code.successCode);
        return map2;
    }

    public Map<String,Object> OperatorReport() throws EduException{
        User user = loginUtil.verification();
        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String,Object> map2 = new HashMap<>();
        String optType = "QUERY_ZIP_REPORT";
        UserInfo userInfo = userInformationService.selectByUserId(user.getUuid());
        UserOrderNo userOrderNo = userInformationService.selectUserOrderNo(user.getUuid());
        String tradeNo = userOrderNo.getTradeNo();
        String mobile = user.getUser_mobile();
        String idCard = userInfo.getCardNo();
        String realName = userInfo.getUserName();
        map1.put("optType",optType);
        map1.put("tradeNo",tradeNo);
        map1.put("mobile",mobile);
        map1.put("idCard",idCard);
        map1.put("realName",realName);
        Map<String, Object> map5 = yunYingShang.Send(map1);
        String code = map5.get("code").toString();
        String message = map5.get("message").toString();
        String update_time = map5.get("update_time").toString();
        JSONObject jsonObject1 = JSONObject.fromObject(map5.get("userbasicinfo").toString());
        UserBasicInfoVo userBasicInfoVo = (UserBasicInfoVo)JSONObject.toBean(jsonObject1);
        JSONObject jsonObject2 = JSONObject.fromObject(map5.get("basicinfocheck_items").toString());
        BasicinFocheckItems basicinFocheckItems = (BasicinFocheckItems)JSONObject.toBean(jsonObject2);
        map2.put("message","success");
        map2.put("Code",Code.successCode);
        return map2;
    }

    public Map<String,Object> FullOrder() throws EduException{
        User user = loginUtil.verification();
        Map<String, Object> map1 = new HashMap<String, Object>();
        Map<String,Object> map2 = new HashMap<>();
        String optType = "QUERY_ZIP_MOBILE_MONTH";
        UserInfo userInfo = userInformationService.selectByUserId(user.getUuid());
        UserOrderNo userOrderNo = userInformationService.selectUserOrderNo(user.getUuid());
        String tradeNo = userOrderNo.getTradeNo();
        String mobile = user.getUser_mobile();
        map1.put("optType",optType);
        map1.put("tradeNo",tradeNo);
        map1.put("mobile",mobile);
        Map<String, Object> map5 = yunYingShang.Send(map1);
        String mobile1 = map5.get("mobile").toString();
        int code = Integer.parseInt(map5.get("code").toString());
        String message = map5.get("message").toString();
        String name = map5.get("name").toString();
        String idcard = map5.get("idcard").toString();
        String carrier = map5.get("carrier").toString();
        String province = map5.get("province").toString();
        String city = map5.get("city").toString();
        String open_time = map5.get("open_time").toString();
        String level = map5.get("level").toString();
        String package_name = map5.get("package_name").toString();
        int state = Integer.parseInt(map5.get("state").toString());
        int available_balance = Integer.valueOf(map5.get("available_balance").toString());
        String lastmodifytime = map5.get("lastmodifytime").toString();
        FullOrder fullOrder = new FullOrder();
        fullOrder.setAvailableBalance(available_balance);
        fullOrder.setCarrier(carrier);
        fullOrder.setCity(city);
        fullOrder.setCode(code+"");
        fullOrder.setIdcard(idcard);
        fullOrder.setLastmodifytime(lastmodifytime);
        fullOrder.setLevel(level);
        fullOrder.setMessage(message);
        fullOrder.setMobile(mobile);
        fullOrder.setName(name);
        fullOrder.setOpenTime(open_time);
        fullOrder.setPackageName(package_name);
        fullOrder.setProvince(province);
        fullOrder.setState(state);
        userInformationService.insertFullOrder(fullOrder);
        map2.put("message","success");
        map2.put("Code",Code.successCode);
        return map2;
    }


}
