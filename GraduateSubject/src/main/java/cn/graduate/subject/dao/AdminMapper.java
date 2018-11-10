package cn.graduate.subject.dao;

import cn.graduate.subject.pojo.College;
import cn.graduate.subject.pojo.Grade;
import cn.graduate.subject.pojo.Subject;
import cn.graduate.subject.pojo.User;
import cn.graduate.subject.vo.UserAndSuject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
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
    public int delSubject(@Param( value = "sid") int sid )throws Exception;

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
    public Subject selectSubjectById( @Param(value = "sid") int sid ) throws Exception;

    /**
     * 根据题目的名称进行模糊查询
     * @param sname
     * @return
     * @throws Exception
     */
    public List<Subject> selectSubjectByName (@Param( value = "sname") String sname ) throws Exception;

    /**
     * 在查看一个题目的时候, 查看有多少人选了
     * @param sid
     * @return
     * @throws Exception
     */
    public int selectNumberForSuject( @Param(value = "sid") int sid ) throws Exception;
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
    public int delUser ( @Param(value = "uid") int uid ) throws Exception;

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
    public List<UserAndSuject> selectUserByMoreWays( User user ) throws Exception;

    /**
     * 根据cid查找班级
     * @param cid
     * @return
     * @throws Exception
     */
    public List<Grade> selectGradeByCid(@Param(value = "cid") int cid ) throws Exception;

    /**
     * 根据gid查找专业
     */
    public College selectCollegeByCid(@Param(value = "cid") int cid ) throws Exception;
    /**
     * 根据gid 查找专业
     */
    public Grade selectGradeByGid( @Param( value = "gid" ) int gid ) throws Exception;

    /**
     * 查找所有的专业
     *
     * @return
     * @throws Exception
     */

    public List<College> selectCollege() throws Exception;
}
