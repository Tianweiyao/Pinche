package com.hodehtml.demo.redis;

import com.hodehtml.demo.utils.StringUtils;
import com.hodehtml.demo.utils.ValidatorUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class RedisUtil {
	private static final Log log = LogFactory.getLog(RedisUtil.class);
	public BaseJedis jedisUtils = null;
		
	public static RedisUtil getJedisUtils(RedisEnum enums){
		RedisUtil utils = new RedisUtil();
		if(enums.CODE.equals(RedisEnum.DISTRIBUTE_LOCK.CODE)){
			utils.jedisUtils = new DistributeJedis();
		}
		utils.jedisUtils = new DataJedis();
		return utils;
	}
	
	
	/**
	 * 从REDIS队列头部取出数据
	 * @param channalName REDIS的键
	 * @return
	 */
	public  String getListHead(String channalName){
		String result = null;
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			result = jedis.lpop(channalName);
		} catch (Exception e) {
			e.printStackTrace();
			 broken = true;
			log.error(" channelName:"+channalName+" 队列读取失败");
		}finally{
			close(jedis, broken);
		}
		return result;
	}
	
	/**
	 * 往REDIS队列尾部中添加数据
	 * @param key
	 * @param params
	 */
	public  void addListEnd(String key,String params){
		if(ValidatorUtils.isEmpty(params)||"{}".equals(params)){
			return;
		}
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			jedis.rpush(key, params);
			log.info(" channelName:"+key+" 添加参数:"+params+"添加到队列成功。");
		} catch (Exception e) {
			e.printStackTrace();
			 broken = true;
			log.error(" channelName:"+key+" 添加参数:"+params+"失败，稍后重新添加,异常信息:"+ StringUtils.nullToStrTrim(e.getMessage()));
		}finally{
			close(jedis, broken);
		}
	}

	
	/**
	 * 获取哈希值
	 * @param group
	 * @param key
	 * @return
	 */
	public  String getHashKey(String group,String key){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			return jedis.hget(group, key);
		} catch (Exception e) {
			log.error(" 缓存方法名 method:"+group + " cachekey " + key + " 获取失败，稍后重新添加,异常信息:" 
					+ StringUtils.nullToStrTrim(e.getMessage()));
			e.printStackTrace();
			broken = true;
		}
		finally{
			close(jedis, broken);
		}
		return "";
	}
	
	/**
	 * 设置哈希值
	 * @param group
	 * @param key
	 * @param value
	 */
	public  void setHashKey(String group,String key, String value){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			jedis.hset(group, key, value);
			log.info(" 缓存方法名 method:"+group + " cachekey " + key +" 添加值："+value+"更新成功。");
		} catch (Exception e) {
			log.error(" 缓存方法名 method:"+group + " cachekey " + key  +" 值：" + value + "失败，稍后重新添加,异常信息:"+StringUtils.nullToStrTrim(e.getMessage()));
			 broken = true;
		}
		finally{
			close(jedis, broken);
		}
	}
	
	
	/**
	 * 删除哈希值
	 * @param group
	 * @param key
	 * @return
	 */
	public  String delHashKey(String group,String key){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			if(jedis.hexists(group, key)){
				jedis.hdel(group, key);
			}
		} catch (Exception e) {
			log.error(" 缓存方法名 method:"+group + " cachekey " + key + " 删除失败，稍后重新添加,异常信息:" 
					+ StringUtils.nullToStrTrim(e.getMessage()));
			e.printStackTrace();
			 broken = true;
		}
		finally{
			close(jedis, broken);
		}
		return "";
	}
	
	/**
	 * 判断哈希值是否存在
	 * @param group
	 * @param key
	 * @return
	 */
	public  boolean existsHashKey(String group, String key){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			return jedis.hexists(group, key);
		} catch (Exception e) {
			log.error(" 缓存方法名 method:"+group + " key " + key + "获取existsKey失败，稍后重新添加,异常信息:" 
					+ StringUtils.nullToStrTrim(e.getMessage()));
			 broken = true;
		}
		finally{
			close(jedis, broken);
		}
		return false;
	}
	
	/**
	 * 获得哈希值的所有key
	 * @param group 
	 * @return
	 */
	public  Set<String> getHashKeys(String group){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			return jedis.hkeys(group);			
		} catch (Exception e) {
			log.error(" 缓存方法名 method:"+group + "获取getKeys失败，稍后重新添加,异常信息:" 
					+ StringUtils.nullToStrTrim(e.getMessage()));
			broken = true;
		}
		finally{
			close(jedis, broken);
		}
		return null;
	}
	
	
	/**
	 * 删除哈希keys
	 * @param group 
	 */
	public  void delHashKeys(String group){
		try {
			Set<String> methodKeys = getHashKeys(group);
			if(ValidatorUtils.isEmpty(methodKeys))
			{
				log.info(" 缓存方法名 method:"+group + "不存在key值。");
				return;
			}
			for(String cachekey : methodKeys ){
				delHashKey(group,cachekey); 
	        }
		} catch (Exception e) {
			log.error(" 缓存方法名 method:"+group 
					+ " delCacheMethod失败，稍后重新添加,异常信息:"+StringUtils.nullToStrTrim(e.getMessage()));
		}
	}
	

	
	/**
	 *  redis 操作 字符串 获取值
	 * @param
	 * @param key 设置的KEY
	 * @return
	 */
	public  String getStringKey(String key){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			return jedis.get(key);
		} catch (Exception e) {
			e.printStackTrace();
			broken = true;
			log.error(String.format("获取key:[%s]对应的值,操作失败!",key));
		}
		finally{
			close(jedis, broken);
		}
		return null;
	}
	
	/**
	 *  redis 操作 字符串 设置值
	 * @param
	 * @param key  设置的KEY
	 * @param value 设置的值
	 */
	public  void setStringKey(String key,String value){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			jedis.set(key, value);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(String.format("设置key:[%s],对应值:[%s],操作失败!",key,value));
			broken = true;
		}
		finally{
			close(jedis, broken);
		}
	}
	
	/**
	 *  redis 操作 字符串 设置值
	 * @param
	 * @param key  设置的KEY
	 * @param value 设置的值
	 * @param seconds 超时时间
	 */
	public  void addStringKeyExpire(String key,String value,int seconds){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			jedis.setex(key, seconds, value);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(String.format("设置key:[%s],对应值:[%s],操作失败!",key,value));
			broken = true;
		}
		finally{
			close(jedis, broken);
		}
	}
	
	/**
	 *  redis 操作 字符串 设置值
	 * @param
	 * @param key  设置的KEY
	 * @param
	 * @param seconds 超时时间
	 */
	public  void setStringKeyExpire(String key,int seconds){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			jedis.expire(key, seconds);
		} catch (Exception e) {
			e.printStackTrace();
			log.error(String.format("设置key:[%s],对应值:[%s],操作失败!",key,seconds));
			broken = true;
		}
		finally{
			close(jedis, broken);
		}
	}
	
	/**
	 * redis 操作字符串 清除值
	 * @param
	 * @param key 设置的KEY
	 */
	public  void delStringKey(String key){
		Jedis jedis = null;
		boolean broken = false;
		try {
			jedis = jedisUtils.getJedis();
			jedis.del(key);
		} catch (Exception e) {
			e.printStackTrace();
			broken = true;
			log.error(String.format("删除key:[%s],对应值,操作失败!",key));
		}
		finally{
			close(jedis, broken);
		}
	}
	
	public JedisPool getPool(){
		return jedisUtils.getPool();
	}
	
	public void close(Jedis jedis, boolean broken){
		try {
			if(broken){
				jedisUtils.returnBrokenRedis(jedis);
			}else{
				jedisUtils.closeJedis(jedis);
			}
		} catch (Exception e) {
        	log.error("return back jedis failed, will fore close the jedis."+e.getMessage());
            if ((jedis != null) && jedis.isConnected()) {
                try {
                    try {
                        jedis.quit();
                    } catch (Exception e2) {
                    }
                    jedis.disconnect();
                } catch (Exception e3) {
                }
            }
        
		}
	}
	
}
