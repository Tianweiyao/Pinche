package com.hodehtml.demo.redis;


import redis.clients.jedis.JedisPool;

public class DistributeJedis extends BaseJedis{
	
	private static JedisPool pool  = null;
	@Override
	public JedisPool getPool() {
    	if(pool==null){
        	pool = initPool(RedisEnum.DISTRIBUTE_LOCK.CODE);
    	}
		return pool;
	}
	

}