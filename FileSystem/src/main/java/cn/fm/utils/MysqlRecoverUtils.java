package cn.fm.utils;

import org.springframework.web.multipart.MultipartFile;

public class MysqlRecoverUtils {

     public static int recover(MultipartFile file) throws Exception {
         /**
          * 获取配置文件信息
          */
         MysqlBackupUtils mysqlBackupUtils = new MysqlBackupUtils();
         String[] mysqlInfo = mysqlBackupUtils.getProperties();
         /**
          * 获取路径名
          */
         String fileName = file.getOriginalFilename();
         String cmd = "mysql " + MysqlBackupUtils.DB_NAME + "-u" + mysqlInfo[0] + " -p" + mysqlInfo[1]
                 + " -e source " + fileName;

         Process process = Runtime.getRuntime().exec(cmd);

         return process.waitFor();
     }
}
