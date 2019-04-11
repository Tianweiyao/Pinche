package com.hodehtml.demo.utils;

import com.core.framework.exception.EduException;
import com.core.framework.utils.StringUtils;
import org.jdom.Element;
import org.json.JSONException;
import org.json.JSONObject;


public class ApiUtils {
	
	public static String getRequestContentValue(Object content, String name) throws EduException {

		if(content == null) {
    		throw new EduException(110005);
		}

		String value = "";

        if (content instanceof Element) {

        	value = StringUtils.nullToStrTrim(((Element) content).getChildText(name));
        } else if (content instanceof JSONObject) {

    		try {
    			value = StringUtils.nullToStrTrim(String.valueOf(((JSONObject) content).get(name)));
			} catch (JSONException e) {
				value = "";
			}
        } else {

    		throw new EduException(110005);
        }

        return value.trim();
	}
	
}
