package cn.query.dao;

import cn.query.pojo.Admin;
import cn.query.pojo.FileInSystem;

public interface AdminDao {
    /**
     * 查找admin进行登录
     * @param name
     * @return
     * @throws Exception
     */
    public Admin selectAdmin (String name) throws  Exception;

    /**
     * 录入一个记录
     * @param fileInSystem
     * @return
     * @throws Exception
     */
    public int insertFile(FileInSystem fileInSystem) throws Exception;


}
