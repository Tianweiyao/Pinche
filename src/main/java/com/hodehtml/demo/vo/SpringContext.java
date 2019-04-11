package com.hodehtml.demo.vo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ApplicationContextEvent;

import java.util.Map;

/**
 * Spring 上下文
 */

public class SpringContext implements ApplicationContextAware, ApplicationListener<ApplicationContextEvent> {
	
	
	public SpringContext(){
		System.out.println("SpringContext初始化完成");
	}
	
    private static ApplicationContext applicationContext = null;
    private static boolean RUN = false ;
    /** 
     * 实现ApplicationContextAware接口的回调方法，设置上下文环境 
     * @param applicationContext 
     */
    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
    	SpringContext.applicationContext = applicationContext;  
    }
    /** 
     * @return ApplicationContext 
     */  
    public static ApplicationContext getApplicationContext() {
        return applicationContext;  
    }
    
    /** 
     * 获取对象 
     *  
     * @param name 
     * @return Object
     * @throws BeansException
     */  
    public static Object getBean(String name) throws BeansException {
        return getApplicationContext().getBean(name);  
    }
    
    public static <T> Map<String,T> getBean(Class<T> classes){
    	return getApplicationContext().getBeansOfType(classes) ;
    }
    
	@Override
	public void onApplicationEvent(ApplicationContextEvent event) {
		try{
			ApplicationContext context = event.getApplicationContext() ;
			if(context.getParent() == null && !RUN){
				RUN = true ;
				Map<String,StartAfterSpringLoaded> map = context.getBeansOfType(StartAfterSpringLoaded.class) ;
				for (String str : map.keySet()) {
					if(map.get(str) == null){
						continue ;
					}
					try{
						map.get(str).start();
					}catch(Exception e){
						e.printStackTrace();
					}
				}
			}
		}catch(Exception e){
			e.printStackTrace();
		}
	} 
}
