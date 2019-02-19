package cn.fm.utils;

import com.machinezoo.sourceafis.FingerprintMatcher;
import com.machinezoo.sourceafis.FingerprintTemplate;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class FingerUtils {
    public static String first = "123";
    public static String second = "123";
    public static double fingerCompare() throws IOException {
        byte[] fptemplate = new byte[2048];
        byte[] compareTemplate = new byte[2048];
        double maxScore = 0;
//        System.out.println( System.getProperty("java.library.path"));
        /**
         * 将base64的字符串,放在 第二个参数中 ,如下的compareTemplate ,
         * 第三个参数 是而二进制数组的长度
         */
        byte[] image = Files.readAllBytes(Paths.get("/home/wxq/finger1.bmp"));
        FingerprintTemplate template = new FingerprintTemplate()
                .dpi(500)
                .create(image);
        String json = template.serialize();

        byte[] image2 = Files.readAllBytes(Paths.get("/home/wxq/finger_def.bmp"));
        FingerprintTemplate template2 = new FingerprintTemplate()
                .dpi(500)
                .create(image2);
        String json2 = template2.serialize();
        //将二进制数组转为一个指纹模板类
        System.out.println(json);
        System.out.println(json2);
        System.out.println( json.length() );
//        FingerprintTemplate probe = new FingerprintTemplate().deserialize( json );
//        //将二进制的数组转为一个指纹模板类
//        FingerprintTemplate candidate = new FingerprintTemplate().deserialize( json2 );
        //定义一个用来比较的matcher
        FingerprintMatcher matcher = new FingerprintMatcher().index(template);
        return matcher.match( template2 );

    }
    public static void main( String[] args ) throws IOException {
       double score = fingerCompare();
       System.out.println( score );
    }
}
