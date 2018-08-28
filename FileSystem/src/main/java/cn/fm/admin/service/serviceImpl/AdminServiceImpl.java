package cn.fm.admin.service.serviceImpl;

import cn.fm.admin.service.AdminService;
import cn.fm.admin.dao.AdminMapper;
import cn.fm.pojo.*;
import cn.fm.utils.DateToStringUtils;
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

       user.setUupdatetime(DateToStringUtils.dataTostring());
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
     * 查找指定的用户,根据id  ,返回一个展现给前台页面
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public UserExtend findWorkerById(int id) throws Exception{
        //先获取 这个用户的身份,然后将这个用户的权限和角色取出来,放进增强类,返回
        int[] rids = selectRid(id);
        int[] pids = selectPids(rids);
        List<Role> roles = findRolesShow(rids);
        List<Permission> permissions = findPermissionsShow(pids);
        User user = adminMapper.findWorkerById(id);
        UserExtend ue = setUserExtend(user);

        StringBuffer stringBuffer = new StringBuffer();
        for(Role s : roles){
             stringBuffer.append(s.getRdescribe() + ",");
        }

        ue.setRname(stringBuffer.toString());
        stringBuffer.delete(0,stringBuffer.length());
        for(Permission p : permissions){
              stringBuffer.append(p.getPdescribe() + "-");
        }
        ue.setPname(stringBuffer.toString());
        return ue;
    }
    /**
     * 展示给前端的权限和角色信息 ,展示这个人有多少的角色和信息, 给上面的类,让他使用
     */
    @Override
    public List<Role> findRolesShow(int[] rids) throws Exception{
        return adminMapper.findRoles(rids);
    }
    @Override
    public List<Permission> findPermissionsShow(int[] pids) throws Exception{
        return adminMapper.findPermissions(pids);
    }

    /**
     * 给上面的类负责把user 放到userExtend中
     * @param user
     * @return
     * @throws Exception
     */
    public UserExtend setUserExtend(User user) throws Exception {
        UserExtend ue = new UserExtend();
        ue.setUid(user.getUid());
        ue.setState(user.getState());
        ue.setUemail(user.getUemail());
        ue.setUname(user.getUname());
        ue.setUupdatetime(user.getUupdatetime());
        ue.setUcompany(user.getUcompany());
        return ue;
    }
    /**
     * 删除一个用户 根据id
     * @param id
     * @return
     * @throws Exception
     */
    @Override
    public int deleteWorkerById(int id) throws Exception{
        return adminMapper.deleteWorkerById(id) + adminMapper.deleteUser_roles(id) ;
    }

    /**
     * 根据id进行修改用户信息
     */
    @Override
    public int updateWorkerById(User user) throws Exception{
        return adminMapper.updateWorkerById(user);
    }

    /**
     * 根据id更改用户角色
     */
    @Override
    public int  updateUser_Role(int uid,int rid) throws Exception{
        return updateUser_Role(uid,rid);
    }

    /**
     * 查找全部角色,用来在添加用户的时候设置角色
     * @return
     * @throws Exception
     */
    @Override
    public List<Role> selectAllRoles() throws Exception{
        return adminMapper.selectAllRoles();
    }

    /**
     * 查找全部的权限,在添加用户的时候设置
     * @return
     * @throws Exception
     */
    @Override
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
    @Override
    public int[] selectRid(int uid) throws Exception{
        return adminMapper.selectRid(uid);
    }

    /**
     * 根据rid查找角色的名称
     * @param rid
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> selectRoles(int[] rid) throws Exception {
        return adminMapper.selectRoles(rid);
    }

    /**
     * 根据角色id在关联表里查出相应的权限Pid
     * @param rid
     * @return
     * @throws Exception
     */
    @Override
    public int[] selectPids(int[] rid) throws Exception{
        return adminMapper.selectPids(rid);
    }

    /**
     * 根据权限id的数组查找权限
     * @param list
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> selectPermissions(int[] list) throws Exception {
        return adminMapper.selectPermissions(list);
    }

    /**
     * 将上面的几个函数整合一下查出一个全的来, 通过用户id 来查找用户的角色和权限信息
     * 放到Realm中进行验证
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> findPermissions(int uid) throws Exception {
         return selectPermissions(selectPids(selectRid(uid)));
    }
    /**
     * 将上面的几个函数整合一下查出一个全的来Role
     * @param uid
     * @return
     * @throws Exception
     */
    @Override
    public Set<String> findRoles(int uid) throws Exception {
        return selectRoles(selectRid(uid));
    }

    /**
     * 添加用户单位
     * @param workPlace
     * @return
     * @throws Exception
     */
    @Override
    public int insertCompany(WorkPlace workPlace) throws Exception{
        return adminMapper.insertCompany(workPlace);
    }
    /**
     * 添加分类
     * @param classify
     * @return
     * @throws Exception
     */
    @Override
    public int insertClassify(Classify classify) throws Exception{
        return adminMapper.insertClassify(classify);
    }

}
