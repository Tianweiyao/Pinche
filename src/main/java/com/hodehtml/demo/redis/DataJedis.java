package com.hodehtml.demo.redis;

import com.hodehtml.demo.utils.TimeUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.exceptions.JedisException;

public class DataJedis extends BaseJedis{
	private static final Log log = LogFactory.getLog(DataJedis.class);

	private static JedisPool pool  = null;

	@Override
	public JedisPool getPool() {
    	if(pool==null){
        	pool = initPool(RedisEnum.DATA.CODE);
    	}
		return pool;
	}
	
	public String get30Random(RedisKeyEnum enums){		
		String recordNo = TimeUtils.getSysTime("yyyyMMddhhmmssSSS");
		String random = null;
		Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = getPool().getResource();
            Long nums = jedis.scard(enums.KEY);
            if(nums<100){
            	for(int i=0;i<1000;i++){
                	jedis.sadd(enums.KEY, (int)(Math.random()*9000+1000)+"");
            	}
            }
            random = jedis.spop(enums.KEY);            
        } catch (JedisException e) {
        	e.printStackTrace();
        	log.error(e.getMessage());
            random = (int)(Math.random()*9000+1000)+"";
            broken = true;
        } finally {
    		if(broken){
    			returnBrokenRedis(jedis);
    		}else{
    			closeJedis(jedis);
    		}
    	}
		return recordNo+"000000000"+random;
	}
	

	public String get16Random(RedisKeyEnum enums){		
		String recordNo = TimeUtils.getSysTimeLong().substring(2);
		String random = null;
		Jedis jedis = null;
        boolean broken = false;
        try {
            jedis = getPool().getResource();
            Long nums = jedis.scard(enums.KEY);
            if(nums<100){
            	for(int i=0;i<1000;i++){
                	jedis.sadd(enums.KEY, (int)(Math.random()*9000+1000)+"");
            	}
            }
            random = jedis.spop(enums.KEY);            
        } catch (JedisException e) {
        	e.printStackTrace();
        	log.error(e.getMessage());
            random = (int)(Math.random()*9000+1000)+"";
            broken = true;
        } finally {
    		if(broken){
    			returnBrokenRedis(jedis);
    		}else{
    			closeJedis(jedis);
    		}
    	}
		return recordNo+random;
	}


}
