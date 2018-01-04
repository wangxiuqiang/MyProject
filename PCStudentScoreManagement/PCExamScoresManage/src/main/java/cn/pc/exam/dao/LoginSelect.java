package cn.pc.exam.dao;

import cn.pc.exam.pojo.Admin;
import cn.pc.exam.pojo.LoginPo;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Teacher;

/**
 * 用来查找登录用的账号密码   包含admin   teacher student  的函数
 */
public interface LoginSelect {
    public Admin queryAdmin(String name) throws Exception;

    public Teacher queryTeacher(String id) throws Exception;

    public Student queryStudent(String id) throws Exception;

}
