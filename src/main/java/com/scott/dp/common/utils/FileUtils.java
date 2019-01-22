package com.scott.dp.common.utils;
import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * @CLASSNAME :FileUtils
 * @Description :文件操作集合
 * @Author :Mr.薛
 * @Data :2018/12/29 0029  10:12
 * @Version :V1.0
 * @Status : 编写
 **/
public class FileUtils {
    private static ArrayList<Object> scanFiles = new ArrayList<Object>();

    /**linkedList实现**/
    private static LinkedList<File> queueFiles = new LinkedList<File>();


    /**
     * TODO:递归扫描指定文件夹下面的指定文件
     * @return ArrayList<Object>
     * @author Mr.薛
     * @time 2017年11月3日
     */
    public static ArrayList<Object> scanFilesWithRecursion(String folderPath) throws Exception{
        ArrayList<String> dirctorys = new ArrayList<String>();
        File directory = new File(folderPath);
        if(!directory.isDirectory()){
            throw new Exception('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
        }
        if(directory.isDirectory()){
            File [] filelist = directory.listFiles();
            for(int i = 0; i < filelist.length; i ++){
                /**如果当前是文件夹，进入递归扫描文件夹**/
                if(filelist[i].isDirectory()){
                    scanFiles.add(filelist[i].getAbsolutePath());
                    /**递归扫描下面的文件夹**/
                    scanFilesWithRecursion(filelist[i].getAbsolutePath());
                }
                /**非文件夹**/
                else{
                    scanFiles.add(filelist[i].getAbsolutePath());
                }
            }
        }
        return scanFiles;
    }

    /**
     *
     * TODO:非递归方式扫描指定文件夹下面的所有文件
     * @return ArrayList<Object>
     * @param folderPath 需要进行文件扫描的文件夹路径
     * @author Mr.薛
     * @time 2017年11月3日
     */
    public static ArrayList<Object> scanFilesWithNoRecursion(String folderPath) throws Exception{
        File directory = new File(folderPath);
        if(!directory.isDirectory()){
            throw new Exception('"' + folderPath + '"' + " input path is not a Directory , please input the right path of the Directory. ^_^...^_^");
        }
        else{
            //首先将第一层目录扫描一遍
            File [] files = directory.listFiles();
            //遍历扫出的文件数组，如果是文件夹，将其放入到linkedList中稍后处理
            for(int i = 0; i < files.length; i ++){
                if(files[i].isDirectory()){
                    queueFiles.add(files[i]);
                }else{
                    //暂时将文件名放入scanFiles中
                    scanFiles.add(files[i].getAbsolutePath());
                }
            }

            //如果linkedList非空遍历linkedList
            while(!queueFiles.isEmpty()){
                //移出linkedList中的第一个
                File headDirectory = queueFiles.removeFirst();
                File [] currentFiles = headDirectory.listFiles();
                for(int j = 0; j < currentFiles.length; j ++){
                    if(currentFiles[j].isDirectory()){
                        //如果仍然是文件夹，将其放入linkedList中
                        scanFiles.add(currentFiles[j]);
                    }else{
                        scanFiles.add(currentFiles[j].getAbsolutePath());
                    }
                }
            }
        }

        return scanFiles;
    }

    /**
     *
     * TODO:判断文件类型
     * @return ArrayList<Object>
     * @param fileName 文件名称
     * @author Mr.薛
     * @time 2019年1月7日
     */
    public static String getFileType(String fileName) {
        String[] strArray = fileName.split("\\.");
        int suffixIndex = strArray.length -1;
        return strArray[suffixIndex];
    }

    /**
     *
     * TODO:判断文件类型
     * @return ArrayList<Object>
     * @param fileName 文件名称  0:空 1:图片 2：文档 3:视频 4:音乐 5:其他
     * @author Mr.薛
     * @time 2019年1月7日
     */
    public static int fileType(String fileName) {
        if (fileName == null) {
            return 0;
        } else {
// 获取文件后缀名并转化为写，用于后续比较
            String fileType = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length()).toLowerCase();
// 创建图片类型数组
            String img[] = { "bmp", "jpg", "jpeg", "png", "tiff", "gif", "pcx", "tga", "exif", "fpx", "svg", "psd",
                    "cdr", "pcd", "dxf", "ufo", "eps", "ai", "raw", "wmf" };
            for (int i = 0; i < img.length; i++) {
                if (img[i].equals(fileType)) {
                    return 1;
                }
            }
// 创建文档类型数组
            String document[] = { "txt", "doc", "docx", "xls", "htm", "html", "jsp", "rtf", "wpd", "pdf", "ppt" };
            for (int i = 0; i < document.length; i++) {
                if (document[i].equals(fileType)) {
                    return 2;
                }
            }
// 创建视频类型数组
            String video[] = { "mp4", "avi", "mov", "wmv", "asf", "navi", "3gp", "mkv", "f4v", "rmvb", "webm" };
            for (int i = 0; i < video.length; i++) {
                if (video[i].equals(fileType)) {
                    return 3;
                }
            }
// 创建音乐类型数组
            String music[] = { "mp3", "wma", "wav", "mod", "ra", "cd", "md", "asf", "aac", "vqf", "ape", "mid", "ogg",
                    "m4a", "vqf" };
            for (int i = 0; i < music.length; i++) {
                if (music[i].equals(fileType)) {
                    return 4;
                }
            }


        }
        return 5;
    }


    /**
     *
     * TODO:单个文件上传
     * @return ArrayList<Object>
     * @param fileName 文件名称
     * @author Mr.薛
     * @time 2019年1月7日
     */
    public static void uploadFile(byte[] file, String filePath, String fileName) throws Exception {
        File targetFile = new File(filePath);
        if(!targetFile.exists()){
            targetFile.mkdirs();
        }
        FileOutputStream out = new FileOutputStream(filePath+fileName);
        out.write(file);
        out.flush();
        out.close();
    }

    public static void main(String[] args) throws Exception{
//        ArrayList<Object> list = FileUtils.scanFilesWithRecursion("E:\\work\\idea\\dp-BOOT\\doc");
//        ArrayList<Object> list = FileUtils.scanFilesWithNoRecursion("E:\\work\\idea\\dp-BOOT\\doc");
//        for(Object o:list){
//            System.out.println(o);
//        }
//        System.out.println(getFileType("a.jpg"));
        System.out.println(fileType("a.jpg"));
    }






}
