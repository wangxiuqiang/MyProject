package cn.graduate.subject.utils;

/**
 * 用来删除图片的工具类
 */
public class RmFileUtils {
    private static String cmd = "rm -r /root/OCR/pic/*";
    public static void rmFile() throws  Exception {
        Process process = Runtime.getRuntime().exec(cmd);
        process.waitFor();
    }
}
