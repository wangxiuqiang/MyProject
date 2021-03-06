package cn.graduate.subject.service.serviceImpl;

import cn.graduate.subject.dao.UserMapper;
import cn.graduate.subject.pojo.Progress;
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

    /**
     * 通过学号查出邮箱并将密码改为初始化
     * @param uaccount
     * @return
     * @throws Exception
     */
    @Override
    public String selectEmailAndUpdateUpwd( String uaccount ) throws Exception{
        String email = userMapper.selectEmailByUaccount( uaccount );
        if( email != null ) {
            int result = userMapper.updateUpwdForBegin( uaccount );
            if ( result > 0 ) {
                return email;
            }
            return null;
        }
        return null;
    }

    /**
     * 添加一个进度到数据库
     * @param pone
     * @return
     * @throws Exception
     */
    public int addProgress(  String pone , int uid ) throws Exception {
        Progress progress = new Progress();
        progress.setPone(pone);
        int result = userMapper.addProgress( progress );
        if( result > 0 ) {
            return    insertUserPid( progress.getPid() , uid );
        } else {
            return 0;
        }

    }

    /**
     * 同时将pid写到user中,保证学生和自己的id相对应
     * @param pid
     * @param uid
     * @return
     * @throws Exception
     */
    public int insertUserPid( int pid , int uid ) throws Exception {
        return userMapper.insertUserPid( pid , uid );
    }

    /**
     * 更新一个新的进程
     * @param progress
     * @return
     * @throws Exception
     */
    public int updateProgress(Progress progress ) throws Exception {
         return userMapper.updateProgress( progress );
    }
    /**
     * 根据pid查找一个学生的进程
     */
    public Progress selectProgressByPid( int pid ) throws Exception {
        return userMapper.selectProgressByPid( pid );
    }
}
