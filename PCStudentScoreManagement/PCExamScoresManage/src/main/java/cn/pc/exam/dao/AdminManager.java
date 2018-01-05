package cn.pc.exam.dao;

import cn.pc.exam.pojo.Teacher;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;

public interface AdminManager {
    public TeacherExtend queryTeacher(String id) throws Exception;
    public StudentExtend queryStudent(String id) throws Exception;
}
