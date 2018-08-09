package cn.fm.utils;

import cn.fm.pojo.Admin;
import cn.fm.pojo.User;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PassWordHelper {

    private  String algorithmName = "SHA-256";
    private  String  salt ;
    private  int hasIterations = 3;
    /**
     * 用来获取配置文件中的数据,
     * 首先通过输入流 将文件读取,使用properties来加载输入流,从中获取信息
     * @return
     * @throws Exception
     */
    public String getSalt() {
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("shiro/salt.properties");

        Properties properties = new Properties();
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
//        System.out.println(properties.getProperty("salt"));
        return properties.getProperty("salt");
    }




    public  String SHA256(String upwd) throws Exception{
//        System.out.println(admin.getApwd());
//        if(admin.getApwd() != null) {
//            salt = admin.getAname() + getSalt();
//            System.out.println(salt);
//            SimpleHash simpleHash = new SimpleHash(algorithmName,admin.getApwd(),salt,hasIterations);
//            System.out.println(simpleHash.toHex());
//            return simpleHash.toHex();
//        }else if(user.getUpwd() != null){
//            salt = user.getUname() + getSalt();
//            System.out.println(salt);
//            SimpleHash simpleHash = new SimpleHash(algorithmName,user.getUpwd(),salt,hasIterations);
//            System.out.println(simpleHash.toHex());
//            return simpleHash.toHex();
//        }else {
//            return null;
//        }
        salt = getSalt();
        if (upwd != null) {
            SimpleHash simpleHash = new SimpleHash(algorithmName,upwd,salt,hasIterations);
//            System.out.println(simpleHash.toHex());
            return simpleHash.toHex();
        }else {
            return null;
        }
    }
}
