package cn.fm.pojo;

import javax.validation.constraints.NotNull;
import java.util.List;

public class GetFile {

    /**共19个字段
     * 用来区分两种文件 此文件type = 2
     */
    private int type;

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    private int gfid;
    @NotNull(message = "{getfile.name.isnull}")
    private String gfname;
    private String gfidea;
    private String gfloader;
    //来文单位
    private String gffrom;
    //保密密级
    private String gflevel;
    //保密期限
    private String gettime;
    @NotNull(message = "{getfile.company.isnull}")
    //承办单位
    private String gfcompany;
    //份号,文件字号
    private String gfpartnumber;
    //传阅人
    private String gfpersonRead;
    //用来保存传阅人的用户id
    private int[] gfpersonReadIds;
    //用来保存传阅人的姓名
    private String[] gfpersonReadNames;
    private String gfresult;
//    private int gfclassifyid;
//    @NotNull(message = "{getfile.classifyname.isnull}")
//    private String gfclassifyname;
//    private String gfaddress;
    private String gfdatetime;
    @NotNull(message = "{getfile.number.isnull}")
    //收文号
    private String gfnumber;

    /**
     * 是不是借出
     */
    private int isborrow;
    //是否删除
    private int state;
//清退字段
    private int back;
    //等待被领取
    private int waitborrow;
    //清退时间
    private String backDate;

    public int getWaitborrow() {
        return waitborrow;
    }

    public void setWaitborrow(int waitborrow) {
        this.waitborrow = waitborrow;
    }

    public String getGffrom() {
        return gffrom;
    }

    public String getGfpartnumber() {
        return gfpartnumber;
    }

    public void setGfpartnumber(String gfpartnumber) {
        this.gfpartnumber = gfpartnumber;
    }

    public String getGfpersonRead() {
        return gfpersonRead;
    }

    public void setGfpersonRead(String gfpersonRead) {
        this.gfpersonRead = gfpersonRead;
    }

    public String getBackDate() {
        return backDate;
    }

    public void setBackDate(String backDate) {
        this.backDate = backDate;
    }

    public void setGffrom(String gffrom) {
        this.gffrom = gffrom;
    }

    public String getGflevel() {
        return gflevel;
    }

    public void setGflevel(String gflevel) {
        this.gflevel = gflevel;
    }

    public String getGettime() {
        return gettime;
    }

    public void setGettime(String gettime) {
        this.gettime = gettime;
    }

    public int getBack() {
        return back;
    }

    public void setBack(int back) {
        this.back = back;
    }
//    /**
//     * 所在的分类的所有id地址
//     */
//    /**
//     * 用来 返回自己的分类和全部父类id
//     * */
//    private List<Integer> classifies;
//
//    public List<Integer> getClassifies() {
//        return classifies;
//    }
//
//    public void setClassifies(List<Integer> classifies) {
//        this.classifies = classifies;
//    }
    public int getIsborrow() {
        return isborrow;
    }

    public void setIsborrow(int isborrow) {
        this.isborrow = isborrow;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String[] getGfpersonReadNames() {
        return gfpersonReadNames;
    }

    public void setGfpersonReadNames(String[] gfpersonReadNames) {
        this.gfpersonReadNames = gfpersonReadNames;
    }

    public int[] getGfpersonReadIds() {
        return gfpersonReadIds;
    }

    public void setGfpersonReadIds(int[] gfpersonReadIds) {
        this.gfpersonReadIds = gfpersonReadIds;
    }
//    @Override
//    public String toString() {
//        return "GetFile{" +
//                "gfid=" + gfid +
//                ", gfname='" + gfname + '\'' +
//                ", gfidea='" + gfidea + '\'' +
//                ", gfloader='" + gfloader + '\'' +
//                ", gfcompany='" + gfcompany + '\'' +
//                ", gfresult='" + gfresult + '\'' +
//                ", gfclassifyid=" + gfclassifyid +
//                ", gfclassifyname='" + gfclassifyname + '\'' +
//                ", gfaddress='" + gfaddress + '\'' +
//                ", gfdatetime='" + gfdatetime + '\'' +
//                ", gfnumber=" + gfnumber +
//                '}';
//    }


    public String getGfnumber() {
        return gfnumber;
    }

    public void setGfnumber(String gfnumber) {
        this.gfnumber = gfnumber;
    }

    public int getGfid() {
        return gfid;
    }

    public void setGfid(int gfid) {
        this.gfid = gfid;
    }

    public String getGfname() {
        return gfname;
    }

    public void setGfname(String gfname) {
        this.gfname = gfname;
    }

    public String getGfidea() {
        return gfidea;
    }

    public void setGfidea(String gfidea) {
        this.gfidea = gfidea;
    }

    public String getGfloader() {
        return gfloader;
    }

    public void setGfloader(String gfloader) {
        this.gfloader = gfloader;
    }

    public String getGfcompany() {
        return gfcompany;
    }

    public void setGfcompany(String gfcompany) {
        this.gfcompany = gfcompany;
    }

    public String getGfresult() {
        return gfresult;
    }

    public void setGfresult(String gfresult) {
        this.gfresult = gfresult;
    }
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

    public String getGfdatetime() {
        return gfdatetime;
    }

    public void setGfdatetime(String gfdatetime) {
        this.gfdatetime = gfdatetime;
    }
}
