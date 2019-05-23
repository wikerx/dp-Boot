package com.scott.dp.common.redis.utils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.scott.dp.common.utils.Ognl;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

/**   
 * @ClassName:  RedisHashUtil   
 * @Description:Redis 之Hash操作   
 * @author: Mr.薛 
 * @date:   2019年5月17日 下午3:39:06     
 * @Copyright: 2019 
 * @Company: 自贸通
 */
public class RedisHashUtil {
	/**  
     * 成功,"OK"  
     */    
    private static final String SUCCESS_OK = "OK";    
    /**  
     * 成功,1L  
     */    
    private static final Long SUCCESS_STATUS_LONG = 1L;    
	
	/***Redis hash 是一个string类型的field和value的映射表，hash特别适合用于存储对象。***/  
    /** 
     * 设置Hash的属性 
     * @param key 
     * @param field 
     * @param value 
     * @return 
     */  
    public static boolean hset(String key, String field, String value){  
        if(Ognl.isEmpty(field)){  
            return false;  
        }  
        Jedis jedis = RedisUtils.getJedis(); 
        //If the field already exists, and the HSET just produced an update of the value, 0 is returned,   
        //otherwise if a new field is created 1 is returned.  
        Long statusCode = jedis.hset(key, field, value);  
        jedis.close();  
        if(statusCode > -1){  
            return true;  
        }  
        return false;  
    }  
      
