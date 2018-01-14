package cn.pc.exam.pojo;

import javax.validation.constraints.Size;

public class Teacher {
  private  static String beforeTid;

    public static String getBeforeTid() {
        return beforeTid;
    }

    public static void setBeforeTid(String beforeTid) {
        Teacher.beforeTid = beforeTid;
    }

    @Size(min = 1,max = 8,message = "{teacher.id.length.error}")
    private String Tid;

    @Size(min = 1,max = 20,message = "{teacher.name.length.error}")
    private String Tname;

    @Size(min = 5,max = 8,message = "{teacher.password.length.error}")
    private String Tpassword;

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
