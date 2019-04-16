package com.hodehtml.demo.utils;/**
 * created by pht on 2019/4/11 0011
 */

import com.hodehtml.demo.vo.DeviceTypeEnum;
import com.hodehtml.demo.vo.EduException;
import com.hodehtml.demo.vo.ServiceCoreVO;
import com.hodehtml.demo.vo.SpringContext;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @program demo
 * @date 2019/4/11 0011
 * @author pht
 */
public class BaseAction {

    private static Logger logger = Logger.getLogger(BaseAction.class);

    private ServiceCoreVO serviceCoreVO = null;
    protected String action = "";
    protected String operatelogno = "";
    protected String recordno = "";
    protected String reqip = "";
    protected String trackid = "";
    protected String devicePort = DeviceTypeEnum.WEIXIN.KEY+"";
    protected int loginTimes = 0;
    protected String desKey="";

    protected String userno = "";
    protected String accessId = "";
    protected String accessKey = "";
    protected Long userId = 0L;
    protected String userLoginName = "";//后台用户登录名
    protected String userRealName = "";//后台用户真实姓名
    protected String userRoleName = "";//后台用户角色名称
    protected Integer customerNo = 0;//前台用户ID
    protected String customerMobile = "";//前台用户手机号
    protected String customerRealName = "";//前台用户真实姓名
    protected String sinaUId  ="";

    protected HttpServletRequest request = null;
    protected HttpServletResponse response = null;

    protected Object reqobject = null;
    protected Map<String, Object> resobject = null;

    @SuppressWarnings({ "unchecked", "rawtypes" })
    public ServiceCoreVO execute(ServiceCoreVO serviceCoreVO) throws EduException {

        this.serviceCoreVO = serviceCoreVO;

        action = serviceCoreVO.getAction();
        operatelogno = serviceCoreVO.getOperateLogno();
        try {
            //利用反射机制，找到对应的action和执行方法
            Object executeAction = SpringContext.getBean(serviceCoreVO.getBean());
            Class classObject =executeAction.getClass();
            Method method0 = executeAction.getClass().getSuperclass().getDeclaredMethod("init",ServiceCoreVO.class);
            method0.invoke(executeAction,serviceCoreVO);
            Method method = classObject.getDeclaredMethod(serviceCoreVO.getMethod());
            return (ServiceCoreVO)method.invoke(executeAction);
        }catch (Exception e) {
            e.printStackTrace();
            logger.error(TraceInfoUtils.getTraceInfo()+e.getMessage());
            logger.error(TraceInfoUtils.getTraceInfo(e.getCause())+e.getCause().getMessage());
            throw new EduException(e.getCause().getMessage());
        }
    }

    public void init(ServiceCoreVO serviceCoreVO){
        this.reqobject = serviceCoreVO.getReqObject();
        this.request = serviceCoreVO.getRequest();
        this.response = serviceCoreVO.getResponse();
        resobject = new LinkedHashMap<String, Object>();
        this.serviceCoreVO = serviceCoreVO;
        this.reqip = serviceCoreVO.getIp();
        this.trackid = serviceCoreVO.getTrackid();
        this.devicePort = serviceCoreVO.getDevicePort()+"";
        this.loginTimes = serviceCoreVO.getLoginTimes();
        this.userId = serviceCoreVO.getUserId();
        this.userLoginName = serviceCoreVO.getUserLoginName();
        this.userRealName = serviceCoreVO.getUserRealName();
        this.userRoleName = serviceCoreVO.getUserRoleName();
        this.customerNo = serviceCoreVO.getCustomerNo();
        this.customerMobile = this.serviceCoreVO.getCustomerMobile();
        this.customerRealName = this.serviceCoreVO.getCustomerRealName();
        this.desKey = serviceCoreVO.getDesKey();
        this.sinaUId = serviceCoreVO.getSinaUId();
        this.accessId = serviceCoreVO.getAccessId();
    }

    protected String getRequestContentValue(String name) throws EduException {
        return ApiUtils.getRequestContentValue(this.reqobject, name);
    }

    protected String getRequestHeaderContent(String name) throws EduException {

        return WebUtils.getInstance().getHeaderDecode(request, name);
    }

    protected ServiceCoreVO response() {

        if(resobject == null || resobject.isEmpty()) {
            return doResponse(100000);
        }

        return doResponse(resobject);
    }

    private ServiceCoreVO doResponse(Object object) {

        if(ValidatorUtils.isNotNull(recordno)) {
            serviceCoreVO.setRecordNo(recordno);
        }
        serviceCoreVO.setResObject(object);
        return serviceCoreVO;
    }

    protected ServiceCoreVO response(int code) {

        return doResponse(code);
    }

    protected ServiceCoreVO response(String code) {

        if(ValidatorUtils.isNull(code)) {
            code = String.valueOf(100002);
        }
        return doResponse(code);
    }

    protected int getCurrentPage() throws EduException {
        String value = getRequestContentValue("cur_page");
        if(!ValidatorUtils.isDigital(value)){
            return 1;
        }
        int rt = Integer.parseInt(value);
        if(rt<=0){
            return 1;
        }
        return rt;
    }

    protected int getPageSize()  throws EduException{
        String value =getRequestContentValue("page_size");
        if(!ValidatorUtils.isDigital(value)){
            return 10;
        }
        int rt = Integer.parseInt(value);
        if(rt<=0){
            return 10;
        }
        return rt;
    }
}
