package cn.fm.admin.dao;

import cn.fm.pojo.Admin;
import cn.fm.pojo.User;
import cn.fm.pojo.WorkPlace;

import java.util.List;

public interface AdminMapper {

    /**
     * 查询管理员,进行登录
     */
    public Admin selectAdmin(Admin admin) throws Exception;

    /**
     * 写入一个用户
     */

    public int addUser(User user) throws Exception;

    /**
     * 查找全部的工作单位
     */
    public List<WorkPlace> selectWorkPlace() throws Exception;

    /**
     * 指定用户的时候判断用户是不是存在,根据email
     * @param uemail
     * @return
     * @throws Exception
     */
    public String  selectEmailIfExist(String uemail) throws Exception;


}
