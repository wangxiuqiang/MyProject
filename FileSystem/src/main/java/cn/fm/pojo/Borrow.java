package cn.fm.pojo;

public class Borrow {
    private int uid;
    private int fileid;
    private String borrowtime;
    private String backtime;
    private int state;
    private String givetime;
    private String shouldback;
    private int wid;

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

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
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
