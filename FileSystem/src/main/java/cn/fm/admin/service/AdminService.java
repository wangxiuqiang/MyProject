package cn.fm.admin.service;

import cn.fm.pojo.*;
import cn.fm.vo.UserExtend;

import java.util.List;
import java.util.Set;

public interface AdminService {
//    /***
//     * 查找admin 用来进行登录
//     * @param admin
//     * @return
//     * @throws Exception
//     */
//    public Admin selectAdmin(Admin admin) throws Exception;
//    /**
//     *  录入一个管理员
//     */
//    public int addAdmin(Admin admin) throws Exception;

    /**
     *  用于登录
     */

    public User findUserByEmail(String uemail) throws Exception;
    /**
     * 插入一个用户
     */
    public int addUser(UserExtend user) throws Exception;


    /**
     * 查找全部的工作单位
     */
    public List<WorkPlace> selectWorkPlace() throws Exception;

    /**
     * 查找 用户是否存在
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
    public UserExtend findWorkerById(int id) throws Exception;

    /**
     * 删除一个用户 根据id
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteWorkerById(int[] id) throws Exception;

    /**
     * 根据id进行修改用户信息
     */
    public int updateWorkerById(User user) throws Exception;
/**
 * 根据id更改用户角色
 */
   public int  updateUser_Role(int uid,int rid) throws Exception;

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
    public int[] selectRid(int uid) throws Exception;

    /**
     * 根据rid查找角色的名称
     * @param rid
     * @return
     * @throws Exception
     */
    public Set<String> selectRoles(int[] rid) throws Exception;

    /**
     * 根据角色id在关联表里查出相应的权限id
     * @param rid
     * @return
     * @throws Exception
     */
    public int[] selectPids(int[] rid) throws Exception;

    /**
     * 根据权限id的数组查找权限
     * @param list
     * @return
     * @throws Exception
     */
    public Set<String> selectPermissions(int[] list) throws Exception;
    /**
     * 将上面的几个函数整合一下查出一个全的来
     * @param uid
     * @return
     * @throws Exception
     */
    public Set<String> findPermissions(int uid) throws Exception;
    /**
     * 将上面的几个函数整合一下查出一个全的来Role
     * @param uid
     * @return
     * @throws Exception
     */
    public Set<String> findRoles(int uid) throws Exception;

    /**
     * 展示给前端的权限和角色信息
     */
     public List<Role> findRolesShow(int[] rids) throws Exception;
    public List<Permission> findPermissionsShow(int[] pids) throws Exception;

    /**
     * 添加用户单位
     * @param workPlace
     * @return
     * @throws Exception
     */
    public int insertCompany(WorkPlace workPlace) throws Exception;
    /**
     * 删除一个 用户单位
     * @param wid
     * @return
     * @throws Exception
     */
    public int delCompany( int wid ) throws Exception;

    /**
     * 更新一个用户单位
     * @param workPlace
     * @return
     * @throws Exception
     */
    public int updateCompany ( WorkPlace workPlace ) throws Exception;
    /**
     * 添加分类
     * @param classify
     * @return
     * @throws Exception
     */
    public int insertClassify(Classify classify) throws Exception;
    /**
     * 根据id更新一个分类
     * @param classify
     * @return
     * @throws Exception
     */
    public int updateClassify( Classify classify ) throws  Exception;

    /**
     * 根据id删除一个分类,最终分类 ,
     * @param cyid
     * @return
     * @throws Exception
     */
    public int delClassify( int cyid ) throws Exception;

    /**
     * 添加密级
     */
    public int addLevelInfo(String lname) throws Exception;
    /**
     * 删除一个密级, 根据id
     * @param lid
     * @return
     * @throws Exception
     */
    public int delLevelInfo( int lid ) throws Exception;

    /**
     * 更新一个密级
     * @param level
     * @return
     * @throws Exception
     */
    public int updateLevelInfo ( Level level ) throws Exception;
    /**
     * 查找密级
     */
    public List<Level> selectAllLevel() throws Exception;
    /**
     * 添加指纹信息
     * @param fingerprint
     * @return
     * @throws Exception
     */
    public int addFingerInfo(Fingerprint fingerprint) throws Exception;
    /**
     * 根据用户id删除指纹信息
     * @param uid
     * @return
     * @throws Exception
     */
    public int delFingerInfoByUid( int[] uid ) throws Exception;
    /**
     * 从数据库中取出所有的指纹信息,并进行比较,返回一个用户对象,如果为空则没有这个人
     * @return
     * @throws Exception
     */
    public User selectAllFingerInfoAndCompare( String finger) throws Exception;
}
