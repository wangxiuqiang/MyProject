package cn.fm.admin.dao;

import cn.fm.pojo.*;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface AdminMapper {

//    /**
//     * 查询管理员,进行登录
//     */
//   public Admin selectAdmin(String aname) throws Exception;

//    /**
//     *  录入一个管理员
//     */
//    public int addAdmin(Admin admin) throws Exception;

    /**
     * 用于登录
     */

    public User findUserByEmail(String uemail) throws Exception;
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

    /**
     * 查找全部角色,用来在添加用户的时候设置角色
     * @return
     * @throws Exception
     */
    public List<Role> selectAllRoles() throws Exception;

    /**
     * 查找全部的权限,在添加用户的时候设置
     * @return
     * @throws Exception
     */
    public List<Permission> selectAllPermissions() throws Exception;
    /**
     * 根据uid ,在关联表中查出角色id
     * @param uid
     * @return
     * @throws Exception
     */
    public int selectRid(int uid) throws Exception;

    /**
     * 根据rid查找角色的名称
     * @param rid
     * @return
     * @throws Exception
     */
    public Set<String> selectRoles(int rid) throws Exception;

    /**
     * 根据角色id在关联表里查出相应的权限id
     * @param rid
     * @return
     * @throws Exception
     */
    public int[] selectPids(int rid) throws Exception;

    /**
     * 根据权限id的数组查找权限
     * @param list
     * @return
     * @throws Exception
     */
    public Set<String> selectPermissions(@Param(value = "list") int[] list) throws Exception;
}
