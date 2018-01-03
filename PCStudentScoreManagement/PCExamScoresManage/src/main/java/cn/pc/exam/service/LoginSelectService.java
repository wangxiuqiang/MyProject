package cn.pc.exam.service;

import cn.pc.exam.pojo.Admin;
import cn.pc.exam.pojo.Teacher;

public interface LoginSelectService {
    public Admin queryAdminIDAndPassWd(String name ) throws Exception;
    public Teacher queryTeacher(String id ) throws Exception;


}
