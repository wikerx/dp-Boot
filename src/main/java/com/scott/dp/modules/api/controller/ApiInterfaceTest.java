package com.scott.dp.modules.api.controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.scott.dp.modules.api.controller.entity.TokenModel;

import java.util.HashMap;
import java.util.Map;

/**
 * @CLASSNAME :ApiInterfaceTest
 * @Description :http接口测试
 * @Author :Mr.薛
 * @Data :2019/1/17 0017  9:37
 * @Version :V1.0
 * @Status : 编写
 **/
public class ApiInterfaceTest {
private static final String REQ_URL = "http://192.168.0.55:8080/scott/";
    public static void main(String[] args) throws  Exception{
        String username = "scott";
        String password = "123456";
        Map<String,String> map = new HashMap<String,String>();
        map.put("username",username);
        map.put("password",password);
        String token = getPostToken(map);
        System.out.println("token======:"+token);
//        System.out.println("anonymous:"+anonymous());
        System.out.println("toketGet:"+toketGet(token));
    }

    /*获取token授权*/
    public static String getPostToken(Map<String,String> map){
        String token = "";
        TokenModel tokenModel = null;
        try {
//            token = HttpClientRequest.httpPost(REQ_URL + "rest/auth", "password=" + password + "&username=" + username);
            token = new HttpClientRequest().postMap(REQ_URL + "rest/auth",map);
            ObjectMapper mapper= new ObjectMapper();
            tokenModel = mapper.readValue(token,TokenModel.class);
        }catch (Exception e){
            e.printStackTrace();
        }
        System.out.println(tokenModel.getToken());
        return token;
    }

    /*匿名接口访问 get */
    public static String anonymous() throws Exception{
        String result = "";
        try{
            result = HttpClientRequest.httpGet("http://192.168.0.55:8080/scott/rest/testAnon");
        }catch (Exception e){
            System.out.println("系统服务中断，请检查系统是否启动，网络是否正常...");
            e.printStackTrace();
        }
        return result;
    }

    /*token访问链接*/
    public static String toketGet(String token) throws Exception{
        String result = "";
        try{
            result = HttpClientRequest.httpTokenGet("http://192.168.0.55:8080/scott/rest/testAuth",token);
        }catch (Exception e){
            System.out.println("系统服务中断，请检查系统是否启动，网络是否正常...");
            e.printStackTrace();
        }
        if("".equals(result)){
            System.out.println("token授权失败");
        }
        return result;
    }


}
