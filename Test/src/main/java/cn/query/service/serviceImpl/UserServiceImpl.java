package cn.query.service.serviceImpl;

import cn.query.dao.UserDao;
import cn.query.pojo.FileInSystem;
import cn.query.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{
    @Autowired
    UserDao userDao;

    @Override
    public List<FileInSystem> selectFileByName(String name) throws Exception {
        return userDao.selectFileByName(name);
    }
}
