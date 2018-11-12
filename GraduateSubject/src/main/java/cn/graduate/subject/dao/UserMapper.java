package cn.graduate.subject.dao;

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
 }