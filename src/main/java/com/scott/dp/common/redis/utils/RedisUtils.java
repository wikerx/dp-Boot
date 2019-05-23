package com.scott.dp.common.redis.utils;
import java.util.ResourceBundle;

import org.springframework.beans.factory.annotation.Value;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
/**   
 * @ClassName:  RedisClient   
 * @Description:redis客户端 非集群模式  不推荐服务使用
 * @author: Mr.薛 
 * @date:   2019年5月15日 下午4:44:17     
 * @Copyright: 2019 
 * @Company: 自贸通
 */
public class RedisUtils {

	static ResourceBundle resource = ResourceBundle.getBundle("config");//配置文件自己写，spring中都在配置文件yml里面，自己改一下就好了
	//服务器IP地址
    private static String ADDR = resource.getString("redis.ip");
    //端口
    private static int PORT = Integer.parseInt(resource.getString("redis.port").trim());
    //密码
    private static String AUTH = resource.getString("redis.auth");
    //连接实例的最大连接数
    private static int MAX_ACTIVE = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    //等待可用连接的最大时间，单位毫秒，默认值为-1，表示永不超时。如果超过等待时间，则直接抛出JedisConnectionException
    private static int MAX_WAIT = 10000;
    //连接超时的时间　　
    private static int TIMEOUT = 10000;
    // 在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
    private static boolean TEST_ON_BORROW = true;
    private static JedisPool jedisPool = null;
    
	    /**
	     * 初始化Redis连接池
	     */
	    static {
	        try {
	            JedisPoolConfig config = new JedisPoolConfig();
	            config.setMaxTotal(MAX_ACTIVE);
	            config.setMaxIdle(MAX_IDLE);
	            config.setMaxWaitMillis(MAX_WAIT);
	            config.setTestOnBorrow(TEST_ON_BORROW);
	            jedisPool = new JedisPool(config, ADDR, PORT, TIMEOUT, AUTH);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }

	    /**
	     * 获取Jedis实例
	     */
	    public synchronized static Jedis getJedis() {
	        try {
	            if (jedisPool != null) {
	                Jedis resource = jedisPool.getResource();
	                return resource;
	            } else {
	                return null;
	            }

	        } catch (Exception e) {
	            e.printStackTrace();
	            return null;
	        }
	    }

	    /***
	     * 
	     * 释放资源
	     */
	    @SuppressWarnings("deprecation")
		public static void returnResource(final Jedis jedis) {
	            if(jedis != null) {
	                jedisPool.returnResource(jedis);
	            }
	    }
	    
}
