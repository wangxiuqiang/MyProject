package cn.ssm.service;

import cn.ssm.pojo.Users;

public interface UserService {

    public int saveUser(Users users);
    public int updateState(String code);

    public String selectUsername(String name);
}
