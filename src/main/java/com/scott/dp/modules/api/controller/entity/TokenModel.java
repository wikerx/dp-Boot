package com.scott.dp.modules.api.controller.entity;
/**
 * @CLASSNAME :TokenModel
 * @Description :Token 实体
 * @Author :Mr.薛
 * @Data :2019/1/17 0017  10:05
 * @Version :V1.0
 * @Status : 编写
 **/
public class TokenModel {
    private String msg;
    private String code;
    private String token;

//    get    set

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
