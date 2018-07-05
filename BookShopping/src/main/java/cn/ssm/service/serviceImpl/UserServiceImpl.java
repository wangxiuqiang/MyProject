package cn.ssm.service.serviceImpl;

import cn.ssm.dao.UserMapper;
import cn.ssm.pojo.Users;
import cn.ssm.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserMapper userMapper;

   @Override
    public int saveUser(Users users){
       return userMapper.insertUsers(users);
   }

    @Override
    public int updateState(String code) {
        return userMapper.updateState(code);
    }

    @Override
    public String selectUsername(String name) {
        return userMapper.selectUsername(name);
    }
}
