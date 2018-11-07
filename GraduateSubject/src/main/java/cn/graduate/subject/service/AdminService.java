package cn.graduate.subject.service;

import cn.graduate.subject.pojo.Subject;
import cn.graduate.subject.pojo.User;
import cn.graduate.subject.vo.UserAndSuject;

import java.util.List;

public interface AdminService {
    /**
     * 添加一个题目
     * @param subject
     * @return
     * @throws Exception
     */
    public int addSubject(Subject subject ) throws Exception;
    /**
     * 根据 更新一个题目
     * @param subject
     * @return
     * @throws Exception
     */
    public int updateSubject ( Subject subject ) throws Exception;

    /**
     * 根据sid 删除一个题目
     * @param sid
     * @return
     * @throws Exception
     */
    public int delSubject(int sid )throws Exception;

    /**
     * 查看所有的题目信息
     * @return
     * @throws Exception
     */
    public List<Subject> selectAllSubject() throws Exception;

    /**
     * 根据id查看某一个的题目
     * @param sid
     * @return
     * @throws Exception
     */
    public Subject selectSubjectById(  int sid ) throws Exception;

    /**
     * 根据题目的名称进行模糊查询
     * @param sname
     * @return
     * @throws Exception
     */
    public List<Subject> selectSubjectByName (String sname ) throws Exception;

    /**
     * 添加账户信息
     * @param user
     * @return
     * @throws Exception
     */
    public int addUser(User user ) throws Exception ;

    /**
     * 删除用户,根据用户的编号
     * @param uid
     * @return
     * @throws Exception
     */
    public int delUser (  int uid ) throws Exception;

    /**
     * 根据用户的编号更新一个用户
     * @param user
     * @return
     * @throws Exception
     */
    public int updateUser( User user ) throws Exception;

    /**
     * 多种方式查询用户,
     * @param user
     * @return
     * @throws Exception
     */
    public List<UserAndSuject> selectUserByMoreWays(User user ) throws Exception;
}
