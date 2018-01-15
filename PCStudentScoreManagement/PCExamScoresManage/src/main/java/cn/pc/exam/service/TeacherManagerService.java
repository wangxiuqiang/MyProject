package cn.pc.exam.service;

import cn.pc.exam.pojoExtends.TeacherExtend;

public interface TeacherManagerService {

    public int updatePassword(TeacherExtend teacherExtend) throws Exception;
    //获取某个老师教授的课程名称
    public void selectTcAndCourse(String Tid) throws Exception;
}
