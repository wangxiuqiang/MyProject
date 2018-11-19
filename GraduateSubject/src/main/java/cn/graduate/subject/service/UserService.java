package cn.graduate.subject.service;

import cn.graduate.subject.pojo.Progress;

public interface UserService {
    /**
     * 根据用户的学号修改密码
     * @param upwd
     * @param uaccount
     * @return
     * @throws Exception
     */
    public int updatePwd( String upwd ,  String uaccount ) throws Exception;
    /**
     * 添加一个题目给自己作为毕业设计
     * @param sid
     * @param uid
     * @return
     * @throws Exception
     */
    public int addSubjectForSelf (int sid, int uid ) throws Exception;

    /**
     * 删除自己的题目,
     * @param uid
     * @return
     * @throws Exception
     */
    public int delSubjectForSelf (  int uid ) throws Exception;

    /**
     * 更新邮箱到数据库
     * @param uemail
     * @param uaccount
     * @return
     * @throws Exception
     */
    public int updateEmail( String uemail ,  String uaccount) throws Exception;

    /**
     * 通过学号查出邮箱并将密码改为初始化
     * @param uaccount
     * @return
     * @throws Exception
     */
    public String selectEmailAndUpdateUpwd( String uaccount ) throws Exception;


    /**
     * 添加一个进度到数据库
     * @param pone
     * @return
     * @throws Exception
     */
    public int addProgress(  String pone  , int uid ) throws Exception;

    /**
     * 同时将pid写到user中,保证学生和自己的id相对应
     * @param pid
     * @param uid
     * @return
     * @throws Exception
     */
    public int insertUserPid( int pid , int uid ) throws Exception;

    /**
     * 更新一个新的进程
     * @param progress
     * @return
     * @throws Exception
     */
    public int updateProgress(Progress progress ) throws Exception;
    /**
     * 根据pid查找一个学生的进程
     */
    public Progress selectProgressByPid( int pid ) throws Exception;
}
