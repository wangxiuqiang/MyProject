package cn.graduate.subject.service.serviceImpl;

import cn.graduate.subject.dao.UserMapper;
import cn.graduate.subject.service.UserService;
import cn.graduate.subject.utils.PassWordHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserMapper userMapper;
    /**
     * 根据用户的学号修改密码
     * @param upwd
     * @param uaccount
     * @return
     * @throws Exception
     */
    @Override
    public int updatePwd( String upwd ,  String uaccount ) throws Exception {
        //将明文密码转换为密文
        PassWordHelper pwh = new PassWordHelper();
        upwd = pwh.SHA256( upwd );
        return userMapper.updatePwd( upwd , uaccount );
    }
    /**
     * 添加一个题目给自己作为毕业设计
     * @param sid
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public int addSubjectForSelf (int sid, int uid ) throws Exception {
        return userMapper.addSubjectForSelf( sid, uid );
    }

    /**
     * 删除自己的题目,
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public int delSubjectForSelf (  int uid ) throws Exception {
        return userMapper.delSubjectForSelf( uid );
    }
    /**
     * 更新邮箱到数据库
     * @param uemail
     * @param uaccount
     * @return
     * @throws Exception
     */
    @Override
    public int updateEmail( String uemail ,  String uaccount) throws Exception {
        return userMapper.updateEmail(uemail,uaccount);
    }



}
