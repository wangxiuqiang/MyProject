package cn.pc.exam.pojoExtends;

import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojo.Grade;
import cn.pc.exam.pojo.Mark;
import cn.pc.exam.pojo.Sc;

import java.util.List;

public class StudentExtend {
    private String Sid;
    private String Sname;
    private String SGid;
    private String SEid;
    private Mark mark;
    private Sc sc;
    private Course course;
    private Grade grade;

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public Sc getSc() {
        return sc;
    }

    public void setSc(Sc sc) {
        this.sc = sc;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Mark getMark() {
        return mark;
    }

    public void setMark(Mark mark) {
        this.mark = mark;
    }

    public String getSpassword() {
        return Spassword;
    }

    public void setSpassword(String spassword) {
        Spassword = spassword;
    }

    private String Spassword;

    public String getSid() {
        return Sid;
    }

    public void setSid(String sid) {
        Sid = sid;
    }

    public String getSname() {
        return Sname;
    }

    public void setSname(String sname) {
        Sname = sname;
    }

    public String getSGid() {
        return SGid;
    }

    public void setSGid(String SGid) {
        this.SGid = SGid;
    }

    public String getSEid() {
        return SEid;
    }

    public void setSEid(String SEid) {
        this.SEid = SEid;
    }
}
