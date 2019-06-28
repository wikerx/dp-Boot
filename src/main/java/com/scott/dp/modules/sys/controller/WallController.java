package com.scott.dp.modules.sys.controller;

import com.scott.dp.common.annotation.SysLog;
import com.scott.dp.common.craw.*;
import com.scott.dp.common.utils.Ognl;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName :WallController
 * @Description :网络攻防核心调用
 * @Author :Mr.薛
 * @Data :2019/6/28  10:32
 * @Version :V1.0
 * @Status : 编写
 **/
@RestController
@RequestMapping("/wall")
public class WallController {
    private static Log log = LogFactory.getLog(WallController.class);


    /**
     * 调用爬虫类方法
     *
     */
    @SysLog("调用爬虫类方法爬取数据")
    @RequestMapping("/internet/list")
    public List<String> listCraw(String ck,String crawlPath,String savelPath){
        List<String> downList = new ArrayList<String>();
        if(Ognl.isEmpty(ck)){
            downList.add("<hr/><br/><strong style='color:red;font-size:18px;'>未选中拉取项，请重新拉取......</strong><br/><hr/>");
        }else {
            FileUtilsForCraw.createSubordinate(savelPath);
            if(ck.indexOf("1")>-1){//站点-全部
                downList.addAll(CrawHtml.crawHtml(crawlPath, savelPath));
                downList.addAll(CrawImg.carwImg(crawlPath, savelPath));
                downList.addAll(CrawJs.carwJs(crawlPath, savelPath));
                downList.addAll(CrawCss.carwCss(crawlPath, savelPath));
                downList.addAll(CrawLink.carwLink(crawlPath, savelPath));
                downList.add("<hr/><br/><strong style='color:green;font-size:18px;'>站点拉取完毕</strong><br/>");
            }else{
                if(ck.indexOf("2")>-1){
                    downList.addAll(CrawHtml.crawHtml(crawlPath, savelPath));
                    downList.add("<hr/><br/><strong style='color:green;font-size:18px;'>网页拉取完毕</strong><br/>");
                }
                if(ck.indexOf("3")>-1){
                    downList.addAll(CrawImg.carwImg(crawlPath, savelPath));
                    downList.add("<hr/><br/><strong style='color:green;font-size:18px;'图片拉取完毕</strong><br/>");
                }
                if(ck.indexOf("4")>-1){
                    downList.addAll(CrawLink.carwLink(crawlPath, savelPath));
                    downList.add("<hr/><br/><strong style='color:green;font-size:18px;'>链接地址拉取完毕</strong><br/>");
                }
                if(ck.indexOf("5")>-1){
                    downList.addAll(CrawJs.carwJs(crawlPath, savelPath));
                    downList.add("<hr/><br/><strong style='color:green;font-size:18px;'>JS地址拉取完毕</strong><br/>");
                }
                if(ck.indexOf("6")>-1){
                    downList.addAll(CrawLink.carwLink(crawlPath, savelPath));
                    downList.add("<hr/><br/><strong style='color:green;font-size:18px;'>CSS拉取完毕</strong><br/><hr/>");
                }
            }
        }
        return downList;
    }

    /**
     * 换机
     */
    @SysLog("换机")
    @RequestMapping("/replace/list")
    public boolean listReplace(String crawlPath,String savelPath,String crawlAccount,String saveAccount,String crawlPwd,String savePwd){//源码位置   换机位置
        return FileUtilsForCraw.btocFile(crawlPath,savelPath,crawlAccount,saveAccount,crawlPwd,savePwd);
    }

    /**
     * 数据备份与恢复
     */
    @SysLog("数据备份与恢复")
    @RequestMapping("/btoc/list")
    public boolean listBtoc(String crawlPath,String savelPath,int type,String crawlAccount,String saveAccount,String crawlPwd,String savePwd){//备份位置   恢复位置  类型
        if(type == 2){
//            数据恢复 数据库 + 源码
        }else{
//            数据备份 数据库 + 源码
        }
        return true;
    }



}
