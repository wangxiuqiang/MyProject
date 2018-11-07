package cn.graduate.subject.pojo;

public class Subject {
    private int sid;
    private String sname;
    private String sintroduce;
    //选择这个题目的人数
    private int count;
    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSintroduce() {
        return sintroduce;
    }

    public void setSintroduce(String sintroduce) {
        this.sintroduce = sintroduce;
    }
}
