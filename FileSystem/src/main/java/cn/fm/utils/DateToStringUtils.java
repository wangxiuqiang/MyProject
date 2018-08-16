package cn.fm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateToStringUtils {
   private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
   public static String dataTostring() {
       return sdf.format(new Date());
   }
}
