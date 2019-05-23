package com.scott.dp.common.activemq;

import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.TextMessage;
import org.apache.activemq.ActiveMQConnectionFactory;

public class ReceiveMessageByMq {

    public static void main(String[] args) {
        
        String receiveType = "1";// 接收的类型 1发布一对一 ；2订阅一对多
        String isNotFile = "true";// 是否有附件
        String ip = ContentUtils.MQ_RECEIVE_IP;// 接收ip
        String modeName = ContentUtils.MQ_POINT_QUEUENAME;// 模式名称
        try {
            receive(receiveType, ip, isNotFile, modeName);
        } catch (JMSException e) {
            e.printStackTrace();
        }
    }

    /**
     * 
     * 
     * Title void Description
     * 
     * @author jacun
     * @param modeName
     * @param ip
     * @param receiveType
     * @date 2017-4-11上午10:43:10
     * @throws JMSException
     */
    public static void receive(String receiveType, String ip, String isNotFile,
            String modeName) throws JMSException {
        System.out.println("开始接收2");
        // 获取 ConnectionFactory
        ConnectionFactory connectionFactory = new ActiveMQConnectionFactory(
                "tcp://" + ip + ":61616");
        // 创建 Connection
        Connection connection = connectionFactory.createConnection();
        connection.start();
        // 创建 Session
        Session session = connection.createSession(false,
                Session.AUTO_ACKNOWLEDGE);
        // 创建 Destinatione
        // 判断是一对一还是一对多
        if ("2".equals(receiveType)) {
            // 一对多
            System.out.println("一对多接收数据3");
            receiveTopic(session, isNotFile, modeName);
        } else {
            // 一对一
            System.out.println("一对一接收数据3");
            receiveQueue(session, isNotFile, modeName);
        }

    }

    private static void receiveTopic(Session session, String isNotFile,
            String modeName) {
        try {
            final String isFile = isNotFile;
            Destination destination = session.createTopic(modeName);
            // 创建 Consumer
            MessageConsumer consumer = session.createConsumer(destination);
            // 注册消息监听器，当消息到达时被触发并处理消息
            consumer.setMessageListener(new MessageListener() {
                // 监听器中处理消息
                public void onMessage(Message message) {
                    if ("true".equals(isFile)) {
                        System.out.println("有文件接收数据4");
                       // ReceiveMessageByMq.receiveFile(message);
                    } else {
                        System.out.println("无文件接收数据4");
                        ReceiveMessageByMq.receiveData(message);

                    }

                }

            });
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    private static void receiveQueue(Session session, String isNotFile,
            String modeName) {
        try {
            final String isFile = isNotFile;
            Destination destination = session.createQueue(modeName);
            // 创建 Consumer
            MessageConsumer consumer = session.createConsumer(destination);
            // 注册消息监听器，当消息到达时被触发并处理消息
            consumer.setMessageListener(new MessageListener() {
                // 监听器中处理消息

                public void onMessage(Message message) {
                    if ("true".equals(isFile)) {
                        System.out.println("有文件接收数据4");
                      //  ReceiveMessageByMq.receiveFile(message);
                    } else {
                        System.out.println("无文件接收数据4");
                        ReceiveMessageByMq.receiveData(message);

                    }

                }

            });
        } catch (JMSException e) {
            e.printStackTrace();
        }

    }

    protected static void receiveData(Message message) {
        String sendType = null;
        String jsonData = null;
        try {
            TextMessage msg = (TextMessage) message;
            sendType = msg.getStringProperty("sendType");
            jsonData = msg.getStringProperty("jsonData");
        } catch (JMSException e) {
            e.printStackTrace();
        }
        System.out.println("无文件接收成功5");
        System.out.println(sendType);
        System.out.println(jsonData);
    }

/*    private static void receiveFile(Message message) {
        String sendType = null;
        String jsonData = null;
        if (message instanceof BlobMessage) {
            BlobMessage blobMessage = (BlobMessage) message;
            try {
                String path = CreateZipFile.createZip("test");
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("请指定文件保存位置");
                fileChooser.setSelectedFile(new File(path));
                File file = fileChooser.getSelectedFile();
                OutputStream os = new FileOutputStream(file);
                InputStream inputStream = blobMessage.getInputStream();
                // 写文件，你也可以使用其他方式
                byte[] buff = new byte[256];
                int len = 0;
                while ((len = inputStream.read(buff)) > 0) {
                    os.write(buff, 0, len);
                }
                os.close();
                System.out.println("有文件接收成功5");
                sendType = blobMessage.getStringProperty("sendType");
                jsonData = blobMessage.getStringProperty("jsonData");
                System.out.println(sendType);
                System.out.println(jsonData);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

    }*/
}
