package cn.graduate.subject.dao;

import cn.graduate.subject.pojo.Role;
import cn.graduate.subject.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.Set;

public interface LoginMapper {
    /**
     * 登录查询 用户的密码和权限信息
     * @param uaccount
     * @param
     * @return
     */
    public User selectUser(@Param( value = "uaccount" ) String uaccount  ) throws Exception;

    /**
     * 查询权限信息
     * @param rid
     * @return
     * @throws Exception
     */

    public Set<String> selectRole(@Param( value = "rid" ) int rid ) throws Exception;
}
