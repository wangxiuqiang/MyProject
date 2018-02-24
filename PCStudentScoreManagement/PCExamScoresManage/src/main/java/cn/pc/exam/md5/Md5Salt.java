package cn.pc.exam.md5;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 *
 * 进行md5 的加密算法,  用于验证用户登录,
 * 1,将用户的密码换为MD5编码,在进行验证,
 * 2.将插入的信息面码进行MD5转码
 * 由于Md5的不可逆性,将 用户登录的密码用来转码后和数据库中的进行比较
 */
public class Md5Salt {

    public static String md5(String str ){
        return new Md5Hash(str,"521").toString();
    }

    public static void main(String[] args) {
      System.out.println(md5("150501"));
     }
}
