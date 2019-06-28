package com.scott.dp.common.craw;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :CrawMessage
 * @Description :爬取数据
 * @Author :Mr.薛
 * @Data :2019/6/27  15:11
 * @Version :V1.0
 * @Status : 编写
 **/
public class CrawMessage {
    private static List<String> downList = new ArrayList<String>();

    public static void main(String[] args) {
    String savepath = "E:/craw/";
    String url = "";
//            文件夹结构搭建
        FileUtilsForCraw.createSubordinate(savepath);
        downList.addAll(CrawHtml.crawHtml(url,savepath));
        downList.addAll(CrawImg.carwImg("http://tu.duowan.com/m/bxgif",savepath));
        downList.addAll(CrawJs.carwJs("http://tu.duowan.com/m/bxgif",savepath));
        downList.addAll(CrawCss.carwCss("http://tu.duowan.com/m/bxgif",savepath));
        downList.addAll(CrawLink.carwLink("http://tu.duowan.com/m/bxgif",savepath));
        for (String str:downList) {
            System.out.println("++++++++++===:"+str);
        }
    }

}
