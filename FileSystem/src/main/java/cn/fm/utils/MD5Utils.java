package cn.fm.utils;

import cn.fm.pojo.Admin;
import cn.fm.pojo.User;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.io.InputStream;
import java.util.Properties;

public class MD5Utils {


    /**
     * 用来获取配置文件中的数据,
     * 首先通过输入流 将文件读取,使用properties来加载输入流,从中获取信息
     * @return
     * @throws Exception
     */
    public  String getSalt() throws  Exception{
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("shiro/salt.properties");

        Properties properties = new Properties();
        properties.load(inputStream);
       return properties.getProperty("salt");
    }

//    public static void main(String[] args) throws Exception {
//       MD5Utils md5Utils = new MD5Utils();
//       md5Utils.getSalt();
//       System.out.println(md5("123"));

//        SimpleHash simpleHash = new SimpleHash("md5","123","filename",1);
//        System.out.println(simpleHash.toString());
//        System.out.println(simpleHash.toHex());
//        Admin  admin = new Admin();
//        admin.setAname("123");
//        admin.setApwd("123");
//        User user = new User();
//        user.setUpwd("456");
//        user.setUname("456");
//        PassWordHelper p = new PassWordHelper();
//        p.SHA256(new Admin(),user);
//    }

    public static String md5(String str) throws  Exception{
        MD5Utils md5Utils = new MD5Utils();

        return new Md5Hash(str,md5Utils.getSalt()).toString();
    }
}
