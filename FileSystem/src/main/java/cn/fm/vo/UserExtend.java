package cn.fm.vo;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class UserExtend {


    @NotNull(message = "{user.company.isnull}")
    private String ucompany;

//  private String uemail;
//  private String code;

    private int  uid;
    private String uname;
    private String upwd;
//  private String ucompany;
    private String uupdatetime;
    private int state;
    private String uphone;
    private String unumber;
    private int wid;
    private String uworkname;
    private String uaccount;
    //    private String code;
//    private int ifAdmin;


//    @NotNull(message = "{user.ifadmin.isnull}")
    private int ifAdmin;
//    @NotNull(message = "{user.roles.isnull}")
    private int rid;

//    @NotNull(message = "{user.roles.isnull}")
    private String rname;

    private String pname;

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

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

//    public String getUemail() {
//        return uemail;
//    }
//
//    public void setUemail(String uemail) {
//        this.uemail = uemail;
//    }
//
//    public String getCode() {
//        return code;
//    }
//
//    public void setCode(String code) {
//        this.code = code;
//    }


    public String getUphone() {
        return uphone;
    }

    public void setUphone(String uphone) {
        this.uphone = uphone;
    }

    public String getUnumber() {
        return unumber;
    }

    public void setUnumber(String unumber) {
        this.unumber = unumber;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

    public String getUworkname() {
        return uworkname;
    }

    public void setUworkname(String uworkname) {
        this.uworkname = uworkname;
    }

    public String getUaccount() {
        return uaccount;
    }

    public void setUaccount(String uaccount) {
        this.uaccount = uaccount;
    }

    public int getIfAdmin() {
        return ifAdmin;
    }

    public void setIfAdmin(int ifAdmin) {
        this.ifAdmin = ifAdmin;
    }

    public int getRid() {
        return rid;
    }

    public void setRid(int rid) {
        this.rid = rid;
    }

    public String getRname() {
        return rname;
    }

    public void setRname(String rname) {
        this.rname = rname;
    }
}
