package com.scott.dp.common.craw;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;

import java.io.*;
import java.net.SocketException;

/**
 * @ClassName :FileUtilsForCraw
 * @Description :爬虫对于文件的处理
 * @Author :Mr.薛
 * @Data :2019/6/27  14:45
 * @Version :V1.0
 * @Status : 编写
 **/
public class FileUtilsForCraw {
    private static Log log= LogFactory.getLog(FileUtilsForCraw.class);
    /**
     * 在目录下创建 html、site、images、link、js、css文件夹
     * 指定目录下创建子目录
     * */
    public static void createSubordinate(String filePath){
        File file = new File(filePath);
//        文件夹不存在，生成
        if(!file.exists()){
            file.mkdirs();
        }
//        创建下级子目录
        if(!new File(filePath+"/html").exists()) {
            createFile(filePath + "/html");
        }
        if(!new File(filePath+"/images").exists()) {
            createFile(filePath + "/images");
        }
        if(!new File(filePath+"/link").exists()) {
            createFile(filePath + "/link");
        }
        if(!new File(filePath+"/js").exists()) {
            createFile(filePath + "/js");
        }
        if(!new File(filePath+"/css").exists()) {
            createFile(filePath + "/css");
        }
    }


    /**
     * 创建文件or文件夹
     * */
    public static boolean createFile(String filePath){
        File file = new File(filePath);
        return file.mkdirs();
    }

    /**
     * 删除文件or文件夹
     * */
    public static boolean deleteFile(String filePath){
        File file = new File(filePath);
        return file.delete();
    }

    /**
     * 当前文件或文件夹是否为空
     * */
    public static boolean isNullFile(String filePath) {
        File file = new File(filePath);
        return (file.exists() && file.length() == 0);
    }

    //复制方法
    private static boolean copy(String src, String des){
        boolean flag = true;
        //初始化文件复制
        File file1=new File(src);
        //把文件里面内容放进数组
        File[] fs=file1.listFiles();
        //初始化文件粘贴
        File file2=new File(des);
        //判断是否有这个文件有不管，没有创建
        if(!file2.exists()){
            file2.mkdirs();
        }
        //遍历文件及文件夹
        for (File f : fs) {
            if(f.isFile()){
                //文件
                flag = fileCopy(f.getPath(),des+"\\"+f.getName()); //调用文件拷贝的方法
            }else if(f.isDirectory()){
                //文件夹
                flag = copy(f.getPath(),des+"\\"+f.getName());//继续调用复制方法      递归的地方,自己调用自己的方法,就可以复制文件夹的文件夹了
            }
        }
        return flag;
    }

    /**
     * 文件复制的具体方法
     */
    private static boolean fileCopy(String src, String des){
        boolean flag = true;
        try {
            //io流固定格式
            BufferedInputStream bis = new BufferedInputStream(new FileInputStream(src));
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(des));
            int i = -1;//记录获取长度
            byte[] bt = new byte[2014];//缓冲区
            while ((i = bis.read(bt)) != -1) {
                bos.write(bt, 0, i);
            }
            //关闭流
            bis.close();
            bos.close();
        }catch(Exception e){
            e.printStackTrace();
            flag = false;
        }
        return flag;
    }

    /**
     * 获取FTPClient对象
     * @param ftpHost FTP主机服务器
     * @param ftpPassword FTP 登录密码
     * @param ftpUserName FTP登录用户名
     * @param ftpPort FTP端口 默认为21
     * @return
     */
    public static FTPClient getFTPClient(String ftpHost, String ftpPassword,
                                         String ftpUserName, int ftpPort) {
        FTPClient ftpClient = null;
        try {
            ftpClient = new FTPClient();
            ftpClient.connect(ftpHost, ftpPort);// 连接FTP服务器
            ftpClient.login(ftpUserName, ftpPassword);// 登陆FTP服务器
            if (!FTPReply.isPositiveCompletion(ftpClient.getReplyCode())) {
                log.info("未连接到FTP，用户名或密码错误。");
                ftpClient.disconnect();
            } else {
                log.info("FTP连接成功。");
            }
        } catch (SocketException e) {
            e.printStackTrace();
            log.info("FTP的IP地址可能错误，请正确配置。");
        } catch (IOException e) {
            e.printStackTrace();
            log.info("FTP的端口错误,请正确配置。");
        }
        return ftpClient;
    }

    /**
     * 文件复制
     * */
    public static boolean btocFile(String from,String to,String crawlAccount,String saveAccount,String crawlPwd,String savePwd){
        if(from.indexOf(":")>-1 || to.indexOf(":")>-1){
//            服务器之间复制
            try {
                String start_ip = from.split(":")[0];
                String start_port = from.split(":")[1].split("/")[0];
                String start_ext = from.split(":")[1].split("/")[1];
                String end_ip = to.split(":")[0];
                String end_port = to.split(":")[1].split("/")[0];
                String end_ext = to.split(":")[1].split("/")[1];
//                ReadFTPFile.readConfigFileForFTP(crawlAccount,crawlPwd,start_ext,start_ip,Integer.parseInt(start_port),start_ext.split("//")[start_ext.split("//").length]);
//                WriteFTPFile.upload(end_ext,saveAccount,savePwd,end_ip,Integer.parseInt(end_port),end_ext.split("//")[end_ext.split("//").length],crawlAccount);
            }catch (Exception e){
                e.printStackTrace();
                return false;
            }
            return true;
        }else {
            return copy(from, to);
        }
    }



}
