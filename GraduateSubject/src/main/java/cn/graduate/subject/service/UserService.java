package cn.graduate.subject.service;

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

}
