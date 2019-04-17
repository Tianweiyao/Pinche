package com.hodehtml.demo.utils;/**
 * created by pht on 2019/3/12 0012
 */

import net.sf.json.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;

/**
 * @program loan-project-api
 * @date 2019/3/12 0012
 * @author pht
 */
public class HttpUtil {

    private final static Logger logger = Logger.getLogger(HttpUtil.class);

    public static String doPost(String url, JSONObject json, String charset) {
        HttpClient httpClient = null;
        HttpPost httpPost = null;
        String result = null;
        try {
            httpClient = new SSLClient();
            httpPost = new HttpPost(url);
            // 设置参数
            StringEntity s = new StringEntity(json.toString());
            s.setContentEncoding("UTF-8");
            s.setContentType("application/json");
            httpPost.setEntity(s);
            HttpResponse response = httpClient.execute(httpPost);
            if (response != null) {
                HttpEntity resEntity = response.getEntity();
                if (resEntity != null) {
                    result = EntityUtils.toString(resEntity, charset);
                }
            }
        } catch (Exception ex) {
            logger.error("https 请求实时代付API异常", ex);
        }
        return result;
    }
}
