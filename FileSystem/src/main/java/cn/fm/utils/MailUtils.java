package cn.fm.utils;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class MailUtils {

    public static void sendMail(String code,String email) throws Exception {
        Properties prop = new Properties();
        prop.setProperty("mail.host","smtp.163.com");
        prop.setProperty("mail.smtp.auth","true");

        prop.setProperty("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        prop.setProperty("mail.smtp.socketFactory.fallback", "false");
        prop.setProperty("mail.smtp.port", "465");
        prop.setProperty("mail.smtp.socketFactory.port", "465");

        Authenticator authenticator = new Authenticator() {
            //在这里面实现账户密码的认证,就可以登录到邮箱服务器上了
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("wangxiuqianga@163.com","251698wodewxqWXQ");
            }
        };

        Session session = Session.getInstance(prop,authenticator);

        Message message  = new MimeMessage(session);//邮件对象
        String fromWhere = "wangxiuqianga@163.com";
//        String toWhere = "1520192786@qq.com";
//设置发件人
        message.setFrom(new InternetAddress(fromWhere));//括号里需要一个地址对象，不能直接输入字符串
//设置收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
        /**message.setRecipient(type,address) 前面的type有三种TO 发送给谁
         ， CC  ：抄送给谁 , BCC : 暗送给谁*/
//设置邮件主题
        message.setSubject("请激活");
//设置文件正文
      //  String content = "<a href='http://localhost:8080/user/activecode?code="+code+"'>点击激活</a>";
        String content = "<a href='http://39.106.191.144:8087/user/activecode?code="+code+"'>点击激活</a>";
        message.setContent(content,"text/html;charset=UTF-8");
        Transport.send(message);
    }

}
