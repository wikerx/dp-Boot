package com.scott.dp.common.craw;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @ClassName :CrawLink
 * @Description :爬取网站的所有连接地址
 * @Author :Mr.薛
 * @Data :2019/6/27  17:44
 * @Version :V1.0
 * @Status : 编写
 **/
public class CrawLink {
    private static Log log = LogFactory.getLog(CrawLink.class);
    private static List<String> downList = new ArrayList<String>();
    /**
     * 获得urlStr对应的网页的源码内容
     * @param urlStr
     * @return
     */
    public static String getURLContent(String urlStr, String charset) {
        StringBuilder sb = new StringBuilder();
        try {
            URL url = new URL(urlStr);
            //url.openStream()打开一个输入流
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), Charset.forName(charset)));
            String temp = "";
            while((temp = reader.readLine()) != null) {
                sb.append(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static List<String> getMatherSubstrs(String destStr) {

        List<String> result = new ArrayList<String>();
//        取href
//        Pattern pt=Pattern.compile("<a.*?/a>");
        Pattern pt=Pattern.compile("<.*?>");
        Matcher mt=pt.matcher(destStr);
        while(mt.find()){
            String s3 = "href=\"(.*?)\"";
            Pattern pt3=Pattern.compile(s3);
            Matcher mt3=pt3.matcher(mt.group());
            while(mt3.find())
            {
                String u = mt3.group().replaceAll("href=|>","");
                u = u.substring(1,u.length()-1).trim();
                if(!(u.indexOf("#") > -1) && !(u.indexOf("javascript") > -1)){
                    result.add(u);
                }
            }
        }
//        取src
        String img = "";
        Pattern p_image;
        Matcher m_image;
//        String regEx_img = "<img.*src\\s*=\\s*(.*?)[^>]*?>";
        String regEx_img = "<*src\\s*=\\s*(.*?)[^>]*?>";
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(destStr);
        while (m_image.find()) {
            // 得到<img />数据
            img = m_image.group();
            // 匹配<img>中的src数据
            Matcher m = Pattern.compile("src\\s*=\\s*\"?(.*?)(\"|>|\\s+)").matcher(img);
            while (m.find()) {
                result.add(m.group(1));
            }
        }

        return result;
    }

    public static List<String> carwLink(String url,String savePath){
        try {
            String destStr = getURLContent(url, "utf-8");
            List<String> result = getMatherSubstrs(destStr);
            BufferedWriter bw = new BufferedWriter(new FileWriter(savePath + "link/link.txt"));
            for (String temp : result) {
                downList = result;
                bw.write(temp);
                bw.newLine();
                bw.flush();
            }
            bw.close();
        }catch (Exception e){
            log.error(e);
            e.printStackTrace();
        }
        return downList;
    }


}
