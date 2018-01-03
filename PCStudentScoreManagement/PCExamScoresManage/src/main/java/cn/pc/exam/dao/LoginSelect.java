package cn.pc.exam.dao;

import cn.pc.exam.pojo.Admin;

/**
 * 用来查找登录用的账号密码
 */
public interface LoginSelect {
    public Admin queryAdmin(String name) throws Exception;
}
