package cn.pc.exam.dao;

import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojoExtends.StudentExtend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface StudentManager {
    public int updatePassword(String password,String Sid) throws Exception;
    public StudentExtend selectStudent(String Sid) throws Exception;

    public List<Course> selectCourses(String Sid ) throws Exception;
    public Float selectScore(String Cid,String Sid) throws Exception;
}
