package cn.pc.exam.service;

import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojo.Grade;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojoExtends.TeacherExtend;

import java.util.List;

public interface TeacherManagerService {

    public int updatePassword(TeacherExtend teacherExtend) throws Exception;
    //获取某个老师教授的课程名称 和班级
    public List<Course> selectTcAndCourse(String Tid) throws Exception;
    public List<Grade> selectTcAndGrade(String Tid) throws Exception;
    public List<Student> selectStudent(String Cid,String Gid)throws Exception;

}
