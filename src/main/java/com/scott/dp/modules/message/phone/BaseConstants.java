package com.scott.dp.modules.message.phone;
public class BaseConstants {

    /**
     * 手机验证码保存的邮箱时间 秒
     */
    public static final long SECONDS = 600;
    /**
     * 日志清理时间参数
     */
    public static final int LOG_DAYS = 90;
    /***/
    public static final int VALIDITY_DAYS = 90;
    /**
     * 日期格式 **
     */
    public final static String DATE_PATTERN = "yyyy-MM-dd";
    /**
     * 日期格式 **
     */
    public final static String DATETIME_PATTERN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 每页的记录数
     */
    public final static int PAGE_SIZE = 20;
    /**
     * 图片URL路径
     */
    public final static String PICTURE_URL = "http://114.55.254.168/app/picture/pictureAction/findImageStreams.action?path=";
    /**
     * 激活邮箱地址
     */
    public final static String ACTIVATE_URL = "http://114.55.254.168/";
    /**
     * 短信参数
     */
    public final static String CORP_ACCOUNT = "kcjk";
    public final static String USER_ACCOUNT = "admin";
    public final static String SMS_PWD = "191515";
    /**
     * 邮箱参数
     */
    public final static String EMAIL_HOST = "smtp.qq.com";
    public final static String EMAIL_USER = "qcy-app@qq.com";
    public final static String EMAIL_PASSWD = "gaotou123";

    /**
     * 图片保存的位置
     * APP default.png
     */
    public static String findPictureDir() {
        String dir;
        String os = System.getProperty("os.name");
        if (os.startsWith("win") || os.startsWith("Windows")) {
            dir = "D:/upload/picture/";
        } else {
            dir = "/upload/picture/";
        }
        return dir;
    }

}