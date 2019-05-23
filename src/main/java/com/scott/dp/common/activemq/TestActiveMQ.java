package com.scott.dp.common.activemq;
import com.scott.dp.common.utils.JSON;

import javax.jms.JMSException;


/**   
 * @ClassName:  TestActiveMQ   
 * @Description:
 * @author: Mr.薛 
 * @date:   2019年5月22日 上午11:48:19     
 * @Copyright: 2019 
 * @Company: 自贸通
 */
@SuppressWarnings("unused")
public class TestActiveMQ {

    //消息推送次数
	private static final int SEND_NUMBER = 5;
    //推送地址
    private static final String SEND_URL = "http://192.168.0.164:8080/paymentServer/getRedisOrder";
    
    
    
    
	public static void main(String[] args) throws JMSException, InterruptedException {
		ActiveMessageUtil active = new ActiveMessageUtil();
		String messageName = "Mr-Xue";
//		生产
//		product(messageName, active);
		
//		消费
		consume(messageName, active);
		
//		初始化 - 消费 - 推送 用的是http tcp的见后面
//		consumeN(messageName, active);
		
		
		
	}
	
	//生产
	public static void product(String messageName,ActiveMessageUtil active){
//		初始化 - 生产
		active.initProduct();
//		String messageMsg = "{ \"name\":\"菜鸟\" , \"url\":\"www.baidu.com\" }";
		try {
			/*生产消息*/
			for (int i = 0; i < 100; i++) {
				active.createMessage(messageName, JSON.toJson(getOrder(i, SEND_URL)));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	//消费 - 订阅
	public static void consume(String messageName,ActiveMessageUtil active) throws JMSException, InterruptedException{
//		初始化 - 消费 - 订阅
		active.initConsume();
		/*消费消息*/
		active.consumeMessageSubscribe(messageName,500);
	}
	//消费 - 订阅
	public static void consumeN(String messageName,ActiveMessageUtil active) throws JMSException, InterruptedException{
//		初始化 - 消费 - 推送
		active.initConsume();
		active.send(messageName, 500,SEND_NUMBER);
	}
	
	
	
	public static OrderEntity getOrder(int i,String SEND_URL){
		OrderEntity order = new OrderEntity();
		order.setOrderId(i);
		order.setOrderNo("20190521142206772228201905211422");
		order.setOrderName("商品"+i);
		order.setNeedAddress(SEND_URL);
		order.setOrderNumber("1000");
		order.setOrderPrice(52.32);
		return order;
	}
	

}
