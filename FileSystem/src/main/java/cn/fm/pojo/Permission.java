package cn.fm.pojo;

/**
 * 权限类
 */
public class Permission {
    private int pid;
    private String pname;
    private String pdescribe;

    public int getPid() {
        return pid;
    }

    public void setPid(int pid) {
        this.pid = pid;
    }

    public String getPname() {
        return pname;
    }

    public void setPname(String pname) {
        this.pname = pname;
    }

    public String getPdescribe() {
        return pdescribe;
    }

    public void setPdescribe(String pdescribe) {
        this.pdescribe = pdescribe;
    }
}
