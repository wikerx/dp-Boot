package com.scott.dp.common.activemq;

import java.io.File;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.DeliveryMode;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Session;
import javax.jms.TextMessage;
import javax.jms.Topic;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQSession;
import org.apache.activemq.BlobMessage;

public class SendMessageByMq {
	 
	     public static void main(String[] args) {
	         String url = "";
	         // String url = "D:/mqfile/84.zip";
	         File file = new File(url);// 发送的文件
	         System.out.println("file=======" + file);
	         String sendType = "2";// 发送的类型 1发布一对一 ；2订阅一对多
	         String isNotFile = "false";// 是否有附件true有 false没有
	         String ip = ContentUtils.MQ_SEND_IP;// 发送ip
	         String modeName = ContentUtils.MQ_POINT_QUEUENAME;// 模式名称
	         String json = "[{\"name\":\"40013386.jpg\",\"url\":\"http://h.hiphotos.baidu.com/baik23e5.jpg\"}]";// 要发送的json数据
	         // 发送方法
	         String result = send(sendType, ip, modeName, json, file);
	 
	         if (result.equals("success")) {
	             try {
	                 System.out.println("开始接收1");
	                 // 接收方法
	                 ReceiveMessageByMq.receive(sendType, ip, isNotFile, modeName);
	             } catch (JMSException e) {
	                 e.printStackTrace();
	             }
	         }
	     }
	 
	     /**
	      * 
	      * 
	      * Title String Description
	      * 
	      * @author jacun
	      * @date 2017-4-11上午11:44:17
	      * @param sendType
	      *            发送类型 1发布一对一 ；2订阅一对多
	      *            发送ip和端口
	      * @param modeName
	      *            模式名称
	      * @param jsonData
	      *            要发送的json数据
	      * @param file
	      *            发送的文件
	      * @return
	     */
	     public static String send(String sendType, String ip, String modeName,
	             String jsonData, File file) {
	         String str = null;
	         System.out.println("开始发送1");
	         try {
	             // 获取 ConnectionFactory,ConnectionFactory：连接工厂，JMS用它创建连接
	             ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
	                     "tcp://"
	                             + ip
	                             + ":61616?jms.blobTransferPolicy.defaultUploadUrl=http://"
	                             + ip + ":8161/fileserver/");
	             // 创建 Connection,Connection：JMS客户端到JMS Provider的连接
	             Connection connection = connectionFactory.createConnection();
	              connection.start();
	             // 创建 Session,Session：一个发送或接收消息的线程
	             ActiveMQSession session = (ActiveMQSession) connection
	                     .createSession(false, Session.AUTO_ACKNOWLEDGE);
	             // 创建 Destination,Destination：消息的目的地;消息发送给谁.
	             // Destination destination = null;
	             // 判断是点对点1还是发布订阅2
	             if ("2".equals(sendType)) {
	                 System.out.println("一对多发布2");
	                 createTopic(session, modeName, jsonData, file);
	             } else {
	                 System.out.println("一对一发布2");
	                 // 点对点发布
	                 createQueue(session, modeName, jsonData, file);
	             }
	 
	             session.close();
	             // 不关闭 Connection, 程序则不退出
	             connection.close();
	             // 发送完成删除文件
	             // if (file != null) {
	             // if (file.exists()) {
	             // file.delete();
	             // }
	             // }
	            str = "success";
	             return str;
	         } catch (JMSException e) {
	             e.printStackTrace();
	             str = "fail";
	             return str;
	         }
	     }
	 
	     private static void createQueue(ActiveMQSession session, String modeName,
	             String jsonData, File file) {
	         try {
	             Destination destination = session.createQueue(modeName);
	             // 创建 Producer,MessageProducer：消息发送者
	             MessageProducer producer = session.createProducer(destination);
	            // 设置持久性的话，文件也可以先缓存下来，接收端离线再连接也可以收到文件
	             producer.setDeliveryMode(DeliveryMode.PERSISTENT);// 设置为持久性
	             if (file.length() > 0) {
	                 System.out.println("一对一上传文件3");
	                 // 构造 blobMessage，用来传输文件
	                 isFileTransfer(producer, session, file, jsonData);
	             } else {
	                 System.out.println("一对一无文件3");
	                 notFileTransfer(producer, session, jsonData);
	             }
	 
	         } catch (JMSException e) {
	             e.printStackTrace();
	         }
	 
	     }
	 
	     // 点对点无文件发送
	     private static void notFileTransfer(MessageProducer producer,
	             ActiveMQSession session, String jsonData) {
	         try {
	             TextMessage message = session.createTextMessage();
	             message.setStringProperty("sendType", "1");
	             message.setStringProperty("jsonData", jsonData);
	             message.setStringProperty("isNotFile", "false");
	             // 设置该消息的超时时间(有效期)
	             producer.setTimeToLive(60000);
	             // 发送
	             producer.send(message);
	             producer.close();
	             System.out.println("发送成功无文件4");
	         } catch (JMSException e) {
	             e.printStackTrace();
	         }
	 
	     }
	 
	     // 点对点有文件发送
	     private static void isFileTransfer(MessageProducer producer,
	             ActiveMQSession session, File file, String jsonData) {
	         try {
	             BlobMessage blobMessage = session.createBlobMessage(file);
	             blobMessage.setStringProperty("sendType", "1");
	             blobMessage.setStringProperty("jsonData", jsonData);
	             blobMessage.setStringProperty("isNotFile", "true");
	             // 设置该消息的超时时间(有效期)
	             producer.setTimeToLive(60000);
	             // 发送
	             producer.send(blobMessage);
	             producer.close();
	             System.out.println("发送成功有文件4");
	         } catch (JMSException e) {
	             e.printStackTrace();
	         }
	 
	     }
	 
	     private static void createTopic(ActiveMQSession session, String modeName,
	             String jsonData, File file) {
	         try {
	             Topic topic = session.createTopic(modeName);
	             MessageProducer producer = session.createProducer(topic);
	             producer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);
	             if (file.length() > 0) {
	                 System.out.println("一对多上传文件3");
	                 // 构造 blobMessage，用来传输文件
	                 isFileTransfer(producer, session, file, jsonData);
	             } else {
	                 System.out.println("一对多无文件3");
	                 notFileTransfer(producer, session, jsonData);
	             }
	         } catch (JMSException e) {
	             e.printStackTrace();
	         }
	     }
 }