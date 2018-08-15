package cn.fm.vo;

import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.pojo.User;

import java.util.List;

public class BorrowCFExtends {

    private User user;
    private CompanyFile companyFile;
    private String backtime;
    private String borrowtime;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public CompanyFile getCompanyFile() {
        return companyFile;
    }

    public void setCompanyFile(CompanyFile companyFile) {
        this.companyFile = companyFile;
    }

    public String getBacktime() {
        return backtime;
    }

    public void setBacktime(String backtime) {
        this.backtime = backtime;
    }

    public String getBorrowtime() {
        return borrowtime;
    }

    public void setBorrowtime(String borrowtime) {
        this.borrowtime = borrowtime;
    }
    //    private int uid;
//    private int fileid;
//
//    private String borrowtime;
//    private String backtime;
//
//    private String ucompany;
//    private String uname;
//    /**
//     * companyFile 属性
//     */
//    /**
//     * 用来区分两种文件 type = 1
//     */
//    private int type;
//    /**
//     * 是不是借出  ,1 没有借出, 2 借出
//     */
//    private int isborrow;
//
//    private int cfid;
//    private String cfname;
//    private String cfdate;
//    private String cfaccept;
//    private String cfeditor;
//    private String cfsend;
//    private String cflevel;
//    private String cfloader;
//    private int cfnumber;
//    private String cffontid;
//    private String cflanguage;
//    private String cfaddress;
//    private int cfclassifyid;
//    private String cfclassifyname;
//
//    public int getUid() {
//        return uid;
//    }
//
//    public void setUid(int uid) {
//        this.uid = uid;
//    }
//
//    public int getFileid() {
//        return fileid;
//    }
//
//    public void setFileid(int fileid) {
//        this.fileid = fileid;
//    }
//
//    public String getBorrowtime() {
//        return borrowtime;
//    }
//
//    public void setBorrowtime(String borrowtime) {
//        this.borrowtime = borrowtime;
//    }
//
//    public String getBacktime() {
//        return backtime;
//    }
//
//    public void setBacktime(String backtime) {
//        this.backtime = backtime;
//    }
//
//    public String getUcompany() {
//        return ucompany;
//    }
//
//    public void setUcompany(String ucompany) {
//        this.ucompany = ucompany;
//    }
//
//    public String getUname() {
//        return uname;
//    }
//
//    public void setUname(String uname) {
//        this.uname = uname;
//    }
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    public int getIsborrow() {
//        return isborrow;
//    }
//
//    public void setIsborrow(int isborrow) {
//        this.isborrow = isborrow;
//    }
//
//    public int getCfid() {
//        return cfid;
//    }
//
//    public void setCfid(int cfid) {
//        this.cfid = cfid;
//    }
//
//    public String getCfname() {
//        return cfname;
//    }
//
//    public void setCfname(String cfname) {
//        this.cfname = cfname;
//    }
//
//    public String getCfdate() {
//        return cfdate;
//    }
//
//    public void setCfdate(String cfdate) {
//        this.cfdate = cfdate;
//    }
//
//    public String getCfaccept() {
//        return cfaccept;
//    }
//
//    public void setCfaccept(String cfaccept) {
//        this.cfaccept = cfaccept;
//    }
//
//    public String getCfeditor() {
//        return cfeditor;
//    }
//
//    public void setCfeditor(String cfeditor) {
//        this.cfeditor = cfeditor;
//    }
//
//    public String getCfsend() {
//        return cfsend;
//    }
//
//    public void setCfsend(String cfsend) {
//        this.cfsend = cfsend;
//    }
//
//    public String getCflevel() {
//        return cflevel;
//    }
//
//    public void setCflevel(String cflevel) {
//        this.cflevel = cflevel;
//    }
//
//    public String getCfloader() {
//        return cfloader;
//    }
//
//    public void setCfloader(String cfloader) {
//        this.cfloader = cfloader;
//    }
//
//    public int getCfnumber() {
//        return cfnumber;
//    }
//
//    public void setCfnumber(int cfnumber) {
//        this.cfnumber = cfnumber;
//    }
//
//    public String getCffontid() {
//        return cffontid;
//    }
//
//    public void setCffontid(String cffontid) {
//        this.cffontid = cffontid;
//    }
//
//    public String getCflanguage() {
//        return cflanguage;
//    }
//
//    public void setCflanguage(String cflanguage) {
//        this.cflanguage = cflanguage;
//    }
//
//    public String getCfaddress() {
//        return cfaddress;
//    }
//
//    public void setCfaddress(String cfaddress) {
//        this.cfaddress = cfaddress;
//    }
//
//    public int getCfclassifyid() {
//        return cfclassifyid;
//    }
//
//    public void setCfclassifyid(int cfclassifyid) {
//        this.cfclassifyid = cfclassifyid;
//    }
//
//    public String getCfclassifyname() {
//        return cfclassifyname;
//    }
//
//    public void setCfclassifyname(String cfclassifyname) {
//        this.cfclassifyname = cfclassifyname;
//    }
}
