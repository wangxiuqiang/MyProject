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

    /**
     * 查询所有的用户
     * @return
     * @throws Exception
     */
    public List<User> findAllWorker() throws Exception;

    /**
     * 查找指定的用户,根据id
     * @param id
     * @return
     * @throws Exception
     */
    public User findWorkerById(int id) throws Exception;

    /**
     * 删除一个用户 根据id
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteWorkerById(int id) throws Exception;

    /**
     * 根据id进行修改用户信息
     */
    public int updateWorkerById(User user) throws Exception;
}
