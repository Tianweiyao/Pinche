package com.hodehtml.demo.redis;

public enum RedisEnum {
	
	DATA("data","数据中心"),
	DISTRIBUTE_LOCK("distribute","分布式锁");
	
	public String CODE = "" ;
	public String REMARK = "" ;
	
	RedisEnum(String code, String remark){
		CODE = code ;
		REMARK = remark ;
	}
}
