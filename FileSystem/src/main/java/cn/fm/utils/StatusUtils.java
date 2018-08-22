package cn.fm.utils;

/**
 * 状态码的工具类
 */
public class StatusUtils {
    public static final int PAGE_SIZE = 15;

    public static final  String statecode = "statecode";

    /**
     * 传过来的是null 空值
     */
    public static final int IS_NULL = 11;
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
     * 登录失败,
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

    /**
     * 没有相关权限
     */
    public static final int NO_ROLE_PERMISSION = -1;
/**
 * 账号没有激活
 */
    public static final int LOCK_INFO = -2;

    /**
     *   记录 存在
     */
    public static final int EXIST_CONTENT = 12;
}
