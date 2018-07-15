package cn.ssm.utils;

import java.io.File;
import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

public class UploadUtils {
	public static String upload(MultipartFile file){
		//访问路径
		String fwPath = "http://localhost:8080/bookshopping/pic/";
		String filename = "";
		try {//定义上传的位置
		String path = "/home/wxq/IdeaProjects/BookShopping/pic";
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
		filename = UUIDUtils.getUUID() + filename;
		
		path = path+filename;
	
			file.transferTo(new File(path));
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return fwPath+filename;
	} 

}
