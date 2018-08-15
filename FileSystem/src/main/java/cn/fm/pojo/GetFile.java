package cn.fm.pojo;

import javax.validation.constraints.NotNull;

public class GetFile {

    /**
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
    @NotNull(message = "{getfile.company.isnull}")
    private String gfcompany;
    private String gfresult;
    private int gfclassifyid;
    @NotNull(message = "{getfile.classifyname.isnull}")
    private String gfclassifyname;
    private String gfaddress;
    private String gfdatetime;
    @NotNull(message = "{getfile.number.isnull}")
    private int gfnumber;
    /**
     * 是不是借出
     */
    private int isborrow;

    public int getIsborrow() {
        return isborrow;
    }

    public void setIsborrow(int isborrow) {
        this.isborrow = isborrow;
    }

    @Override
    public String toString() {
        return "GetFile{" +
                "gfid=" + gfid +
                ", gfname='" + gfname + '\'' +
                ", gfidea='" + gfidea + '\'' +
                ", gfloader='" + gfloader + '\'' +
                ", gfcompany='" + gfcompany + '\'' +
                ", gfresult='" + gfresult + '\'' +
                ", gfclassifyid=" + gfclassifyid +
                ", gfclassifyname='" + gfclassifyname + '\'' +
                ", gfaddress='" + gfaddress + '\'' +
                ", gfdatetime='" + gfdatetime + '\'' +
                ", gfnumber=" + gfnumber +
                '}';
    }


    public int getGfnumber() {
        return gfnumber;
    }

    public void setGfnumber(int gfnumber) {
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

    public int getGfclassifyid() {
        return gfclassifyid;
    }

    public void setGfclassifyid(int gfclassifyid) {
        this.gfclassifyid = gfclassifyid;
    }

    public String getGfclassifyname() {
        return gfclassifyname;
    }

    public void setGfclassifyname(String gfclassifyname) {
        this.gfclassifyname = gfclassifyname;
    }

    public String getGfaddress() {
        return gfaddress;
    }

    public void setGfaddress(String gfaddress) {
        this.gfaddress = gfaddress;
    }

    public String getGfdatetime() {
        return gfdatetime;
    }

    public void setGfdatetime(String gfdatetime) {
        this.gfdatetime = gfdatetime;
    }
}