    /** 
     * 批量设置Hash的属性 
     * @param key 
     * @param fields 
     * @param values 
     * @return 
     */  
    public static boolean hmset(String key, String[] fields, String[] values){  
        if(Ognl.isEmpty(key) || Ognl.isEmpty(fields) || Ognl.isEmpty(values)){  
            return false;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        Map<String, String> hash = new HashMap<String, String>();  
        for (int i=0; i<fields.length; i++) {  
            hash.put(fields[i], values[i]);  
        }  
        String statusCode = jedis.hmset(key, hash);  
        jedis.close();  
        if(SUCCESS_OK.equalsIgnoreCase(statusCode)){  
            return true;  
        }  
        return false;  
    }  
      
    /** 
     * 批量设置Hash的属性 
     * @param key 
     * @param map Map<String, String> 
     * @return 
     */  
    public static boolean hmset(String key, Map<String, String> map){  
        if(Ognl.isEmpty(key) || Ognl.isEmpty(map)){  
            return false;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        String statusCode = jedis.hmset(key, map);  
        jedis.close();  
        if(SUCCESS_OK.equalsIgnoreCase(statusCode)){  
            return true;  
        }  
        return false;  
    }  
      
    /** 
     * 仅当field不存在时设置值，成功返回true 
     * @param key 
     * @param field 
     * @param value 
     * @return 
     */  
    public static boolean hsetNX(String key, String field, String value){  
        if(Ognl.isEmpty(key) || Ognl.isEmpty(field)){  
            return false;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        //If the field already exists, 0 is returned,    
        //otherwise if a new field is created 1 is returned.  
        Long statusCode = jedis.hsetnx(key, field, value);
        jedis.close();  
        if(SUCCESS_STATUS_LONG == statusCode){  
            return true;  
        }  
        return false;  
    }  
      
    /** 
     * 获取属性的值 
     * @param key 
     * @param field 
     * @return 
     */  
    public static String hget(String key, String field){  
        if(Ognl.isEmpty(key) || Ognl.isEmpty(field)){
            return null;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        String value = jedis.hget(key, field);  
        jedis.close();  
        return value;  
    }  
      
    /** 
     * 批量获取属性的值 
     * @param key 
     * @param fields String... 
     * @return 
     */  
    public static List<String> hmget(String key, String... fields){  
        if(Ognl.isEmpty(key) || Ognl.isEmpty(fields)){  
            return null;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        List<String> values = jedis.hmget(key, fields);  
        jedis.close();  
        return values;  
    }  
      
    /** 
     * 获取在哈希表中指定 key 的所有字段和值 
     * @param key 
     * @return Map<String, String> 
     */  
    public static Map<String, String> hgetAll(String key){  
        if(Ognl.isEmpty(key)){  
            return null;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        Map<String, String> map = jedis.hgetAll(key);  
        jedis.close();  
        return map;  
    }  
      
    /** 
     * 删除hash的属性 
     * @param key 
     * @param fields 
     * @return 
     */  
    public static boolean hdel(String key, String... fields){  
        if(Ognl.isEmpty(key) || Ognl.isEmpty(fields)){  
            return false;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        jedis.hdel(key, fields);  
        jedis.close();  
        return true;  
    }  
      
    /** 
     * 查看哈希表 key 中，指定的字段是否存在。 
     * @param key 
     * @param field 
     * @return 
     */  
    public static boolean hexists(String key, String field){  
        if(Ognl.isEmpty(key) || Ognl.isEmpty(field)){  
            return false;  
        }  
        Jedis jedis = RedisUtils.getJedis();  
        boolean result = jedis.hexists(key, field);  
        jedis.close();  
        return result;  
    }  
      
    /** 
     * 为哈希表 key 中的指定字段的整数值加上增量 increment 。 
     * @param key 
     * @param field 
     * @param increment 正负数、0、正整数 
     * @return 
     */  
    public static long hincrBy(String key, String field, long increment){  
        Jedis jedis = RedisUtils.getJedis();  
        long result = jedis.hincrBy(key, field, increment);  
        jedis.close();  
        return result;  
    }  
      
    /** 
     * 为哈希表 key 中的指定字段的浮点数值加上增量 increment 。(注：如果field不存在时，会设置新的值) 
     * @param key 
     * @param field 
     * @param increment，可以为负数、正数、0 
     * @return 
     */  
    public static Double hincrByFloat(String key, String field, double increment){  
        Jedis jedis = RedisUtils.getJedis();  
        Double result = jedis.hincrByFloat(key, field, increment);  
        jedis.close();  
        return result;  
    }  
      
    /** 
     * 获取所有哈希表中的字段 
     * @param key 
     * @return Set<String> 
     */  
    public static Set<String> hkeys(String key){  
        Jedis jedis = RedisUtils.getJedis();  
        Set<String> result = jedis.hkeys(key);  
        jedis.close();  
        return result;  
    }  
      
    /** 
     * 获取哈希表中所有值 
     * @param key 
     * @return List<String> 
     */  
    public static List<String> hvals(String key){  
        Jedis jedis = RedisUtils.getJedis();  
        List<String> result = jedis.hvals(key);  
        jedis.close();  
        return result;  
    }  
      
    /** 
     * 获取哈希表中字段的数量，当key不存在时，返回0 
     * @param key 
     * @return 
     */  
    public static Long hlen(String key){  
        Jedis jedis = RedisUtils.getJedis();  
        Long result = jedis.hlen(key);  
        jedis.close();  
        return result;  
    }  
      
    /** 
     * 迭代哈希表中的键值对。 
     * @param key 
     * @param cursor 
     * @return ScanResult<Entry<String, String>> 
     */  
    public static ScanResult<Entry<String, String>> hscan(String key, String cursor){  
        Jedis jedis = RedisUtils.getJedis();  
        ScanResult<Entry<String, String>> scanResult = jedis.hscan(key, cursor);   
        jedis.close();  
        //System.out.println(scanResult.getResult());  
        return scanResult;  
    }  
    
    
    public static void main(String[] args) {
		/*录入hash值*/
//    	System.out.println(hset("merchantHash", "1002", "1002-商户信息"));
    	
    	/*判断值是否存在*/
//    	System.out.println(hsetNX("merchantHash", "1002", "1002-商户信息"));
    	
    	/*获取Hash中的所有key数据*/
//    	Set<String> setKeys = hkeys("merchantHash");//获取所有key
//    	for (String str : setKeys) {  
//    	      System.out.println(str);  
//    	} 
    	
    	/*获取Hash中的所有value数据*/
//    	List<String> listValues = hvals("merchantHash");//获取所有value
//    	for(String str:listValues){
//    		System.out.println(str);
//    	}
    	
    	/*获取属性的值*/
    	System.out.println("1002:"+hget("merchantHash", "1002"));
    	
    	/*删除指定键值*/
    	System.out.println("移除数据："+hdel("merchantHash", "1003"));
    	
	}
}
