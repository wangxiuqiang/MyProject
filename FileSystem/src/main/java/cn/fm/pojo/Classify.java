package cn.fm.pojo;

public class Classify {

    /**
     * 子含父, 设置最后一个子分类,然后一级级找到父分类,,完成分类的选择
     */

    private int cyid;
    private String cyname;
    private int cyfather;
    private String cyaddress;

    public int getCyid() {
        return cyid;
    }

    public void setCyid(int cyid) {
        this.cyid = cyid;
    }

    public String getCyname() {
        return cyname;
    }

    public void setCyname(String cyname) {
        this.cyname = cyname;
    }

    public int getCyfather() {
        return cyfather;
    }

    public void setCyfather(int cyfather) {
        this.cyfather = cyfather;
    }

    public String getCyaddress() {
        return cyaddress;
    }

    public void setCyaddress(String cyaddress) {
        this.cyaddress = cyaddress;
    }
}
