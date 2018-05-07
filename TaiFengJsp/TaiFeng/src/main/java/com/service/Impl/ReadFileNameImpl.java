package com.service.Impl;

import com.service.ReadFileName;
import org.springframework.stereotype.Service;

import java.io.File;

@Service("readFinishImageFile") //结训small照片读取
public class ReadFileNameImpl  implements ReadFileName{
    @Override
    public String[] fileNames() {
        File imageFinishMkdir = new File("/home/wxq/IdeaProjects/TaiFeng/src/main/webapp/images/2014-2015-2016/finish/smallShow");
        String[] names = imageFinishMkdir.list();
        return names;
    }

//    public static void main(String[] args) {
//        ReadFileName readFileName = new ReadFileNameImpl();
//       String[] name = readFileName.fileNames();
//        for(int i = 0; i < name.length; i ++){
//            System.out.println(name[i]);
//        }
//    }
}
