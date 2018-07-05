package cn.ssm.dao;

import cn.ssm.pojo.Users;

import java.util.List;

public interface UserMapper {


    /*
    对用户信息的增删改查,
     */
    public Users selectUser(int id) ;
    public int updateUser(Users users);
    public int deleteUsers( int id);
    public int insertUsers(Users users);
    public List<Users> findAllUsers();

    /**
     * 登录使用的查询,如果有这个记录就返回 ,没有就返回null
     * @param id
     * @return
     */
    public Users login(int id) ;
    public int updateState(String code);
//    判断是不是有了这个用户
    public String selectUsername(String name);
}
