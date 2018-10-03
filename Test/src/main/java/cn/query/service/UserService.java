package cn.query.service;

import cn.query.pojo.FileInSystem;

import java.util.List;

public interface UserService {
    /**
     * 通过名字查找一个
     * @param name
     * @return
     * @throws Exception
     */
    public List<FileInSystem> selectFileByName(String name) throws Exception;
}
