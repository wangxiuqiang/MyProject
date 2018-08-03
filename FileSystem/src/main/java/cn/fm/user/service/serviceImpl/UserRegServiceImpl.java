package cn.fm.user.service.serviceImpl;

import cn.fm.user.dao.UserRegMapper;
import cn.fm.user.service.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegServiceImpl implements UserRegService {
    @Autowired
    UserRegMapper userRegMapper;

    /**
     * 设置密码
     * @param upwd
     * @param code
     * @return
     * @throws Exception
     */
    @Override
    public int updateUserpwd(String upwd, String code) throws Exception {
        return userRegMapper.updateUserpwd(upwd,code);
    }
}
