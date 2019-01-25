package com.scott.dp.modules.message.phone;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author make it
 * @version SVN #V1# #2016-8-27#
 */
public class SmsUtil {


    public static void main(String[] args) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sms = new SmsUtil().sendSms("18772101110","scott于"+df.format(new Date())+"将您的密码已重置为123456，请保护好您的新密码，切勿告知他人！【XXX公司】");
//        System.out.println(sms+new Date());
        System.out.println(new SmsUtil().balance());
    }


    /*短信发送 - 密码重置*/
    public static String ResetPassword(String phone,String name,String pwd){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        String sms = new SmsUtil().sendSms(phone,name+"于"+df.format(new Date())+"将您的密码修改为"+pwd+"，请保护好您的新密码，切勿告知他人！【XXX公司】");
        return new SmsUtil().balance();
    }


    /**
     * 发送短信
     */
    public String sendSms(String mobile, String content) {
        String url = "http://oa-sms.com/sendSms.action";
        String corpAccount = BaseConstants.CORP_ACCOUNT;
        String userAccount = BaseConstants.USER_ACCOUNT;
        String pwd = BaseConstants.SMS_PWD;
        try {
            content = URLEncoder(content, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        pwd = SmsMD5.MD5(pwd);
        return SendToHttp(url, "corpAccount=" + corpAccount + "&userAccount=" + userAccount + "&pwd=" + pwd + "&mobile=" + mobile + "&content=" + content);
    }

    /**
     * 查看余额
     */
    public String balance() {
        String url = "http://oa-sms.com/balance.action";
        String corpAccount = BaseConstants.CORP_ACCOUNT;
        String userAccount = BaseConstants.USER_ACCOUNT;
        String pwd = BaseConstants.SMS_PWD;
        pwd = SmsMD5.MD5(pwd);
        return SendToHttp(url, "corpAccount=" + corpAccount + "&userAccount=" + userAccount + "&pwd=" + pwd);
    }

    /**
     * @param url       请求 URL
     * @param parameter 参数列表
     * @return sting
     */
    private static String SendToHttp(String url, String parameter) {
        StringBuilder sb = new StringBuilder();
        HttpURLConnection connection;
        String connect_err = "error";
        try {
            connection = (HttpURLConnection) (new URL(url)).openConnection();
        } catch (Exception ex) {
            return connect_err;
        }
        if (connection != null) {
            try {
                connection.setRequestMethod("POST"); // 注意POST为大写的方式才可以，在实际中测试过遇到该问题
                connection.setRequestProperty("content-type", "application/x-www-form-urlencoded");
                System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 设置超时时间
                System.setProperty("sun.net.client.defaultReadTimeout", "30000");
                connection.setDoOutput(true);
                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(parameter);
                writer.flush();
                writer.close();
                BufferedReader read;
                read = new BufferedReader(new InputStreamReader(
                        connection.getInputStream()));
                String str;
                while ((str = read.readLine()) != null) {
                    sb.append(str.trim());
                }
                read.close();// 关闭读取流
            } catch (Exception ex) {
                ex.printStackTrace();
                return "timeout";
            } finally {
                connection.disconnect();
            }
            return sb.toString();
        } else {
            return connect_err;
        }
    }

    /**
     * @param str  内容
     * @param type 编码格式 utf-8 ISO8859_1，gbk,gb2312
     * @return string
     * @throws UnsupportedEncodingException
     */
    public static String URLEncoder(String str, String type) throws UnsupportedEncodingException {
        if (str == null) {
            return null;
        }
        return java.net.URLEncoder.encode(str, type);
    }

    public Long findBalance() {
        Long balance = 0L;
        try {
            String res = balance();
            if (StringUtils.isNotBlank(res)) {
                String[] mes = res.split("#");
                if (mes.length >= 2) {
                    if (NumberUtils.isNumber(mes[1])) {
                        balance = NumberUtils.toLong(mes[1]);
                    }
                }
            }
        } catch (Exception e) {
            balance = -1L;
            e.printStackTrace();
        }
        return balance;
    }

}