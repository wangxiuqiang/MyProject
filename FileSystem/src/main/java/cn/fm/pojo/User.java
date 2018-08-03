package cn.fm.pojo;

public class User {
    private int  uid;
    private String uname;
    private String upwd;
    private String ucompany;
    private String uupdatetime;
    private int state;
    private String uemail;
    private String code;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }

    public String getUcompany() {
        return ucompany;
    }

    public void setUcompany(String ucompany) {
        this.ucompany = ucompany;
    }

    public String getUupdatetime() {
        return uupdatetime;
    }

    public void setUupdatetime(String uupdatetime) {
        this.uupdatetime = uupdatetime;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
