package cn.pc.exam.pojoExtends;

import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojo.Grade;
import cn.pc.exam.pojo.Tc;

import java.util.List;

public class TeacherExtend {
    private String Tid;
    private String Tname;
    private String Tpassword;
    private String newPassword; //自己改密码的时候的,确认密码
    //定义 list来接收查出来的list
    private List<Tc>  tcList ;
    private List<Course> courseList;
    private List<Grade> gradeList;

    public List<Grade> getGradeList() {
        return gradeList;
    }

    public void setGradeList(List<Grade> gradeList) {
        this.gradeList = gradeList;
    }

    public List<Tc> getTcList() {
        return tcList;
    }

    public void setTcList(List<Tc> tcList) {
        this.tcList = tcList;
    }

    public List<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(List<Course> courseList) {
        this.courseList = courseList;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }




    public String getTid() {
        return Tid;
    }

    public void setTid(String tid) {
        Tid = tid;
    }

    public String getTname() {
        return Tname;
    }

    public void setTname(String tname) {
        Tname = tname;
    }

    public String getTpassword() {
        return Tpassword;
    }

    public void setTpassword(String tpassword) {
        Tpassword = tpassword;
    }
}
