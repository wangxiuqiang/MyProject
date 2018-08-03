package cn.fm.utils;

/**
 * 状态码的工具类
 */
public class StatusUtils {

    /**
     * 成功注册
     */
    public static final int SUCCESS_REG = 1;
    /**
     *   注册失败  (管理员注册 和 用户激活用)
     */
    public final static  int FAILURE_REG = 0;

    /**
     *   登录成功
     */
    public static final int SUCCESS_LOGIN = 2;
    /**
     * 登录失败
     */
    public static final int FAILURE_LOGIN = 3;
    /**
     * 查询成功
     */
    public static final  int SUCCESS_FIND = 5;
    /**
     * 没查询到结果
     */
    public static final int FAILURE_FIND = 4;

    /**
     * 录入成功
     */
    public static final  int SUCCESS_INSERT = 6;
    /**
     * 录入失败
     */
    public static final int FAILURE_INSERT = 7;

    public static final int SUCCESS_DEL = 9;

    public static final int FAILURE_DEL = 10;
}
