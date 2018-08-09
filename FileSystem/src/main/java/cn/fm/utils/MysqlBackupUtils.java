package cn.fm.utils;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class MysqlBackupUtils {

    public static String BACKUP_DIR = "/home/wxq/filesystem_";
    public static String DB_NAME = "filesystem";
    private static SimpleDateFormat sdf = new SimpleDateFormat("yy-MM-dd");

    public String[] getProperties() throws Exception {
        /**
         * 读取配置文件
         */
        InputStream inputStream = this.getClass().getClassLoader().getResourceAsStream("db.properties");
        Properties properties = new Properties();
        properties.load(inputStream);

        String[] mysqlInfo = {properties.getProperty("jdbc.username"),properties.getProperty("jdbc.password")};
        return mysqlInfo;
    }

//    public static void main(String[] args) throws Exception {
//        MysqlBackupUtils mysqlBackupUtils = new MysqlBackupUtils();
//        String[] mysqlInfo = mysqlBackupUtils.getProperties();
//        System.out.println(mysqlInfo[0] + "\n" + mysqlInfo[1]);
//        backup();
//    }

    public static int backup() throws Exception {
        String time = sdf.format(new Date());
        String savePath =  BACKUP_DIR + time +".sql";
        /**
         * 获取配置文件中的信息
         */
        MysqlBackupUtils mysqlBackupUtils = new MysqlBackupUtils();
        String[] mysqlInfo = mysqlBackupUtils.getProperties();

        String cmd = "mysqldump -u"+ mysqlInfo[0] +" -p"+ mysqlInfo[1] +" --databases " +
                DB_NAME + " -r "+
                savePath + " --skip-lock-tables";
        //这个 -r 不能省..如果要成功,就一定有,  输出符号不需要
        //--skip-lock-tables 跳过表锁定,,加快速度
        Process process = Runtime.getRuntime().exec(cmd);

//        int processComplete = process.waitFor();
//        if(processComplete == 0) {
//            System.out.println("备份成功");
//        }else {
//            System.out.println("备份失败");
//        }

        return process.waitFor();
    }
}
