package com.scott.dp.modules.sys.controller;

import com.scott.dp.common.annotation.SysLog;
import com.scott.dp.common.constant.MsgConstant;
import com.scott.dp.common.entity.Page;
import com.scott.dp.common.entity.R;
import com.scott.dp.common.utils.FileUtils;
import com.scott.dp.common.utils.UploadUtils;
import com.scott.dp.modules.file.FileUtil;
import com.scott.dp.modules.sys.entity.ISysFileEntity;
import com.scott.dp.modules.sys.entity.SysFileEntity;
import com.scott.dp.modules.sys.service.ISysFileService;
import com.scott.dp.modules.sys.service.SysFileService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @CLASSNAME :SysFileController
 * @Description :DOTO
 * @Author :Mr.薛
 * @Data :2019/5/21  16:26
 * @Version :V1.0
 * @Status : 编写
 **/
@RestController
@RequestMapping("/sys/file")
public class SysFileController extends AbstractController{
    static Log log = LogFactory.getLog(SysFileController.class);
    @Autowired
    ISysFileService fileService;
    @Autowired
    private SysFileService sysFileService;
    /*
     * *
     * 批量文件的上传操作
     * 注：必须post请求
     */
    //  图片批量上传操作
    @RequestMapping(value = "/upload", method = RequestMethod.POST , consumes = "multipart/form-data")
    public String upAll(@RequestParam("file") MultipartFile files, HttpServletRequest request, HttpServletResponse response
    ) throws IllegalStateException, IOException {
        int count = 0;
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
                    ISysFileEntity fileEntity = new ISysFileEntity();
                    //取得当前上传文件的文件名称
                    String myFileName = file.getOriginalFilename();
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在
                    if (myFileName.trim() != "") {
                        log.info(myFileName);
                        //重命名上传后的文件名
                        String fileName = String.valueOf(new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss_SSS").format(new Date())+String.valueOf((int)(1+Math.random()*999999)) +"_"+ file.getOriginalFilename());
                        try {
                            FileUtil.uploadFile(file.getBytes(), filePath, fileName);
                        } catch (Exception e) {
                            // TODO: handle exception
                        }
                        fileEntity.setFile_name(myFileName.split("[.]")[0]);//文件名称
                        fileEntity.setFile_add(filePath + fileName);//文件位置
                        fileEntity.setFile_up_id(getUserId());//上传者
                        fileEntity.setRemark("文档录入");//上传说明
                        fileEntity.setUp_time(new Date());//录入时间
                        fileEntity.setFile_type(1);
                        fileEntity.setFile_new_name(fileName.split("[.]")[0]);
                        count = fileService.insertFileMessage(fileEntity);//录入信息
                        log.info(filePath + fileName);
                    }
                }
            }
        }
        if(count > 0){
            return MsgConstant.MSG_SUCCEED;
        }else{
            return  MsgConstant.MSG_ERROR;
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
            log.info("upload path:" + upload.getAbsolutePath());
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
            log.info("upload path:" + upload.getAbsolutePath());
            filePath = upload.getAbsolutePath() + "/";
        } catch (Exception e) {
        }
        return filePath;
    }


    /**
     * 列表
     * @param params
     * @return
     */
    @RequestMapping("/list")
    public Page<SysFileEntity> list(@RequestBody Map<String, Object> params) {
        return sysFileService.listSysFile(params);
    }

    /**
     * 新增
     * @param sysFile
     * @return
     */
    @SysLog("新增文件")
    @RequestMapping("/save")
    public R save(@RequestBody SysFileEntity sysFile) {
        return sysFileService.saveSysFile(sysFile);
    }

    /**
     * 根据id查询详情
     * @param id
     * @return
     */
    @RequestMapping("/info")
    public R getById(@RequestBody Long id) {
        return sysFileService.getSysFileById(id);
    }

    /**
     * 修改
     * @param sysFile
     * @return
     */
    @SysLog("修改文件")
    @RequestMapping("/update")
    public R update(@RequestBody SysFileEntity sysFile) {
        return sysFileService.updateSysFile(sysFile);
    }

    /**
     * 删除
     * @param id
     * @return
     */
    @SysLog("删除文件")
    @RequestMapping("/remove")
    public R batchRemove(@RequestBody Long[] id) {
        List<SysFileEntity> list = sysFileService.getFileIds(id);
        for (int i = 0;i < list.size();i++){
            SysFileEntity fileEntity = list.get(i);
            FileUtils.delete(fileEntity.getFileAdd());//删除文件位置源文件
        }
        return sysFileService.batchRemove(id);
    }











}
