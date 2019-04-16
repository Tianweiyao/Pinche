package com.hodehtml.demo.redis;

public enum RedisKeyEnum {
	
	RANDOM_4("random_4","4位随机数"),
	REFRESH("refresh","系统防刷"),
	LOGINIP_LIB("login_ip_lib","IP地址库"),
	ALARM("alarm","预警"),
	CONFIG("params_config","参数配置"),
	SCHEDULE_USER("user_schedule","用户操作定时器"),
	SCHEDULE_OUT_WARD("company_out_ward","公司出款业务"),
	SCHEDULE_NOTIFY_OUT_WARD("out_ward_notify","资金出款结果回调"),
	SCHEDULE_NOTIFY_REPAY("repay_notify","资金还款结果回调"),
	SCHEDULE_MESSAGE("schedule_message","消息队列"),
	SCHEDULE_DAILY("schedule_daily","日数据统计");
	
	public String KEY = "" ;
	public String REMARK = "" ;
	
	RedisKeyEnum(String key, String remark){
		KEY = key ;
		REMARK = remark ;
	}
}
