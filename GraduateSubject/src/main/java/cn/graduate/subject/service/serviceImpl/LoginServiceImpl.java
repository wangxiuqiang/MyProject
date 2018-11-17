package cn.graduate.subject.service.serviceImpl;

import cn.graduate.subject.dao.LoginMapper;
import cn.graduate.subject.pojo.Role;
import cn.graduate.subject.pojo.User;
import cn.graduate.subject.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class LoginServiceImpl implements LoginService {

    @Autowired
    LoginMapper loginMapper;
    /**
     * 登录查询 用户的密码和权限信息
     * @param uaccount
     *
     * @return
     */
    public User selectUser(String uaccount  , int ifAdmin ) throws Exception {
        return loginMapper.selectUser( uaccount ,ifAdmin );
    }

    /**
     * 查询权限信息
     * @param rid
     * @return
     * @throws Exception
     */

    public Set<String> selectRole(int rid ) throws Exception {
        return loginMapper.selectRole( rid );
    }
}
