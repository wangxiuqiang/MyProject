package cn.fm.vo;

import cn.fm.pojo.CompanyFile;

import java.util.List;

public class CompanyFileExtends {

    /**
     * 添加了 一个状态位 ,一个数值总和,状态位用来确保这个查询是不是成功
     */
    private int state;
    private int count;
    private List<CompanyFile> companyFiles;

    public List<CompanyFile> getCompanyFiles() {
        return companyFiles;
    }

    public void setCompanyFiles(List<CompanyFile> companyFiles) {
        this.companyFiles = companyFiles;
    }
    public int getState() {
        return state;
    }



    public void setState(int state) {
        this.state = state;

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
