package cn.ssm.utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 日期转换的工具类
 * @author Administrator
 *
 */
public class DateUtils {

	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String format(Date date){
		return sdf.format(date);
	}

}