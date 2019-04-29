package com.scott.dp.modules.file;

import com.scott.dp.common.utils.Ognl;
import com.scott.dp.modules.sys.entity.SysAreaEntity;
import com.scott.dp.modules.sys.service.SysAreaService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
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
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

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
    Log log = LogFactory.getLog(FileController.class);
    @Autowired
    private SysAreaService sysAreaService;
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

    @RequestMapping(value = "/downFile",method = RequestMethod.GET)
    public void DownFile(HttpServletRequest request, HttpServletResponse response) throws  Exception{
//        1.查询获取所有数据结果集
        List<SysAreaEntity> list = sysAreaService.getAllAreas();
//        2.将数据保存至服务器
        String savePath = listExcel(list,getFilePath());
        log.info("数据文件保存位置："+savePath);
//        3.下载数据
//        4.删除保存的数据，减少服务器内存消耗
        /*略过这些步骤，直接下载*/
        // 读到流中
        InputStream inStream = new FileInputStream(savePath);// 文件的存放路径
        // 设置输出的格式
        response.reset();
        response.setContentType("bin");
        String fileName = savePath.split("[//]")[savePath.split("[//]").length-1];
        response.addHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\"");
        // 循环取出流中的数据
        byte[] b = new byte[100];
        int len;
        try {
            while ((len = inStream.read(b)) > 0)
                response.getOutputStream().write(b, 0, len);
            inStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
















    /**
     *
     * @param list 从数据库中查询需要导入excel文件的信息列表
     * @return 返回生成的excel文件的路径
     * @throws Exception
     */
    public static String listExcel(List<SysAreaEntity> list,String folderPath) throws Exception {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Workbook wb = new XSSFWorkbook();
        //标题行抽出字段
        String[] title = {"区域id","行政区划代码", "父级id", "地区名称", "层级", "排序号,1:省级,2:地市,3:区县", "显示,1:显示,0:隐藏", "备注","创建时间","修改时间"};
        //设置sheet名称，并创建新的sheet对象
        String sheetName = "区域信息表";
        Sheet stuSheet = wb.createSheet(sheetName);
        //获取表头行
        Row titleRow = stuSheet.createRow(0);
        //创建单元格，设置style居中，字体，单元格大小等
        CellStyle style = wb.createCellStyle();
        Cell cell = null;
        //把已经写好的标题行写入excel文件中
        for (int i = 0; i < title.length; i++) {
            cell = titleRow.createCell(i);
            cell.setCellValue(title[i]);
            cell.setCellStyle(style);
        }
        //把从数据库中取得的数据一一写入excel文件中
        Row row = null;
        for (int i = 0; i < list.size(); i++) {
            //创建list.size()行数据
            row = stuSheet.createRow(i + 1);
            //把值一一写进单元格里
            //设置第一列为自动递增的序号
            row.createCell(0).setCellValue(list.get(i).getAreaId());
            row.createCell(1).setCellValue(list.get(i).getAreaCode());
            row.createCell(2).setCellValue(list.get(i).getParentCode());
            row.createCell(3).setCellValue(list.get(i).getName());
            row.createCell(4).setCellValue(list.get(i).getLayer());
            row.createCell(5).setCellValue(list.get(i).getOrderNum());
            row.createCell(6).setCellValue(list.get(i).getStatus());
            row.createCell(7).setCellValue(list.get(i).getRemark());
            //把时间转换为指定格式的字符串再写入excel文件中
            if (Ognl.isNotEmpty(list.get(i).getGmtCreate())) {
                row.createCell(8).setCellValue(sdf.format(list.get(i).getGmtCreate()));
            }else{
                row.createCell(8).setCellValue(list.get(i).getGmtCreate());
            }
            if (Ognl.isNotEmpty(list.get(i).getGmtModified())) {
                row.createCell(9).setCellValue(sdf.format(list.get(i).getGmtModified()));
            }else {
                row.createCell(9).setCellValue(list.get(i).getGmtModified());
            }
        }
        //设置单元格宽度自适应，在此基础上把宽度调至1.5倍
        for (int i = 0; i < title.length; i++) {
            stuSheet.autoSizeColumn(i, true);
            stuSheet.setColumnWidth(i, stuSheet.getColumnWidth(i) * 15 / 10);
        }

        //创建上传文件目录
        File folder = new File(folderPath);
        //如果文件夹不存在创建对应的文件夹
        if (!folder.exists()) {
            folder.mkdirs();
        }
        //设置文件名
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss");
        String fileName = sdf1.format(new Date()) + sheetName + ".xlsx";//文件重命名
        String savePath = folderPath + fileName;

        OutputStream fileOut = new FileOutputStream(savePath);
        wb.write(fileOut);
        fileOut.close();
        //返回文件保存全路径
        return savePath;
    }
    
    
}
