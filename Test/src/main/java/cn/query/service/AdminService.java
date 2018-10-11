package cn.query.service;

import cn.query.pojo.Admin;
import cn.query.pojo.FileInSystem;

public interface AdminService {
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

    /**
     * 删除一记录
     * @param id
     * @return
     * @throws Exception
     */
    public int delFile( int id ) throws Exception ;

    /**
     * 更新一个记录
     * @param fileInSystem
     * @return
     * @throws Exception
     */
    public int updateFile( FileInSystem fileInSystem ) throws Exception;
}
