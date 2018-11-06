package cn.graduate.subject.pojo;

public class User {
    private int uid;
    private int sid;//选题的编号
    private int pid;//进度的编号
    private int rid;//权限id;
    private int uage; //年龄
    private int ucollege; // 专业id
    private int ugrage; //班级id
    private int ifAdmin;

    private String uaccount;//学号账号,用来登录
    private String uname;
    private String uemail;
    private String usex; //只有男 女俩个值
    private String upwd;

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public int getUage() {
        return uage;
    }

    public void setUage(int uage) {
        this.uage = uage;
    }

    public int getUcollege() {
        return ucollege;
    }

    public void setUcollege(int ucollege) {
        this.ucollege = ucollege;
    }

    public int getUgrage() {
        return ugrage;
    }

    public void setUgrage(int ugrage) {
        this.ugrage = ugrage;
    }

    public int getIfAdmin() {
        return ifAdmin;
    }

    public void setIfAdmin(int ifAdmin) {
        this.ifAdmin = ifAdmin;
    }

    public String getUaccount() {
        return uaccount;
    }

    public void setUaccount(String uaccount) {
        this.uaccount = uaccount;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public String getUpwd() {
        return upwd;
    }

    public void setUpwd(String upwd) {
        this.upwd = upwd;
    }
}
