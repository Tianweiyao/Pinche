package com.hodehtml.demo.utils;

import com.core.framework.config.PropertiesConfig;
import com.core.framework.constants.Constants;
import com.core.framework.utils.*;
import org.apache.log4j.Logger;

import java.util.LinkedHashMap;
import java.util.Map;


public class ResponseUtils {
	private static Logger logger = Logger.getLogger(ResponseUtils.class);
	private final String ROOT = "response";
	private final String CONTENT = "content";

	private String format = Constants.XML;
	private String logno = "";

	public ResponseUtils(String format, String logno) {
		this.format = format;
		this.logno = logno;
    }

	private Map<String, Object> getInfoMap() {

		return getInfoMap(100000);
	}

	private Map<String, Object> getInfoMap(int code) {

		return getInfoMap(String.valueOf(code));
	}

	private Map<String, Object> getInfoMap(String code) {

		Map<String, Object> map = new LinkedHashMap<String, Object>();
		Map<String, Object> info = new LinkedHashMap<String, Object>();
		if(!ValidatorUtils.isInfoCode(code)) {
			code = String.valueOf(100002);
		}
		info.put("code", code);
		info.put("msg", PropertiesConfig.getProperties("message.properties", code+""));
		if(!code.equals(String.valueOf(100000))) {
			info.put("logno", logno);
		}
		/*info.put("maintain_starttime","0");
		info.put("maintain_endtime","0");
		String maintain = RedisUtil.getKeyword(RedisUtil.REDIS_QTYD_MAINTAIN);
		if(ValidatorUtils.isNotEmpty(maintain)){
			String[] tp = maintain.split(";");
			String status = tp[0];
			String startTime = tp[1];
			String endTime = tp[2];
			Long maintainTimeStart = TimeUtils.getTimeStampByDate(startTime, TimeUtils.yyyyMMddHHmmss);
			Long maintainTimeEnd = TimeUtils.getTimeStampByDate(endTime, TimeUtils.yyyyMMddHHmmss);
			if(StatusConstantWeb.STATUS_NORMAL_CHAR.equals(status)){
				if(maintainTimeEnd<TimeUtils.getCurrentTimeStamp()){
					info.put("maintain_starttime","0");
					info.put("maintain_endtime","0");
				}else{
					info.put("maintain_starttime",maintainTimeStart+"");
					info.put("maintain_endtime",maintainTimeEnd+"");
				}
			}
		}*/
		info.put("server_time", TimeUtils.getCurrentTimeStamp()+"");
		map.put("info", info);
		return map;
	}

	private String response(int code) {

		return doResponse(getInfoMap(code));
	}

	private String response(String code) {

		return doResponse(getInfoMap(code));
	}

	private String response(Map<String, Object> content) {

		Map<String, Object> map = getInfoMap();
		if(content.size()==1){
			for (String key:content.keySet()) {
				map.put(CONTENT, content.get(key));
			}
		}else{
			map.put(CONTENT, content);
		}

		return doResponse(map);
	}

	@SuppressWarnings("unchecked")
	public String response(Object object) {

		if(object == null) {
			return null;
		}

        if (object instanceof Integer) {
    		return response(((Integer) object).intValue());
        } else if (object instanceof String) {
    		return response(object.toString());
        } else if (object instanceof Map) {
        	return response((Map<String, Object>)object);
        } else {
        	return null;
        }
	}

	private boolean response(int code, String filename) {

		return doResponse(getInfoMap(code), filename);
	}

	private boolean response(String code, String filename) {

		return doResponse(getInfoMap(code), filename);
	}

	private boolean response(Map<String, Object> content, String filename) {

		Map<String, Object> map = getInfoMap();
		if(content != null && !content.isEmpty()) { //content
			map.put(CONTENT, content);
		}

		return doResponse(map, filename);
	}

	@SuppressWarnings("unchecked")
	public boolean response(Object object, String filename) {

		if(object == null) {
			return false;
		}

        if (object instanceof Integer) {
    		return response(((Integer) object).intValue(), filename);
        } else if (object instanceof String) {
    		return response(object.toString(), filename);
        } else if (object instanceof Map) {
        	return response((Map<String, Object>)object, filename);
        } else {
        	return false;
        }
	}

	private String doResponse(Map<String, Object> map) {

    	if(this.format.equalsIgnoreCase(Constants.XML)) {

    		try {
				return new XMLUtils().toXML(ROOT, map);
			} catch (Exception e) {
				logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				return "";
			}
    	} else if(this.format.equalsIgnoreCase(Constants.JSON)) {

			try {
				return new JSONUtils().toJSON(ROOT, map);
			} catch (Exception e) {
				logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				return "";
			}
    	} else {
			logger.error(TraceInfoUtils.getTraceInfo() + 110007);
			return "";
    	}
	}

	private boolean doResponse(Map<String, Object> map, String filename) {
		

    	if(this.format.equalsIgnoreCase(Constants.XML)) {

    		try {
				return new XMLUtils().toXML(ROOT, map, filename);
			} catch (Exception e) {
				logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				return false;
			}
    	} else if(this.format.equalsIgnoreCase(Constants.JSON)) {
			try {
				return new JSONUtils().toJSON(ROOT, map, filename);
			} catch (Exception e) {
				logger.error(TraceInfoUtils.getTraceInfo() + StringUtils.nullToStrTrim(e.getMessage()));
				return false;
			}
    	} else {
			logger.error(TraceInfoUtils.getTraceInfo() + 110007);
			return false;
    	}
	}

}
