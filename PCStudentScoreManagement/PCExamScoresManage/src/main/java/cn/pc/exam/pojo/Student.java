package cn.pc.exam.pojo;

import javax.validation.constraints.Size;

public class Student {
    @Size(min = 1,max = 15,message = "{}")
    private String Sid;

    private String Sname;

    private String SGid;

    private String SEid;

    private String Spassword;

    public String getSpassword() {
        return Spassword;
    }

    public void setSpassword(String spassword) {
        Spassword = spassword;
    }



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
