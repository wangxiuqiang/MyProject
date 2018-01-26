package cn.pc.exam.service;

import cn.pc.exam.pojo.Course;

import java.util.List;

public interface StudentManagerService {

    public int  updatePassword(String firstStudent,String password,String Sid) throws Exception;
    public List<Course> selectCourses(String Sid) throws Exception;
    public Float selectScore(String Cid,String Sid) throws Exception;
}
