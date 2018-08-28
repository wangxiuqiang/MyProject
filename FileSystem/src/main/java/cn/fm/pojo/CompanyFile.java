package cn.fm.pojo;

import javax.validation.constraints.NotNull;
import java.util.List;

public class CompanyFile {

    /**
     * 用来区分两种文件 type = 1
     */
    private int type;
    /**
     * 是不是借出  ,1 没有借出, 2 借出
     */
    private int isborrow;

    private int cfid;
    @NotNull(message = "{companyfile.name.isnull}")
    private String cfname;
    private String cfdate;
    @NotNull(message = "{companyfile.accept.isnull}")
    private String cfaccept;
    @NotNull(message = "{companyfile.editor.isnull}")
    private String cfeditor;
    @NotNull(message = "{companyfile.send.isnull}")
    private String cfsend;
    @NotNull(message = "{companyfile.level.isnull}")
    private String cflevel;
    private String cfloader;
    @NotNull(message = "{companyfile.number.isnull}")
    private int cfnumber;
    @NotNull(message = "{companyfile.fontid.isnull}")
    private String cffontid;
    @NotNull(message = "{companyfile.language.isnull}")
    private String cflanguage;
    private String cfaddress;
    private int cfclassifyid;
    @NotNull(message = "{companyfile.classifyname.isnull}")
    private String cfclassifyname;

    /**
     * 用来 返回自己的分类和全部父类id
     * */
    private List<Integer> classifies;

    public List<Integer> getClassifies() {
        return classifies;
    }

    public void setClassifies(List<Integer> classifies) {
        this.classifies = classifies;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public int getIsborrow() {
        return isborrow;
    }

    public void setIsborrow(int isborrow) {
        this.isborrow = isborrow;
    }
    public int getCfid() {
        return cfid;
    }

    public void setCfid(int cfid) {
        this.cfid = cfid;
    }

    public String getCfname() {
        return cfname;
    }

    public void setCfname(String cfname) {
        this.cfname = cfname;
    }

    public String getCfdate() {
        return cfdate;
    }

    public void setCfdate(String cfdate) {
        this.cfdate = cfdate;
    }

    public String getCfaccept() {
        return cfaccept;
    }

    public void setCfaccept(String cfaccept) {
        this.cfaccept = cfaccept;
    }

    public String getCfeditor() {
        return cfeditor;
    }

    public void setCfeditor(String cfeditor) {
        this.cfeditor = cfeditor;
    }

    public String getCfsend() {
        return cfsend;
    }

    public void setCfsend(String cfsend) {
        this.cfsend = cfsend;
    }

    public String getCflevel() {
        return cflevel;
    }

    public void setCflevel(String cflevel) {
        this.cflevel = cflevel;
    }

    public String getCfloader() {
        return cfloader;
    }

    public void setCfloader(String cfloader) {
        this.cfloader = cfloader;
    }

    public int getCfnumber() {
        return cfnumber;
    }

    public void setCfnumber(int cfnumber) {
        this.cfnumber = cfnumber;
    }

    public String getCffontid() {
        return cffontid;
    }

    public void setCffontid(String cffontid) {
        this.cffontid = cffontid;
    }

    public String getCflanguage() {
        return cflanguage;
    }

    public void setCflanguage(String cflanguage) {
        this.cflanguage = cflanguage;
    }

    public String getCfaddress() {
        return cfaddress;
    }

    public void setCfaddress(String cfaddress) {
        this.cfaddress = cfaddress;
    }

    public int getCfclassifyid() {
        return cfclassifyid;
    }

    public void setCfclassifyid(int cfclassifyid) {
        this.cfclassifyid = cfclassifyid;
    }

    public String getCfclassifyname() {
        return cfclassifyname;
    }

    public void setCfclassifyname(String cfclassifyname) {
        this.cfclassifyname = cfclassifyname;
    }
}
