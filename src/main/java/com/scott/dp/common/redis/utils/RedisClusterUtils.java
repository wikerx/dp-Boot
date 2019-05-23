package com.scott.dp.common.redis.utils;

import java.io.IOException;
import java.util.LinkedHashSet;
import java.util.Set;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPoolConfig;

/**   
 * @ClassName:  RedisClusterUtil   
 * @Description:Redis 集群模式   到时候用xml配置加载
 * @author: Mr.薛 
 * @date:   2019年5月17日 下午4:14:38     
 * @Copyright: 2019 
 * @Company: 自贸通
 */
public class RedisClusterUtils {
	//连接实例的最大连接数
    private static int MAX_ACTIVE = 1024;
    //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例，默认值也是8。
    private static int MAX_IDLE = 200;
    //连接超时的时间　　单位毫秒
    private static int TIMEOUT = 10000;
    
	
	/**
	 * 获取集群
	 * */
	public synchronized static JedisCluster getJedisCluster(){
		JedisPoolConfig poolConfig = new JedisPoolConfig();
	    // 最大连接数
	    poolConfig.setMaxTotal(MAX_ACTIVE);
	    // 最大空闲数
	    poolConfig.setMaxIdle(MAX_IDLE);
	    // 最大允许等待时间，如果超过这个时间还未获取到连接，则会报JedisException异常：
	    // Could not get a resource from the pool
	    poolConfig.setMaxWaitMillis(TIMEOUT);
	    Set<HostAndPort> nodes = new LinkedHashSet<HostAndPort>();
	    nodes.add(new HostAndPort("192.168.0.55", 6379));
	    nodes.add(new HostAndPort("192.168.0.55", 6380));
	    nodes.add(new HostAndPort("192.168.0.55", 6381));
	    nodes.add(new HostAndPort("192.168.0.55", 6382));
	    nodes.add(new HostAndPort("192.168.0.55", 6383));
	    nodes.add(new HostAndPort("192.168.0.55", 6384));
	    JedisCluster cluster = new JedisCluster(nodes, poolConfig);
	    return cluster;
	}
	
	/**
	 * 关闭资源
	 * */
	public static void close(JedisCluster cluster){
		try {
			cluster.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void main(String[] args) {
		getJedisCluster();
	}
}
