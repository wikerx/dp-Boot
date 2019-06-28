package com.scott.dp.common.craw;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName :CrawImg
 * @Description :爬取网站js
 * @Author :Mr.薛
 * @Data :2019/6/27  15:09
 * @Version :V1.0
 * @Status : 编写
 **/
public class CrawJs {
    private static Log log = LogFactory.getLog(CrawJs.class);
    // 获取img标签正则
    private static final String JSURL_REG = "<script.*src=(.*?)[^>]*?>";
    // 获取src路径的正则
    private static final String JSSRC_REG = "[a-zA-z]+://[^\\s]*";

    private static List<String> downList = new ArrayList<String>();

    public static List<String> carwJs(String url,String savePath) {
        try {
            //获得html文本内容
            String HTML = getHtml(url);
            List<String> imgUrl = getjsUrl(HTML);
            List<String> jsUrl = new ArrayList<String>();
            for (String js:imgUrl) {
                js = js.split("src=\"")[1].split(".js")[0]+".js";
                jsUrl.add(js);
            }
            //下载
            download(jsUrl,savePath);

        }catch (Exception e){
            e.printStackTrace();
        }
        return downList;
    }

    //获取HTML内容
    private static String getHtml(String url)throws Exception{
        URL url1=new URL(url);
        URLConnection connection=url1.openConnection();
        InputStream in=connection.getInputStream();
        InputStreamReader isr=new InputStreamReader(in);
        BufferedReader br=new BufferedReader(isr);

        String line;
        StringBuffer sb=new StringBuffer();
        while((line=br.readLine())!=null){
            sb.append(line,0,line.length());
            sb.append('\n');
        }
        br.close();
        isr.close();
        in.close();
        return sb.toString();
    }

    //获取jsUrl地址
    private static List<String> getjsUrl(String html){
        Matcher matcher=Pattern.compile(JSURL_REG).matcher(html);
        List<String>listimgurl=new ArrayList<String>();
        while (matcher.find()){
            listimgurl.add(matcher.group());
        }
        return listimgurl;
    }


    //下载图片
    private static void download(List<String> listImgSrc,String savePth) {
            for (String url : listImgSrc) {
//                下载js
                try {
                    downLoadFromUrl(url, savePth + "js/");
                }catch (Exception e){//一个失败不跳出循环
                    log.error("【"+e+"】 下载失败");
                    downList.add("【"+e+"】 下载失败");
                }
            }
    }


    /**
     * 从网络Url中下载文件
     * @param urlStr
     * @param savePath
     * @throws IOException
     */
    private static void  downLoadFromUrl(String urlStr,String savePath) throws IOException{
        String fileName = urlStr.substring(urlStr.lastIndexOf("/") + 1, urlStr.length());
        URL url = new URL(urlStr);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        //设置超时间为3秒
        conn.setConnectTimeout(3*1000);
        //防止屏蔽程序抓取而返回403错误
        conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");
        //得到输入流
        InputStream inputStream = conn.getInputStream();
        //获取自己数组
        byte[] getData = readInputStream(inputStream);

        //文件保存位置
        File saveDir = new File(savePath);
        if(!saveDir.exists()){
            saveDir.mkdir();
        }
        File file = new File(saveDir+File.separator+fileName);
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(getData);
        if(fos!=null){
            fos.close();
        }
        if(inputStream!=null){
            inputStream.close();
        }
        log.info("【"+urlStr+"】 下载成功");
        downList.add("【"+urlStr+"】 下载成功");
    }


    /**
     * 从输入流中获取字节数组
     * @param inputStream
     * @return
     * @throws IOException
     */
    private static  byte[] readInputStream(InputStream inputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int len = 0;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        while((len = inputStream.read(buffer)) != -1) {
            bos.write(buffer, 0, len);
        }
        bos.close();
        return bos.toByteArray();
    }


}
