package cn.fm.utils;

/**
 * 用来删除图片的工具类
 */
public class RmFileUtils {
    private static String cmd = "rm -r /root/OCR/pic/*";
//    private static String cmd = "rm -r /home/wxq/finger/*";
    public static void rmFile() throws  Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
    }
}
