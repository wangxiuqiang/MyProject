package cn.graduate.subject.dao;

import cn.graduate.subject.pojo.Progress;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    /**
     * 根据用户的学号修改密码
     * @param upwd
     * @param uaccount
     * @return
     * @throws Exception
     */
    public int updatePwd(@Param(value = "upwd") String upwd , @Param( value = "uaccount") String uaccount ) throws Exception;

    /**
     * 添加一个题目给自己作为毕业设计
     * @param sid
     * @param uid
     * @return
     * @throws Exception
     */
    public int addSubjectForSelf ( @Param( value = "sid") int sid, @Param( value = "uid") int uid ) throws Exception;

    /**
     * 删除自己的题目,
     * @param uid
     * @return
     * @throws Exception
     */
    public int delSubjectForSelf ( @Param(value = "uid" ) int uid ) throws Exception;

    public int updateEmail(@Param(value = "uemail") String uemail , @Param(value = "uaccount") String uaccount) throws Exception;

    /**
     * 将密码初始化
     * @param uaccount
     * @return
     * @throws Exception
     */
    public int updateUpwdForBegin(@Param( value = "uaccount") String uaccount ) throws Exception;

    /**
     * 通过学号查找邮件
     * @param uaccount
     * @return
     * @throws Exception
     */
    public String selectEmailByUaccount( @Param( value = "uaccount") String uaccount) throws Exception;

    /**
     * 添加一个进度到数据库
     * @param progress
     * @return
     * @throws Exception
     */
    public int addProgress( Progress progress  ) throws Exception;

    /**
     * 同时将pid写到user中,保证学生和自己的id相对应
     * @param pid
     * @param uid
     * @return
     * @throws Exception
     */
    public int insertUserPid( @Param( value = "pid") int pid ,@Param(value = "uid") int uid ) throws Exception;

    /**
     * 更新一个新的进程
     * @param progress
     * @return
     * @throws Exception
     */
    public int updateProgress( Progress progress ) throws Exception;
    /**
     * 根据pid查找一个学生的进程
     */
    public Progress selectProgressByPid( @Param( value = "pid") int pid ) throws Exception;
}
