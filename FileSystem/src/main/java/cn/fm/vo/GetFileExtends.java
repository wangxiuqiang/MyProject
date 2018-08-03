package cn.fm.vo;

import cn.fm.pojo.GetFile;

import java.util.List;

public class GetFileExtends {

    private int state;
    private int count;

    private List<GetFile> getFiles;

    public List<GetFile> getGetFiles() {
        return getFiles;
    }

    public void setGetFiles(List<GetFile> getFiles) {
        this.getFiles = getFiles;
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
