package cn.graduate.subject.utils;



import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;


public class MailUtils {
    //state 用来判断是谁发的邮件  1表示 管理员发给学生的邮件, 其他的表示学生修改密码
    public static void sendMail(int state,String email,String content) throws Exception {
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
//       String uname1 = "*" + uname.substring(1);

        Session session = Session.getInstance(prop,authenticator);

        Message message  = new MimeMessage(session);//邮件对象
        String fromWhere = "wangxiuqianga@163.com";
//        String toWhere = "1520192786@qq.com";
//设置发件人
        message.setFrom(new InternetAddress(fromWhere));//括号里需要一个地址对象，不能直接输入字符串
//设置收件人
        message.setRecipient(Message.RecipientType.TO,new InternetAddress(email));
        /**message.setRecipient(type,address) 前面的type有三种 TO 发送给谁
         ， CC  ：抄送给谁 , BCC : 暗送给谁*/
//设置邮件主题
        if( state == 1 ) {
            message.setSubject("通知");
        } else if( state == 2 ){
            message.setSubject("修改密码");
        } else {
            message.setSubject("绑定邮箱");
        }

//设置文件正文
      //  String content = "<a href='http://localhost:8080/user/activecode?code="+code+"'>点击激活</a>";
//        String content = "<a href='http://39.106.191.144/worker/activecode?activecode="+code+"'>" + uname1 + ",主管使用您的邮箱进行登录,确认则点击激活</a>";
        message.setContent(content,"text/html;charset=UTF-8");
        Transport.send(message);
    }

}
