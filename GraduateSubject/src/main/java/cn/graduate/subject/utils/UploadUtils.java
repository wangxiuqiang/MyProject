package cn.graduate.subject.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class UploadUtils {
    public static String upload(MultipartFile file , int flag ) {

        if(file.isEmpty()) {
            return null;
        }
        //访问路径
//        String fwPath = "http://39.106.191.144/OCR/pic/";
        String filename = "";
        String path = "/root/OCR/pic/";
        try {//定义上传的位置
            //获取文件的日期
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy");
            //根据年份创建一个文件夹
            String b = format.format(date);
            File filePath = new File( path  + "/" + b);
            //判断上传位置是否存在
            if (!filePath.exists()) {
                //创建的是当前目录
                //filePath.mkdir();
                //创建当前目录以及父目录
                filePath.mkdirs();
            }
            //获取上传文件的真实名称
            filename = file.getOriginalFilename();
            if( flag == 1) {
                //获取上传文件的后缀名
                filename = filename.substring(filename.lastIndexOf('.'));
            }

            System.out.println(filename);
            //上传文件新的名称

            SimpleDateFormat format2 = new SimpleDateFormat("yyyy-MM-dd");
            //创建文件名
            String a = format2.format(date);
            filename = a + filename;
//            System.out.println(filename);
            path = path + filename;
            file.transferTo(new File(path));
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        //传回来文件的地址信息
        return path;
    }

}
