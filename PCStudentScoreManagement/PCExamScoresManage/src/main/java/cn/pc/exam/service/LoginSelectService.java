package cn.pc.exam.service;

import cn.pc.exam.pojo.Admin;
import cn.pc.exam.pojo.LoginPo;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Teacher;

public interface LoginSelectService {
    public Admin queryAdminIDAndPassWd(String name ) throws Exception;
    public Teacher queryTeacher(String id ) throws Exception;
    public Student queryStudent(String id ) throws Exception;


}
