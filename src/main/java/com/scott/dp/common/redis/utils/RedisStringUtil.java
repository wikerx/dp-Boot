package com.scott.dp.common.redis.utils;

import redis.clients.jedis.Jedis;

/**   
 * @ClassName:  RedisStringUtil   
 * @Description:Redis 之 字符处理   
 * @author: Mr.薛 
 * @date:   2019年5月17日 下午3:59:08     
 * @Copyright: 2019 
 * @Company: 自贸通
 */
public class RedisStringUtil {
	/**  
	 * 成功,"OK"  
	 */    
	private static final String SUCCESS_OK = "OK";    
	/**  
	 * 成功,1L  
	 */    
	private static final Long SUCCESS_STATUS_LONG = 1L;    
	/**  
	 * 只用key不存在时才设置。Only set the key if it does not already exist  
	 */    
	private static final String NX = "NX";    
	/**  
	 * XX -- 只有key存在时才设置。和NX相反。Only set the key if it already exist.  
	 */    
	private static final String XX = "XX";    
	/**  
	 * EX|PX, 时间单位，EX是秒，PX是毫秒。expire time units: EX = seconds; PX = milliseconds  
	 */    
	private static final String EX = "EX";    
	/**  
	 * EX|PX, 时间单位，EX是秒，PX是毫秒。expire time units: EX = seconds; PX = milliseconds  
	 */    
//	private static final String PX = "PX";    
	    
	/**  
	 * 成功返回true  
	 * @param key  
	 * @param value  
	 * @return  
	 */    
	public static boolean set(String key, String value){    
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();    
	        String statusCode = jedis.set(key, value);    
	        if(SUCCESS_OK.equalsIgnoreCase(statusCode)){    
	            return true;    
	        }   
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;    
	}    
	    
	/**  
	 * 返回值  
	 * @param key  
	 * @return  
	 */    
	public static String get(String key){  
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();    
	        return jedis.get(key);    
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return null;  
	}    
	    
	/**  
	 * 设置key值和过期时间  
	 * @param key  
	 * @param value  
	 * @param seconds 秒数，不能小于0  
	 * @return  
	 */    
	public static boolean setByTime(String key, String value, int seconds){    
	    if(seconds < 0){    
	        return false;    
	    }   
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();    
	        String statusCode = jedis.setex(key, seconds, value);    
	        if(SUCCESS_OK.equalsIgnoreCase(statusCode)){    
	            return true;    
	        }   
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;    
	}    
	    
