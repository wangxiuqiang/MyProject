package cn.pc.exam.dao;

import cn.pc.exam.pojoExtends.StudentExtend;

public interface StudentManager {
    public int updatePassword(String password,String Sid) throws Exception;
    public StudentExtend selectStudent(String Sid) throws Exception;
}
