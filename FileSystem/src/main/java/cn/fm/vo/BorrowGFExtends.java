package cn.fm.vo;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.GetFile;
import cn.fm.pojo.User;

public class BorrowGFExtends {
      private User user;
      private GetFile getFile;
      private String backtime;
      private String borrowtime;
     private String sholdback;
     private String givetime;
    private Borrow borrow;
    private User userSecond;
    private User userThird;

    public User getUserSecond() {
        return userSecond;
    }

    public void setUserSecond(User userSecond) {
        this.userSecond = userSecond;
    }

    public User getUserThird() {
        return userThird;
    }

    public void setUserThird(User userThird) {
        this.userThird = userThird;
    }

    public Borrow getBorrow() {
        return borrow;
    }

    public void setBorrow(Borrow borrow) {
        this.borrow = borrow;
    }

    public String getSholdback() {
        return sholdback;
    }

    public void setSholdback(String sholdback) {
        this.sholdback = sholdback;
    }

    public String getGivetime() {
        return givetime;
    }

    public void setGivetime(String givetime) {
        this.givetime = givetime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public GetFile getGetFile() {
        return getFile;
    }

    public void setGetFile(GetFile getFile) {
        this.getFile = getFile;
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
//     * 用来区分两种文件 此文件type = 2
//     */
//    private int type;
//
//    public int getType() {
//        return type;
//    }
//
//    public void setType(int type) {
//        this.type = type;
//    }
//
//    private int gfid;
//    private String gfname;
//    private String gfidea;
//    private String gfloader;
//    private String gfcompany;
//    private String gfresult;
//    private int gfclassifyid;
//    private String gfclassifyname;
//    private String gfaddress;
//    private String gfdatetime;
//    private int gfnumber;
//    /**
//     * 是不是借出
//     */
//    private int isborrow;
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
//    public int getGfid() {
//        return gfid;
//    }
//
//    public void setGfid(int gfid) {
//        this.gfid = gfid;
//    }
//
//    public String getGfname() {
//        return gfname;
//    }
//
//    public void setGfname(String gfname) {
//        this.gfname = gfname;
//    }
//
//    public String getGfidea() {
//        return gfidea;
//    }
//
//    public void setGfidea(String gfidea) {
//        this.gfidea = gfidea;
//    }
//
//    public String getGfloader() {
//        return gfloader;
//    }
//
//    public void setGfloader(String gfloader) {
//        this.gfloader = gfloader;
//    }
//
//    public String getGfcompany() {
//        return gfcompany;
//    }
//
//    public void setGfcompany(String gfcompany) {
//        this.gfcompany = gfcompany;
//    }
//
//    public String getGfresult() {
//        return gfresult;
//    }
//
//    public void setGfresult(String gfresult) {
//        this.gfresult = gfresult;
//    }
//
//    public int getGfclassifyid() {
//        return gfclassifyid;
//    }
//
//    public void setGfclassifyid(int gfclassifyid) {
//        this.gfclassifyid = gfclassifyid;
//    }
//
//    public String getGfclassifyname() {
//        return gfclassifyname;
//    }
//
//    public void setGfclassifyname(String gfclassifyname) {
//        this.gfclassifyname = gfclassifyname;
//    }
//
//    public String getGfaddress() {
//        return gfaddress;
//    }
//
//    public void setGfaddress(String gfaddress) {
//        this.gfaddress = gfaddress;
//    }
//
//    public String getGfdatetime() {
//        return gfdatetime;
//    }
//
//    public void setGfdatetime(String gfdatetime) {
//        this.gfdatetime = gfdatetime;
//    }
//
//    public int getGfnumber() {
//        return gfnumber;
//    }
//
//    public void setGfnumber(int gfnumber) {
//        this.gfnumber = gfnumber;
//    }
//
//    public int getIsborrow() {
//        return isborrow;
//    }
//
//    public void setIsborrow(int isborrow) {
//        this.isborrow = isborrow;
//    }
}
