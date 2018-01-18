package cn.pc.exam.service;

import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojo.Grade;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;

import java.util.List;

public interface TeacherManagerService {

    public int updatePassword(TeacherExtend teacherExtend) throws Exception;
    //获取某个老师教授的课程名称 和班级
    public List<Course> selectTcAndCourse(String Tid) throws Exception;
    public List<Grade> selectTcAndGrade(String Tid) throws Exception;
    public List<StudentExtend> selectStudent(String Cid, String Gid)throws Exception;
    public int insertScore(float score,String Sid,String Cid) throws Exception;
}
