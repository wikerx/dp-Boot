package com.scott.dp.modules.file;

import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Random;
import java.util.TimeZone;

/**
 * @CLASSNAME :FileController
 * @Description :文件上传
 * @Author :Mr.薛
 * @Data :2019/1/17 0017  17:49
 * @Version :V1.0
 * @Status : 编写
 **/
@RestController
@RequestMapping("/upFile")
public class FileController {

    /*
    * *
    * 单个文件的上传操作
    * 注：必须post请求
    */
    @RequestMapping(value = "/uploadOne", method = RequestMethod.POST)
    //处理文件上传
    public ModelAndView uploadImg(@RequestParam("file") MultipartFile file,
                                  HttpServletRequest request) {
        String contentType = file.getContentType();
        long t = System.currentTimeMillis();//获得当前时间的毫秒数
        Random rd = new Random(t);//作为种子数传入到Random的构造器中
        String fileName = String.valueOf(System.currentTimeMillis()) + file.getOriginalFilename();//替换文件名称
        System.out.println(fileName);
//        这里的路径在dev模式的时候是在target里面   正式环境 无target直接就是制定的路径
        String filePath = getImagePath();
        try {
            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
        } catch (Exception e) {
            // TODO: handle exception
        }
        //返回json  或页面
        ModelAndView mv = new ModelAndView("/file/success");
        return mv;
    }


 /*
  * *
  * 批量文件的上传操作
  * 注：必须post请求
  */
    //  图片批量上传操作
    @RequestMapping(value = "/upAll", method = RequestMethod.POST , consumes = "multipart/form-data")
    public void upAll(@RequestParam("file") MultipartFile files, HttpServletRequest request, HttpServletResponse response
    ) throws IllegalStateException, IOException {
        //创建一个通用的多部分解析器
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());
//        这里的路径在dev模式的时候是在target里面   正式环境 无target直接就是制定的路径
        String filePath = getFilePath();
        File localFile = new File(filePath);
        if (!localFile.exists()) {
            localFile.mkdir();
        }
        if (multipartResolver.isMultipart(request)) {
            //转换成多部分request
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest) request;
            //取得request中的所有文件名
            Iterator<String> iter = multiRequest.getFileNames();
            Long i = 0L;
            while (iter.hasNext()) {
                MultipartFile file = multiRequest.getFile(iter.next());
                if (file != null) {
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        System.out.println(myFileName);
                        //重命名上传后的文件名
                        String fileName = String.valueOf(new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date())+String.valueOf((int)(1+Math.random()*999999)) +"_"+ file.getOriginalFilename());
                        try {
                            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        System.out.println(filePath + fileName);
                    }
                }
            }
        }
    }

    //      获取图片的相对路径
    private String getImagePath() {
        String filePath = null;
        //        这里的路径在dev模式的时候是在target里面   正式环境 无target直接就是制定的路径
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            File upload = new File(path.getAbsolutePath(), "static/upload/file/");
            if (!upload.exists()) {
                upload.mkdirs();
            }
            System.out.println("upload path:" + upload.getAbsolutePath());
            filePath = upload.getAbsolutePath() + "/";
        } catch (Exception e) {
        }
        return filePath;
    }

    //      获取文件的相对路径
    private String getFilePath() {
        String filePath = null;
        //        这里的路径在dev模式的时候是在target里面   正式环境 无target直接就是制定的路径
        try {
            File path = new File(ResourceUtils.getURL("classpath:").getPath());
            if (!path.exists()) {
                path = new File("");
            }
            File upload = new File(path.getAbsolutePath(), "static/upload/file/");
            if (!upload.exists()) {
                upload.mkdirs();
            }
            System.out.println("upload path:" + upload.getAbsolutePath());
            filePath = upload.getAbsolutePath() + "/";
        } catch (Exception e) {
        }
        return filePath;
    }


}
