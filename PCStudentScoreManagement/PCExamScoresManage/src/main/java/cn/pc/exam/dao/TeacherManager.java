package cn.pc.exam.dao;

import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojo.Grade;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Tc;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;

import java.util.List;

public interface TeacherManager {
    public int updatePassword(TeacherExtend teacherExtend) throws Exception;

    public List<Tc> selectTcWhereTid(String Tid) throws Exception;
    //通过Tc中的courseId
    public List<Course> selectCourse(String[] array) throws Exception;
    //通过Tc中的GradeId找班级信息
    public List<Grade> selectGrade(String[] array) throws Exception;
    //通过Cid和Gid查询学生
    public List<StudentExtend> selectStudentForCidGid(String Cid,String Gid) throws Exception;
}
