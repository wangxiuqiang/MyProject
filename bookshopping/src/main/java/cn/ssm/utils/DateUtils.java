package cn.ssm.utils;
/**
 * 日期转换工具类
 * 
 */
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtils {
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static String format(Date date) {
		return sdf.format(date);
	}

}
