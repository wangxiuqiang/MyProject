package cn.graduate.subject.service;

import cn.graduate.subject.pojo.Role;
import cn.graduate.subject.pojo.User;

import java.util.Set;

public interface LoginService {

    /**
     * 登录查询 用户的密码和权限信息
     * @param uaccount
     * @param
     * @return
     */
    public User selectUser( String uaccount ) throws Exception;

    /**
     * 查询权限信息
     * @param rid
     * @return
     * @throws Exception
     */

    public Set<String> selectRole(int rid ) throws Exception;
}
