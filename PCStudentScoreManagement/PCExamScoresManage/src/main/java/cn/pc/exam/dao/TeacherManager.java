package cn.pc.exam.dao;

import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojo.Tc;
import cn.pc.exam.pojoExtends.TeacherExtend;

import java.util.List;

public interface TeacherManager {
    public int updatePassword(TeacherExtend teacherExtend) throws Exception;

    public List<Tc> selectTcWhereTid(String Tid) throws Exception;
    //通过Tc中的courseId
    public List<Course> selectCourse(String[] array) throws Exception;


}
