package com.hodehtml.demo.utils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.FileInputStream;
import java.io.InputStream;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Properties;

public class ConfigUtil {
	private static final Log log = LogFactory.getLog(ConfigUtil.class);
	
    private static volatile Properties PROPERTIES;
    
    public static Properties getProperties() {
        if (PROPERTIES == null) {
            synchronized (ConfigUtil.class) {
                if (PROPERTIES == null) {
                    String path = System.getProperty(Constants.CONFIG_PROPERTIES_KEY);
                    if (path == null || path.length() == 0) {
                        path = System.getenv(Constants.CONFIG_PROPERTIES_KEY);
                        if (path == null || path.length() == 0) {
                            path = Constants.DEFAULT_CONFIG_PROPERTIES;
                        }
                    }
                    PROPERTIES = loadProperties(path, false, true);
                }
            }
        }
        return PROPERTIES;
    }
    
    public static void addProperties(Properties properties) {
        if (properties != null) {
            getProperties().putAll(properties);
        }
    }

    public static void setProperties(String key,String value) {
    	getProperties().setProperty(key, value);
    }
    
    public static void setProperties(Properties properties) {
        if (properties != null) {
            PROPERTIES = properties;
        }
    }
    
	public static String getProperty(String key) {
	    return getProperty(key, null);
	}
	
    public static String getProperty(String key, String defaultValue) {
        String value = System.getProperty(key);
        if (value != null && value.length() > 0) {
            return value;
        }
        Properties properties = getProperties();
        return properties.getProperty(key, defaultValue);
    }
    
    /**
     * 加载文件
     * @param fileName 文件名
     * @param allowMultiFile 是否加载多个
     * @param optional 可选
     * @return
     */
    public static Properties loadProperties(String fileName, boolean allowMultiFile, boolean optional) {
    	Properties properties = new Properties();
        if (fileName.startsWith("/")) {
            try {
                FileInputStream input = new FileInputStream(fileName);
                try {
                    properties.load(input);
                } finally {
                    input.close();
                }
            } catch (Throwable e) {
                log.warn("Failed to load " + fileName + " file from " + fileName + "(ingore this file): " + e.getMessage(), e);
            }
            return properties;
        }
        
        if(!fileName.endsWith(".properties")){
        	fileName = fileName+".properties";
        }
        
        List<java.net.URL> list = new ArrayList<java.net.URL>();
        try {
        	Enumeration<java.net.URL> urls = ConfigUtil.class.getClassLoader().getResources(fileName);
            //Enumeration<java.net.URL> urls = ClassHelper.getClassLoader().getResources(fileName);
            list = new ArrayList<java.net.URL>();
            while (urls.hasMoreElements()) {
                list.add(urls.nextElement());
            }
        } catch (Throwable t) {
            log.warn("Fail to load " + fileName + " file: " + t.getMessage(), t);
        }
        
        if(list.size() == 0) {
            if (! optional) {
                log.warn("No " + fileName + " found on the class path.");
            }
            return properties;
        }
        
        if(! allowMultiFile) {
            if (list.size() > 1) {
                String errMsg = String.format("only 1 %s file is expected, but %d %s files found on class path: %s",
                        fileName, list.size(), fileName,list.toString());
                log.warn(errMsg);
            }

            try {
                properties.load(ConfigUtil.class.getClassLoader().getResourceAsStream(fileName));
            } catch (Throwable e) {
                log.warn("Failed to load " + fileName + " file from " + fileName + "(ingore this file): " + e.getMessage(), e);
            }
            return properties;
        }
        
        log.info("load " + fileName + " properties file from " + list);
        
        for(java.net.URL url : list) {
            try {
                Properties p = new Properties();
                InputStream input = url.openStream();
                if (input != null) {
                    try {
                        p.load(input);
                        properties.putAll(p);
                    } finally {
                        try {
                            input.close();
                        } catch (Throwable t) {}
                    }
                }
            } catch (Throwable e) {
                log.warn("Fail to load " + fileName + " file from " + url + "(ingore this file): " + e.getMessage(), e);
            }
        }
        
        return properties;
    }
	
    private static int PID = -1;
    
    public static int getPid() {
        if (PID < 0) {
            try {
                RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();  
                String name = runtime.getName(); // format: "pid@hostname"  
                PID = Integer.parseInt(name.substring(0, name.indexOf('@')));
            } catch (Throwable e) {
                PID = 0;
            }
        }
        return PID;  
    }
	
	public static void main(String[] args) {
		System.out.println(getPid());
		System.out.println(getProperty("environment", "sdf"));
		Properties properties = loadProperties("log4j",false , true);
		addProperties(properties);
		System.out.println(getProperty("log4j.rootLogger", "sdf"));
		setProperties("log4j.rootLogger","error");
		System.out.println(getProperty("log4j.rootLogger", "sdf"));
	}
}
