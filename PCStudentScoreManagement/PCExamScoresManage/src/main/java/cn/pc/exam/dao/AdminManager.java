package cn.pc.exam.dao;


import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Teacher;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;

import java.util.List;

public interface AdminManager {
    public TeacherExtend queryTeacher(String id) throws Exception;
    public StudentExtend queryStudent(String id) throws Exception;
    public List<StudentExtend> queryAllStudent() throws Exception;
    public List<TeacherExtend> queryAllTeacher() throws Exception;
    public StudentExtend queryStudentForGrade(String Gid) throws Exception;
    public int delectStudentOne(String id) throws Exception;
    public int delectTeacherOne(String id) throws Exception;
    public int InsertTeacher(Teacher teacher) throws Exception;
    public int InsertStudent(Student student) throws Exception;

}