	/**  
	 *   
	 * @param key  
	 * @param value  
	 * @param nxxx NX|XX  是否存在  
	 * <li>NX -- Only set the key if it does not already exist.</li>  
	 * <li>XX -- Only set the key if it already exist.</li>  
	 * @param expx EX|PX, expire time units ，时间单位格式，秒或毫秒  
	 * <li>EX = seconds;</li>  
	 * <li>PX = milliseconds</li>  
	 * @param time expire time in the units of expx，时间（long型），不能小于0  
	 * @return  
	 */    
	public static boolean set(String key, String value,     
	        String nxxx, String expx, long time){   
	    if(time < 0){    
	        return false;    
	    }  
	      
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();    
	        String statusCode = jedis.set(key, value, nxxx, expx, time);    
	        if(SUCCESS_OK.equalsIgnoreCase(statusCode)){    
	            return true;    
	        }    
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 设置key  
	 * @param key  
	 * @param value  
	 * @param nxxx NX|XX 是否需要存在  
	 * <li>NX -- Only set the key if it does not already exist.</li>   
	 * <li>XX -- Only set the key if it already exist.</li>   
	 * @return  
	 */    
	public static boolean set(String key, String value,     
	        String nxxx){    
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();    
	        String statusCode = jedis.set(key, value, nxxx);    
	        if(SUCCESS_OK.equalsIgnoreCase(statusCode)){    
	            return true;    
	        }   
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 当key不存在时，设置值，成功返回true  
	 * @param key  
	 * @param value  
	 * @return  
	 */    
	public static boolean setIfNotExists(String key, String value){    
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();    
	        Long statusCode = jedis.setnx(key, value);    
	        if(SUCCESS_STATUS_LONG == statusCode){    
	            return true;    
	        }    
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 当key不存在时，设置值，成功返回true，同setIfNotExists  
	 * @param key  
	 * @param value  
	 * @return  
	 */    
	public static boolean setNX(String key, String value){    
	    return setIfNotExists(key, value);    
	}    
	    
	/**  
	 * 仅当key不存在时则设置值，成功返回true，存在时不设置值  
	 * @param key  
	 * @param value  
	 * @param seconds，秒数，不能小于0  
	 * @return  
	 */    
	public static boolean setIfNotExists(String key, String value, long seconds){    
	    if(seconds < 0){    
	        return false;    
	    }    
	    return set(key, value, NX, EX, seconds);    
	}    
	    
	/**  
	 * 仅当key不存在时则设置值，成功返回true，存在时不设置值，同setIfNotExists(key, value, seconds)  
	 * @param key  
	 * @param value  
	 * @param seconds  
	 * @return  
	 */    
	public static boolean setNX(String key, String value, Long seconds){    
	    return setIfNotExists(key, value, seconds);    
	}    
	    
	/**  
	 * 当key存在时则设置值，成功返回true，不存在不设置值  
	 * @param key  
	 * @param value  
	 * @return  
	 */    
	public static boolean setIfExists(String key, String value){    
	    return set(key, value, XX);    
	}    
	    
	/**  
	 * 当key存在时则设置值，成功返回true，不存在不设置值，同setIfExists  
	 * @param key  
	 * @param value  
	 * @return  
	 */    
	public static boolean setXX(String key, String value){    
	    return setIfExists(key, value);    
	}    
	    
	/**  
	 * 仅当key存在时则设置值，成功返回true，不存在不设置值  
	 * @param key  
	 * @param value  
	 * @param seconds，秒数，不能小于0  
	 * @return  
	 */    
	public static boolean setIfExists(String key, String value, long seconds){    
	    if(seconds < 0){    
	        return false;    
	    }    
	    return set(key, value, XX, EX, seconds);    
	}    
	    
	/**  
	 * 仅当key存在时则设置值，成功返回true，不存在不设置值  
	 * @param key  
	 * @param value  
	 * @param seconds，秒数，不能小于0  
	 * @return  
	 */    
	public static boolean setXX(String key, String value, long seconds){    
	    return setIfExists(key, value, seconds);    
	}    
	    
	/**  
	 * 设置超期时间  
	 * @param key  
	 * @param seconds 为Null时，将会马上过期。可以设置-1，0，表示马上过期  
	 * @return  
	 */    
	public static boolean setTime(String key, Integer seconds){   
	    Jedis jedis = null;  
	    try {  
	        if(seconds == null){    
	            seconds = -1;    
	        }  
	        jedis = RedisUtils.getJedis();  
	        Long statusCode = jedis.expire(key, seconds);    
	        if(SUCCESS_STATUS_LONG == statusCode){    
	            return true;    
	        }   
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 设置超期时间  
	 * @param key  
	 * @param seconds 为Null时，将会马上过期。可以设置-1，0，表示马上过期  
	 * @return  
	 */    
	public static boolean setOutTime(String key, Integer seconds){    
	    return setTime(key, seconds);    
	}    
	    
	/**  
	 * 设置超期时间  
	 * @param key  
	 * @param seconds 秒数，为Null时，将会马上过期。可以设置-1，0，表示马上过期  
	 * @return  
	 */    
	public static boolean expire(String key, Integer seconds){    
	    return setTime(key, seconds);    
	}    
	    
	/**  
	 * 判断key是否存在，存在返回true  
	 * @param key  
	 * @return  
	 */    
	public static boolean exists(String key){    
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();  
	        return jedis.exists(key);  
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 判断key是否存在，存在返回true  
	 * @param key  
	 * @return  
	 */    
	public static boolean isExists(String key){    
	    return exists(key);    
	}    
	    
	/**  
	 * 将key设置为永久  
	 * @param key  
	 * @return  
	 */    
	public static boolean persist(String key){  
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();  
	        long time = getTime(key);    
	        if(time == -1){    
	            return true;    
	        }  
	        //已经是永久的，返回0    
	        Long statusCode = jedis.persist(key);    
	        jedis.close();    
	        if(SUCCESS_STATUS_LONG == statusCode){    
	            return true;    
	        }   
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 获取剩余时间（秒）  
	 * @param key  
	 * @return  
	 */    
	public static Long getTime(String key){  
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();  
	        return jedis.ttl(key);  
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return -1L;    
	}    
	    
	/**  
	 * 获取剩余时间（秒）  
	 * @param key  
	 * @return  
	 */    
	public static Long Ttl(String key){    
	    return getTime(key);    
	}    
	    
	/**  
	 * 随机获取一个key  
	 * @return  
	 */    
	public static String randomKey(){  
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();  
	        return jedis.randomKey();  
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return null;  
	}    
	    
	/**  
	 * 随机获取一个key  
	 * @return  
	 */    
	public static String random(){    
	    return randomKey();    
	}    
	    
	/**  
	 * 修改 key 的名称，成功返回true，如果不存在该key，则会抛错：ERR no such key  
	 * 注：如果newKey已经存在，则会进行覆盖。建议使用renameNX  
	 * @param oldkey 原来的key  
	 * @param newKey 新的key  
	 * @return  
	 */    
	public static boolean rename(String oldkey, String newKey){  
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();  
	        String statusCode = jedis.rename(oldkey, newKey);    
	        if(SUCCESS_OK.equalsIgnoreCase(statusCode)){    
	            return true;    
	        }   
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 仅当 newkey 不存在时，将 key 改名为 newkey 。成功返回true  
	 * @param oldkey  
	 * @param newKey  
	 * @return  
	 */    
	public static boolean renameNX(String oldkey, String newKey){   
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();  
	        Long statusCode = jedis.renamenx(oldkey, newKey);    
	        if(SUCCESS_STATUS_LONG == statusCode){    
	            return true;    
	        }  
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 仅当 newkey 不存在时，将 key 改名为 newkey 。成功返回true  
	 * @param oldkey  
	 * @param newKey  
	 * @return  
	 */    
	public static boolean renameIfNotExists(String oldkey, String newKey){    
	    return renameNX(oldkey, newKey);    
	}    
	    
	/**  
	 * 返回 key 所储存的值的类型。  
	 * @param key  
	 * @return  
	 */    
	public static String type(String key){    
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();  
	        return jedis.type(key);    
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return null;  
	}    
	    
	/**  
	 * 返回 key 所储存的值的类型。  
	 * @param key  
	 * @return  
	 */    
	public static String getType(String key){    
	    return type(key);    
	}    
	    
	/**  
	 * 删除key及值  
	 * @param key  
	 * @return  
	 */    
	public static boolean del(String key){    
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();  
	        Long statusCode = jedis.del(key);    
	        if(SUCCESS_STATUS_LONG == statusCode){    
	            return true;    
	        }  
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 删除key及值  
	 * @param key  
	 * @return  
	 */    
	public static boolean delete(String key){    
	    return del(key);    
	}    
	    
	/**  
	 * 删除key及值  
	 * @param key  
	 * @return  
	 */    
	public static boolean remove(String key){    
	    return del(key);    
	}    
	    
	/**  
	 * 批量删除key及值  
	 * @param key  
	 * @return  
	 */    
	public static boolean del(String[] keys){  
	    Jedis jedis = null;  
	    try {  
	        jedis = RedisUtils.getJedis();  
	        Long statusCode = jedis.del(keys);    
	        if(statusCode > 0){    
	            return true;    
	        }  
	    }catch (Exception e) {  
	        e.printStackTrace();  
	    }finally{  
	        if(jedis != null){  
	            jedis.close();  
	        }  
	    }  
	    return false;  
	}    
	    
	/**  
	 * 批量删除key及值  
	 * @param key  
	 * @return  
	 */    
	public static boolean delete(String[] keys){    
	    return del(keys);    
	}    
	    
	/**  
	 * 批量删除key及值  
	 * @param key  
	 * @return  
	 */    
	public static boolean remove(String[] keys){    
	    return del(keys);    
	}   
	  
	  
	public static void main(String[] args) {  
//	    System.out.println(set("name", "Mr.薛"));  
	    System.out.println(setByTime("name1", "Mr.薛1", 10));  
	    System.out.println(getTime("name1"));  
	    /*System.out.println(set("wa", "哈哈", NX, EX, 10L)); 
	    System.out.println(set("wa", "哈哈60", XX, EX, 60L));*/  
	    //System.out.println(set("wa", "哈哈哈哈2", XX));  
//	    System.out.println(setIfNotExists("wa", "哈哈not"));  
	    //System.out.println(setIfNotExists("wa", "哈哈not", 30));  
	    //System.out.println(setIfExists("wahaha", "有就设置"));  
	    //System.out.println(setIfExists("wahaha", "有就设置", 60));  
	    //System.out.println(setTime("wa", -1));  
	    //System.out.println(exists("wa"));  
	    //System.out.println(isExists("wa"));  
	    //System.out.println(setByTime("wa", "30秒过期", 30));  
	    //System.out.println(persist("wa"));  
	    /*for(int i=0; i<30; i++){ 
	        System.out.println(randomKey()); 
	    }*/  
	    //System.out.println(rename("waa", "wa"));  
	    //System.out.println(renameNX("waa", "waa"));  
	    //System.out.println(getType("wa"));  
	    /*System.out.println(del("wa")); 
	    System.out.println(get("wa")); 
	    System.out.println(Ttl("wa"));*/  
//	    System.out.println(del(new String[]{"a"}));  
	    System.out.println(get("name"));
	}  
  
}
