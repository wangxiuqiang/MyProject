package cn.query.utils;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {
	public static String upload(MultipartFile file){

		//访问路径
		String fwPath = "http://39.106.191.144:8180/upload/pic/";
		String filename = "";
		try {//定义上传的位置
	    String path = "/root/query/pic/";
		File filePath = new File(path);
		//判断上传位置是否存在
		if(!filePath.exists()) {
			//创建的是当前目录
			//filePath.mkdir();
			//创建当前目录以及父目录
			filePath.mkdirs();
		}
		//获取上传文件的真实名称
		filename = file.getOriginalFilename();
		
		//获取上传文件的后缀名
		filename = filename.substring(filename.lastIndexOf('.'));
		
		//System.out.println(filename);
		//上传文件新的名称
		filename = UUID.randomUUID() + filename;
		
		path = path + filename;
		file.transferTo(new File(path));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fwPath+filename;
	} 

}
