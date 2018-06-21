package cn.ssm.crm.utils;

import java.util.UUID;

/**
 * 
 * 返回String的工具类
 * @author wxq
 *
 */
public class StringUtils {
//	c产生部门的id
    public  static String createUUID() {
    	return UUID.randomUUID().toString().replaceAll("-", "a");
    }
    /**
     * 如果是空或者为"",则返回true  ,有值则返回false
     * @param source
     * @return
     */
    public static boolean isEmpty(String source) {
    	//如果写 source.equals("") ,可能报空指针,因为source可能是空
    	return source == null || "".equals(source.trim());
    }
}
