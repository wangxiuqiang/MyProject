package cn.pc.exam.service;

import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Teacher;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;

import java.util.List;

public interface AdminManagerService {
    /**
     * 单个查询学生或老师的信息  ,通过参数id
     * @return
     */
    public TeacherExtend queryTeacherForOne(String id) throws Exception;
    public StudentExtend queryStudentForOne(String id) throws Exception;
    public List<TeacherExtend> queryAllTeacher() throws  Exception;
    public List<StudentExtend> queryAllStudent() throws Exception;
    public StudentExtend queryStudentForGrade(String Gid) throws Exception;

    public int delectStudentOne(String id) throws Exception;
    public int delectTeacherOne(String id) throws Exception;

    public int insertStudent(Student student) throws Exception;
    public int insertTeacher(Teacher teacher) throws Exception;


}
