package com.hodehtml.demo.redis;

import com.hodehtml.demo.utils.PropertiesConfig;
import com.hodehtml.demo.utils.ValidatorUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public abstract class BaseJedis {

	public  String fileName = "redis.properties";
	protected  String redis_ip=null;
	protected  String redis_port=null;
	protected  String redis_password=null;
	protected  String redis_pool_maxActive=null;
	protected  String redis_pool_maxIdle=null;
	protected  String redis_pool_maxWait=null;
	protected  String redis_pool_testOnBorrow=null;
	protected  String redis_pool_testOnReturn=null;
	protected  String timeout=null;
	protected  String retrynum=null;
	
	public abstract JedisPool getPool();

	public JedisPool initPool(String key) {
		JedisPool pool = null;
		redis_ip = PropertiesConfig.getProperties(fileName, "redis."+key+".ip");
		redis_port = PropertiesConfig.getProperties(fileName, "redis."+key+".port");
		redis_password = PropertiesConfig.getProperties(fileName, "redis."+key+".password");
		redis_pool_maxActive = PropertiesConfig.getProperties(fileName, "redis."+key+".pool.maxActive");
		redis_pool_maxIdle = PropertiesConfig.getProperties(fileName, "redis."+key+".pool.maxIdle");
		redis_pool_maxWait = PropertiesConfig.getProperties(fileName, "redis."+key+".pool.maxWait");
		redis_pool_testOnBorrow = PropertiesConfig.getProperties(fileName, "redis."+key+".pool.testOnBorrow");
		redis_pool_testOnReturn = PropertiesConfig.getProperties(fileName, "redis."+key+".pool.testOnReturn");
		timeout = PropertiesConfig.getProperties(fileName, "redis."+key+".timeout");
		retrynum = PropertiesConfig.getProperties(fileName, "redis."+key+".retrynum");
		
    	int port = Integer.valueOf(redis_port);
    	JedisPoolConfig config = new JedisPoolConfig();
    	config.setMaxTotal(Integer.valueOf(redis_pool_maxActive));
    	config.setMaxIdle(Integer.valueOf( redis_pool_maxIdle));
    	config.setMaxWaitMillis(Long.valueOf(redis_pool_maxWait));
    	config.setTestOnBorrow(Boolean.valueOf(redis_pool_testOnBorrow));
    	config.setTestOnReturn(Boolean.valueOf(redis_pool_testOnReturn));
        try{
        	if(ValidatorUtils.isNotNull(redis_password)){
                pool = new JedisPool(config, redis_ip, port,Integer.valueOf(timeout),redis_password);
        	}else{
                pool = new JedisPool(config, redis_ip, port,Integer.valueOf(timeout));
        	}
        } catch(Exception e) {
            e.printStackTrace();
        }
        return pool;
    }
	
	public  void clear(){
		redis_ip=null;
		redis_port=null;
		redis_password=null;
		redis_pool_maxActive=null;
		redis_pool_maxIdle=null;
		redis_pool_maxWait=null;
		redis_pool_testOnBorrow=null;
		redis_pool_testOnReturn=null;
		timeout=null;
		retrynum=null;
		if(getPool()!=null){
			getPool().destroy();
		}
	}
	

	/**
	 * 获取Redis实例.
	 * @return Redis工具类实例
	 */
	public Jedis getJedis() {
		Jedis jedis  = null;
		int count =0;
		do{
    		try{ 
    			jedis = getPool().getResource();
    		} catch (Exception e) {
    			e.printStackTrace();
    			 // 销毁对象  
    			returnBrokenRedis(jedis);
    		}
    		count++;
		}while(jedis==null&&count<Integer.valueOf(retrynum));
		return jedis;
	}
	
	/**
	 * 释放redis实例到连接池.
     * @param jedis redis实例
     */
	public void returnBrokenRedis(Jedis jedis) {
		if(jedis != null) {
			jedis.close();
			jedis = null;
		}
	}

	/**
	 * 释放redis实例到连接池.
     * @param jedis redis实例
     */
	public void closeJedis(Jedis jedis) {
		if(jedis != null) {
			jedis.close();
			jedis = null;
		}
	}
}
