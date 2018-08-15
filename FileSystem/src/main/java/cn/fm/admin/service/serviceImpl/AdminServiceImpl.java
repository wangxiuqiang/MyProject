package cn.fm.admin.service.serviceImpl;

import cn.fm.admin.service.AdminService;
import cn.fm.admin.dao.AdminMapper;
import cn.fm.pojo.*;
import cn.fm.utils.MD5Utils;
import cn.fm.utils.MailUtils;
import cn.fm.vo.UserExtend;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminMapper adminMapper;

//    /***
//     * 查找admin用来进行登录
//     * @param admin
//     * @return
//     * @throws Exception
//     */
//    @Override
//    public Admin selectAdmin(Admin admin) throws Exception {
//        return adminMapper.selectAdmin(admin.getAname());
//    }

//    @Override
//    public int addAdmin(Admin admin) throws Exception {
//        return adminMapper.addAdmin(admin);
//    }

    @Override
    public User findUserByEmail(String uemail) throws Exception {
        return adminMapper.findUserByEmail(uemail);
    }

    /**
     * 插入一个用户
     * @return
     */
    @Override
    public int addUser(UserExtend user) throws Exception {
        user.setCode(UUID.randomUUID().toString());
//        MailUtils.sendMail(user.getCode(),user.getUemail());
       int uid = adminMapper.addUser(user);
       System.out.println(uid);
       System.out.println(user.getUid());
       return adminMapper.addUser_Role(user.getUid(),user.getRid()) ;
    }

    /**
     * 查找工作单位 全部
     * @return
     * @throws Exception
     */
    @Override
    public List<WorkPlace>  selectWorkPlace() throws Exception {
        return adminMapper.selectWorkPlace();
    }

    /**
     * 查找用户是否存在  使用email
     * @param uemail
     * @return
     */
    @Override
    public String selectEmailIfExist(String uemail) throws  Exception{
        return adminMapper.selectEmailIfExist(uemail);
    }

    /**
     * 查询所有的用户
     * @return
     * @throws Exception
     */
    public List<User> findAllWorker() throws Exception{
        return adminMapper.findAllWorker();
    }

    /**
     * 查找指定的用户,根据id
     * @param id
     * @return
     * @throws Exception
     */
    public User findWorkerById(int id) throws Exception{
        return adminMapper.findWorkerById(id);
    }

    /**
     * 删除一个用户 根据id
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteWorkerById(int id) throws Exception{
        return adminMapper.deleteWorkerById(id);
    }

    /**
     * 根据id进行修改用户信息
     */
    public int updateWorkerById(User user) throws Exception{
        return adminMapper.updateWorkerById(user);
    }

    /**
     * 查找全部角色,用来在添加用户的时候设置角色
     * @return
     * @throws Exception
     */
    public List<Role> selectAllRoles() throws Exception{
        return adminMapper.selectAllRoles();
    }

    /**
     * 查找全部的权限,在添加用户的时候设置
     * @return
     * @throws Exception
     */
    public List<Permission> selectAllPermissions() throws Exception{
        return adminMapper.selectAllPermissions();
    }

    /**
     * 根据用户查角色和权限,写到shiro里面
     */

    /**
     * 根据uid ,在关联表中查出角色id
     * @param uid
     * @return
     * @throws Exception
     */
    public int selectRid(int uid) throws Exception{
        return adminMapper.selectRid(uid);
    }

    /**
     * 根据rid查找角色的名称
     * @param rid
     * @return
     * @throws Exception
     */
    public Set<String> selectRoles(int rid) throws Exception {
        return adminMapper.selectRoles(rid);
    }

    /**
     * 根据角色id在关联表里查出相应的权限id
     * @param rid
     * @return
     * @throws Exception
     */
    public int[] selectPids(int rid) throws Exception{
        return adminMapper.selectPids(rid);
    }

    /**
     * 根据权限id的数组查找权限
     * @param list
     * @return
     * @throws Exception
     */
    public Set<String> selectPermissions( int[] list) throws Exception {
        return adminMapper.selectPermissions(list);
    }

    /**
     * 将上面的几个函数整合一下查出一个全的来
     * @param uid
     * @return
     * @throws Exception
     */
    public Set<String> findPermissions(int uid) throws Exception {
         return selectPermissions(selectPids(selectRid(uid)));
    }
    /**
     * 将上面的几个函数整合一下查出一个全的来Role
     * @param uid
     * @return
     * @throws Exception
     */
    public Set<String> findRoles(int uid) throws Exception {
        return selectRoles(selectRid(uid));
    }
}
