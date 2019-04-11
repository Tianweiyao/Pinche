package com.hodehtml.demo.utils;

import com.business.enums.RedisKeyEnum;
import com.core.framework.redis.DataJedis;


public class RecordNoUtils {
	
	public static String get(){	
//		String recordNo = TimeUtils.getSysTimeLong().substring(2);
		return  new DataJedis().get16Random(RedisKeyEnum.RANDOM_4);
	}
	
	public static String get30(){
		return  new DataJedis().get30Random(RedisKeyEnum.RANDOM_4);
	}
	
	public static String getRandomCode() {
		String recordNo = get();
		StringBuffer sBuffer = new StringBuffer();
		int t = 0;
		String r = "";
		for(int i=0;i<recordNo.length();i=i+2){
			t = Integer.parseInt(recordNo.substring(i,i+2));
			r = getChar(t);
			sBuffer.append(r);
		}
		return sBuffer.toString();
	}
	
	private static String getChar(int t){
		String r = "";
		if(t>=0&&t<=25){
			r = (char)(t+65)+"";
			if("O".equals(r)){
				r="A";
			}else if("I".equals(r)){
				r="L";
			}
		}else{
			t = t%9;
			if(t==0){
				r = "A";
			}else{
				r = t+"";
			}
		}
		return r;
	}
}
