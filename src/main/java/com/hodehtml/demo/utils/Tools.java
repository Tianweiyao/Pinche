package com.hodehtml.demo.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class Tools
{
	private static final Log log = LogFactory.getLog(Tools.class);
	// 签名版本名称
    public static final String SIGN_VERSION_NAME = "sign_version";
    //签名类型名称
    public static final String SIGN_TYPE_NAME    = "sign_type";
    // 签名名称
    public static final String SIGN_NAME         = "sign";
	/**
     * 把数组所有元素排序，并按照“参数=参数值”的模式用“&”字符拼接成字符串
     *
     * @param params
     *            需要排序并参与字符拼接的参数组
     * @param encode 是否需要urlEncode
     * @return 拼接后字符串
     */
    public static String createLinkString(Map<String, String> params, boolean encode) {
        List<String> keys = new ArrayList<String>(params.keySet());
        Collections.sort(keys);
        String prestr = "";
        String charset = params.get("_input_charset");
        for (int i = 0; i < keys.size(); i++) {
            String key = keys.get(i);
            String value = params.get(key);
            if (encode) {
                try {
                    value = URLEncoder.encode(URLEncoder.encode(value, charset),charset);
                } catch (UnsupportedEncodingException e) {
                	log.error(CommonUtils.getTraceInfo()+" params:"+params.toString()+" encode:"+encode+"\t"+StringUtils.nullToStrTrim(e.getMessage()));
                }
            }
           
            	 if (i == keys.size() - 1) {// 拼接时，不包括最后一个&字符
                     prestr = prestr + key + "=" + value;
                 } else {
                     prestr = prestr + key + "=" + value + "&";
                 }
          }
        return prestr;
    }
}
