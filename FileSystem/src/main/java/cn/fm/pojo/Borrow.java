package cn.fm.pojo;

public class Borrow {
    private int uid;
    private int fileid;
    private String borrowtime;
    private String backtime;
    private int borrowstate;
    private String givetime;
    private String shouldback;
    private int wid;
    private int secondUid;
    private int thirdUid;

    public int getThirdUid() {
        return thirdUid;
    }

    public void setThirdUid(int thirdUid) {
        this.thirdUid = thirdUid;
    }

    public int getBorrowstate() {
        return borrowstate;
    }

    public void setBorrowstate(int borrowstate) {
        this.borrowstate = borrowstate;
    }

    public int getSecondUid() {
        return secondUid;
    }

    public void setSecondUid(int secondUid) {
        this.secondUid = secondUid;
    }

    public String getGivetime() {
        return givetime;
    }

    public void setGivetime(String givetime) {
        this.givetime = givetime;
    }

    public String getShouldback() {
        return shouldback;
    }

    public void setShouldback(String shouldback) {
        this.shouldback = shouldback;
    }

    public int getWid() {
        return wid;
    }

    public void setWid(int wid) {
        this.wid = wid;
    }

   
    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFileid() {
        return fileid;
    }

    public void setFileid(int fileid) {
        this.fileid = fileid;
    }

    public String getBorrowtime() {
        return borrowtime;
    }

    public void setBorrowtime(String borrowtime) {
        this.borrowtime = borrowtime;
    }

    public String getBacktime() {
        return backtime;
    }

    public void setBacktime(String backtime) {
        this.backtime = backtime;
    }
}
