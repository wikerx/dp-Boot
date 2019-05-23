package com.scott.dp.common.activemq;
import java.util.HashMap;
import java.util.Map;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageConsumer;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.TextMessage;

import com.scott.dp.common.utils.HttpClientRequest;
import com.scott.dp.common.utils.JSON;
import org.apache.activemq.ActiveMQConnectionFactory;

/**   
 * @ClassName:  ActiveMessage   
 * @Description:ActiveMQ消息的创建工具类
 * @author: Mr.薛 
 * @date:   2019年5月22日 上午11:29:26     
 * @Copyright: 2019 
 * @Company: 自贸通
 */
public class ActiveMessageUtil {
//	//ActiveMq 的默认用户名
//    private static final String USERNAME = ActiveMQConnection.DEFAULT_USER;//null
//    //ActiveMq 的默认登录密码
//    private static final String PASSWORD = ActiveMQConnection.DEFAULT_PASSWORD;//null
//    //ActiveMQ 的链接地址
//    private static final String BROKEN_URL = ActiveMQConnection.DEFAULT_BROKER_URL;//failover://tcp://localhost:61616
	
    private static final String USERNAME = "admin";
    private static final String PASSWORD = "admin";
    private static final String BROKEN_URL = "failover://tcp://192.168.0.164:61616";
    //链接工厂
    ConnectionFactory connectionFactory;
    //链接对象
    Connection connection;
    //事务管理
    Session session;
     

    //初始化消息工厂 - 生产
    public void initProduct(){
        try {
            //创建一个链接工厂
            connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
            //从工厂中创建一个链接
            connection  = connectionFactory.createConnection();
            //开启链接
            connection.start();
            //创建一个事务（这里通过参数可以设置事务的级别）
            session = connection.createSession(true,Session.SESSION_TRANSACTED);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }
    //初始化消息工厂 - 消费
    public void initConsume(){
    	try {
    		//创建一个链接工厂
    		connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
    		//从工厂中创建一个链接
    		connection  = connectionFactory.createConnection();
    		//开启链接
    		connection.start();
    		//创建一个事务（这里通过参数可以设置事务的级别）
    		session = connection.createSession(false,Session.AUTO_ACKNOWLEDGE);
    	} catch (JMSException e) {
    		e.printStackTrace();
    	}
    }
    
    
  //初始化消息工厂 - true:生产 false：消费
    public void init(boolean flag){
    	try {
    		//创建一个链接工厂
    		connectionFactory = new ActiveMQConnectionFactory(USERNAME,PASSWORD,BROKEN_URL);
    		//从工厂中创建一个链接
    		connection  = connectionFactory.createConnection();
    		//开启链接
    		connection.start();
    		//创建一个事务（这里通过参数可以设置事务的级别）
    		session = connection.createSession(flag,Session.AUTO_ACKNOWLEDGE);
    	} catch (JMSException e) {
    		e.printStackTrace();
    	}
    }
    
    
    /**
     * @param messageName:消息名称
     * @param messageMsg：消息内容 可以设置为Object，根据需求自己来设定
     * @throws JMSException 
     * */
//    创建消息
    public void createMessage(String messageName,String messageMsg) throws JMSException{
    	ThreadLocal<MessageProducer> producerLocal = new ThreadLocal<>();
    	//创建一个消息队列
        Queue queue = session.createQueue(messageName);
        //消息生产者
        MessageProducer messageProducer = null;
        if(producerLocal.get()!=null){
            messageProducer = producerLocal.get();
        }else{
            messageProducer = session.createProducer(queue);
            producerLocal.set(messageProducer);
        }
        //创建消息存储在队列中
    	TextMessage msg = session.createTextMessage(messageMsg);
    	//发送消息
        messageProducer.send(msg);
        //提交事务
        session.commit();
    }
    
    
//    消费消息 - 订阅
    public void consumeMessageSubscribe(String messageName,int ms) throws JMSException, InterruptedException{
    	ThreadLocal<MessageConsumer> consumerLocal = new ThreadLocal<>();
            Queue queue = session.createQueue(messageName);
            MessageConsumer consumer = null;
            if(consumerLocal.get()!=null){
                consumer = consumerLocal.get();
            }else{
                consumer = session.createConsumer(queue);
                consumerLocal.set(consumer);
            }
            while(true){
            	if(ms > 0){//消费间隔时间
            		Thread.sleep(ms);//等待时间 - ms
            	}
                //订阅消息
                TextMessage msg = (TextMessage) consumer.receive();
                //存在即消费
                if(msg!=null) {
                    msg.acknowledge();
//                    System.out.println(Thread.currentThread().getName()+": 我是消费者，我正在消费Msg"+msg.getText());
                }else {
                    break;
                }
            }
    }
    
   
	 /**
     * @param messageName:消息名称
     * @param ms：等待时间 ms
     * @param num 推送次数
	 * @throws JMSException 
	 * @throws InterruptedException 
     * */
//    消费消息 - 推送
    public void send(String messageName,int ms,int num) throws JMSException, InterruptedException{
    	ThreadLocal<MessageConsumer> consumerLocal = new ThreadLocal<>();
        Queue queue = session.createQueue(messageName);
        MessageConsumer consumer = null;
        if(consumerLocal.get()!=null){
            consumer = consumerLocal.get();
        }else{
            consumer = session.createConsumer(queue);
            consumerLocal.set(consumer);
        }
        while(true){
        	if(ms > 0){//消费间隔时间
        		Thread.sleep(ms);//等待时间 - ms
        	}
            //订阅消息
            TextMessage msg = (TextMessage) consumer.receive();
            //存在即消费
            if(msg!=null) {
                msg.acknowledge();//消费消息
                System.out.println(Thread.currentThread().getName()+": 我是消费者，我正在消费Msg"+msg.getText());
                OrderEntity order = (OrderEntity) JSON.toObject(msg.getText(), OrderEntity.class);
//                常规处理方式是将msg.getText() 转为数据实体取出来消费掉，这里简化写个demo
                String url = order.getNeedAddress();
                Map<String,String> map = new HashMap<>();
                map.put("orderNo", order.getOrderNo());
                //异步通知
                CallBack(url, num, map);
                
            }else {
                break;
            }
        }
    }  
    
    
    public static void CallBack(String url,int num,Map<String,String> map){
    	int count = 1;
		String result = HttpClientRequest.postMap(url, map);
		if(!result.equals("succeed")){
			for(int i = 0; i < num; i++){
				if(!result.equals("succeed") && count < num){
					count++;
					result = HttpClientRequest.postMap(url, map);
				}else{
					break;
				}
			}
		}
	}
	
    

}
