package cn.fm.admin.service.serviceImpl;

import cn.fm.admin.service.AdminService;
import cn.fm.admin.dao.AdminMapper;
import cn.fm.pojo.Admin;
import cn.fm.pojo.User;
import cn.fm.pojo.WorkPlace;
import cn.fm.utils.MD5Utils;
import cn.fm.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminServiceImpl implements AdminService{

    @Autowired
    AdminMapper adminMapper;

    /***
     * 查找admin用来进行登录
     * @param admin
     * @return
     * @throws Exception
     */
    @Override
    public Admin selectAdmin(Admin admin) throws Exception {
        return adminMapper.selectAdmin(admin);
    }

    /**
     * 插入一个用户
     * @return
     */
    @Override
    public int addUser(User user) throws Exception {
        user.setCode(UUID.randomUUID().toString());
        String pwd = user.getUpwd();

        MailUtils.sendMail(user.getCode(),user.getUemail());
        return adminMapper.addUser(user);
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
}
