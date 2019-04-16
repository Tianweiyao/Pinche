package com.hodehtml.demo.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class PropertiesConfig {

	private final static Map<String, ResourceBundle> PROPERTY_POOL = new HashMap<String, ResourceBundle>();

	
	public static final String getProperties(String fileName,String key){
		try{
			int index = fileName.indexOf(".") ;
			if(index>0){
				fileName = fileName.substring(0,index > 0 ? index : fileName.length());
				if(!PROPERTY_POOL.containsKey(fileName)){
					PROPERTY_POOL.put(fileName,ResourceBundle.getBundle(fileName));
				}
				return PROPERTY_POOL.get(fileName).getString(key);
			}else{

				return "";
			}
		}catch(Exception e){
			return Constants.UNKNOWN ;
		}
	}
	
	public static final ResourceBundle getProperties(String fileName){
		try{
			int index = fileName.indexOf(".") ;
			fileName = fileName.substring(0,index > 0 ? index : fileName.length());
			if(!PROPERTY_POOL.containsKey(fileName)){
				PROPERTY_POOL.put(fileName,ResourceBundle.getBundle(fileName));
			}
			return PROPERTY_POOL.get(fileName);
		}catch(Exception e){
			return null ;
		}
	}

	public final static void reload() {
		clear();
	}
	
	public final static void clear() {
		PROPERTY_POOL.clear();
	}
}
