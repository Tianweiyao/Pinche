package com.hodehtml.demo.vo;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ServiceCoreVO {
	private String requestTime = ""; // 请求时间
	private String serviceType = ""; // 服务类型：rest或http
	private String ip = ""; // 请求IP
	private String requestUrl = ""; // 请求URL
	private String operateLogno = ""; // 操作编号
	private String bean = ""; //bean
	private String method = "";// 方法名
	private String version = "";
	private String methodVersion = "";

	private String action = "";
	private String format = ""; // xml或json
	private int reqLength = 0; // 请求长度
	private String sign = ""; // 签名信息
	private String reqContent = ""; // 请求内容
	private String reqContentMd5 = ""; // 请求内容的MD5值
	private Object reqObject = null; // 请求对象
	private Object resObject = null; // 回馈对象
	private String accessId = ""; // 访问ID
	private String accessKey = ""; // 访问Key
	private Long userId = 0L;	//用户ID
	private String userLoginName = "";//后台用户登录名
	private String userRealName = "";//后台用户真实姓名
	private String userRoleName = "";//后台角色名称
	private Integer customerNo = 0;	//用户ID
	private String customerMobile = "";//前台用户手机号
	private String customerRealName = "";//前台用户真实姓名

	private int code = 0; // 返回编码
	private String exception = "";
	private String resContent = "";
	private int reslength = 0;
	private String recordNo = "";
	private String desKey="";
	private String sinaUId = "";
	private String trackid = "";
	private int devicePort = DeviceTypeEnum.WEIXIN.KEY;
	private int loginTimes = 0;
	private String consumeMilSecond = "";

	private HttpServletRequest request = null;
	private HttpServletResponse response = null;
	public ServiceCoreVO() {
		super();
	}

	public String getCustomerMobile() {
		return customerMobile;
	}

	public void setCustomerMobile(String customerMobile) {
		this.customerMobile = customerMobile;
	}

	public String getCustomerRealName() {
		return customerRealName;
	}

	public void setCustomerRealName(String customerRealName) {
		this.customerRealName = customerRealName;
	}

	public String getRequestTime() {
		return requestTime;
	}

	public void setRequestTime(String requestTime) {
		this.requestTime = requestTime;
	}

	public String getServiceType() {
		return serviceType;
	}

	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		if("anerroroccurredwhenobtainingipaddress".equals(ip)){
			ip = "";
		}
		this.ip = ip;
	}

	public String getRequestUrl() {
		return requestUrl;
	}

	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}

	public String getOperateLogno() {
		return operateLogno;
	}

	public void setOperateLogno(String operateLogno) {
		this.operateLogno = operateLogno;
	}

	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public int getReqLength() {
		return reqLength;
	}

	public void setReqLength(int reqLength) {
		this.reqLength = reqLength;
	}

	public String getSign() {
		return sign;
	}

	public void setSign(String sign) {
		this.sign = sign;
	}

	public String getReqContentMd5() {
		return reqContentMd5;
	}

	public void setReqContentMd5(String reqContentMd5) {
		this.reqContentMd5 = reqContentMd5;
	}

	public Object getReqObject() {
		return reqObject;
	}

	public void setReqObject(Object reqObject) {
		this.reqObject = reqObject;
	}

	public Object getResObject() {
		return resObject;
	}

	public void setResObject(Object resObject) {
		this.resObject = resObject;
	}

	public String getAccessId() {
		return accessId;
	}

	public void setAccessId(String accessId) {
		this.accessId = accessId;
	}

	public String getAccessKey() {
		return accessKey;
	}

	public void setAccessKey(String accessKey) {
		this.accessKey = accessKey;
	}

	public String getReqContent() {
		return reqContent;
	}

	public void setReqContent(String reqContent) {
		this.reqContent = reqContent;
	}

	public String getMethod() {
		return method;
	}

	public void setMethod(String method) {
		this.method = method;
	}

	public String getBean() {
		return bean;
	}

	public void setBean(String bean) {
		this.bean = bean;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getException() {
		return exception;
	}

	public void setException(String exception) {
		this.exception = exception;
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public void setRequest(HttpServletRequest request) {
		this.request = request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public void setResponse(HttpServletResponse response) {
		this.response = response;
	}

	public String getResContent() {
		return resContent;
	}

	public void setResContent(String resContent) {
		this.resContent = resContent;
	}

	public String getUserLoginName() {
		return userLoginName;
	}

	public void setUserLoginName(String userLoginName) {
		this.userLoginName = userLoginName;
	}

	public String getUserRealName() {
		return userRealName;
	}

	public void setUserRealName(String userRealName) {
		this.userRealName = userRealName;
	}

	public String getUserRoleName() {
		return userRoleName;
	}

	public void setUserRoleName(String userRoleName) {
		this.userRoleName = userRoleName;
	}

	public int getReslength() {
		return reslength;
	}

	public void setReslength(int reslength) {
		this.reslength = reslength;
	}

	public String getRecordNo() {
		return recordNo;
	}

	public void setRecordNo(String recordNo) {
		this.recordNo = recordNo;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
		this.userId = userId;
	}

	public String getDesKey() {
		return desKey;
	}

	public void setDesKey(String desKey) {
		this.desKey = desKey;
	}

	public String getSinaUId() {
		return sinaUId;
	}

	public void setSinaUId(String sinaUId) {
		this.sinaUId = sinaUId;
	}

	public String getTrackid() {
		return trackid;
	}

	public void setTrackid(String trackid) {
		this.trackid = trackid.toLowerCase();
	}

	public int getDevicePort() {
		return devicePort;
	}

	public void setDevicePort(int devicePort) {
		this.devicePort = devicePort;
	}

	public Integer getCustomerNo() {
		return customerNo;
	}

	public void setCustomerNo(Integer customerNo) {
		this.customerNo = customerNo;
	}

	public int getLoginTimes() {
		return loginTimes;
	}

	public void setLoginTimes(int loginTimes) {
		this.loginTimes = loginTimes;
	}

	public String getConsumeMilSecond() {
		return consumeMilSecond;
	}

	public void setConsumeMilSecond(String consumeMilSecond) {
		this.consumeMilSecond = consumeMilSecond;
	}

	public String getMethodVersion() {
		return methodVersion;
	}

	public void setMethodVersion(String methodVersion) {
		this.methodVersion = methodVersion;
	}

}
