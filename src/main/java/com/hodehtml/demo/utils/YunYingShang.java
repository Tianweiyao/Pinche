package com.hodehtml.demo.utils;/**
 * created by pht on 2019/4/19 0019
 */

import com.hodehtml.demo.controller.RePayController;
import com.hodehtml.demo.vo.EduException;
import net.sf.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @author pht
 * @program demo
 * @date 2019/4/19 0019
 */
@Component
public class YunYingShang {

    private Logger logger = LoggerFactory.getLogger(YunYingShang.class);

    public Map<String,Object> Send(Map<String, Object> map1) throws EduException {

        Map<String, Object> map = new HashMap<String, Object>();
        Map<String, Object> map3 = new HashMap<String, Object>();
        JSONObject jsonObject = JSONObject.fromObject(map1);
        map.put("tranTime", System.currentTimeMillis());
        map.put("productCode", null);//产品编号
        map.put("orderId", null); //订单号
        map.put("merchCode", null);//商户号
        map.put("jsonStr", jsonObject);
        map.put("sign", null);//签名串
        map.put("merchPrivate", "");
        JSONObject json = JSONObject.fromObject(map);
        String url = "http://47.93.251.144/datadriver/query";
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
            throw new com.hodehtml.demo.vo.EduException(210050);
        }
        if (ValidatorUtils.isNull(yopresponsemap.get("retCode"))) {
            throw new EduException(210050);
        }
        String message = null;
        //代表成功
        if ("0000".equals(yopresponsemap.get("retCode"))) {
            String orderId = yopresponsemap.get("orderId");
            String retInfo = yopresponsemap.get("retInfo");
            Map<String, Object> map2 = GsonUtil.fronJson2Map(retInfo);
            String codeDesc = map2.get("codeDesc").toString();
            message = map2.get("message").toString();
            map3.put("message",message);
            map3.put("orderId",orderId);
        }
        return map3;
    }
}