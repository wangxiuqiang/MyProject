package cn.fm.user.service.serviceImpl;

import cn.fm.admin.dao.AdminMapper;
import cn.fm.admin.service.AdminService;
import cn.fm.pojo.*;
import cn.fm.user.dao.UserCompanyFileMapper;
import cn.fm.user.dao.UserGetFileMapper;
import cn.fm.user.dao.UserMapper;
import cn.fm.user.service.UserService;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.PassWordHelper;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;
import cn.fm.vo.UserExtend;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserMapper userMapper;

    @Autowired
    AdminService adminService;
    @Autowired
    AdminMapper adminMapper;
    @Autowired
    UserCompanyFileMapper userCompanyFileMapper;
    @Autowired
    UserGetFileMapper userGetFileMapper;
    /**
     * 设置密码
     * @param upwd
     * @param code
     * @return
     * @throws Exception
     */


    @Override
    public int updateUserpwd(String upwd, String code) throws Exception {
        PassWordHelper pwh = new PassWordHelper();

        upwd = pwh.SHA256(upwd);
        if(upwd != null) {
            return userMapper.updateUserpwd(upwd,code);
        }else {
            return 0;
        }

    }

/**
 * 通过姓名进行查询 信息 ,给下面借文件提供id
 */
    @Override
    public List<User> selectUserByName(String name) throws Exception{
        return userMapper.selectUserId(name);
    }

    /**
     * 添加借阅信息,同时在这里修改借出的状态
     * @param uid,cfid
     * @return
     * @throws Exception
     */
    @Override
    public int insertBorrowcfInfo(int uid,int cfid) throws Exception{

        Borrow borrow = new Borrow();
        borrow.setUid(uid);
        borrow.setFileid(cfid);
        if(borrow.getBorrowtime() != null) {
            borrow.setBorrowtime(DateToStringUtils.dataTostring());
        }

        return  userMapper.insertBorrowcfInfo(borrow) + userMapper.updateCompanyFileIsBorrow(cfid);

    }
    @Override
    public int insertBorrowgfInfo(int uid,int gfid) throws Exception{

        Borrow borrow = new Borrow();
        borrow.setUid(uid);
        borrow.setFileid(gfid);
        if(borrow.getBorrowtime() != null) {
            borrow.setBorrowtime(DateToStringUtils.dataTostring());
        }
        return userMapper.insertBorrowgfInfo(borrow) + userMapper.updateGetFileIsBorrow(gfid);
    }
/**
 *  查询一个用户所有的借阅出去的文件
 */
    @Override
    public List<BorrowCFExtends> selectBorrowcfInfo(int uid,int flag) throws Exception{
        //找到用户信息
        User user = adminMapper.findWorkerById(uid);
        //根据用户id找到借阅的文件信息
        List<Borrow> bs = userMapper.selectBorrowcfById(uid);
        List<BorrowCFExtends> bcf = new ArrayList<BorrowCFExtends>();
        bs.forEach(n -> {
            try {
                BorrowCFExtends bcfe = new BorrowCFExtends();
                CompanyFile cf = userCompanyFileMapper.selectCompanyFileById(n.getFileid());
                bcfe.setUser(user);
                bcfe.setCompanyFile(cf);
                bcfe.setBorrowtime(n.getBorrowtime());
                bcfe.setBacktime(n.getBacktime());
                if(flag == 0) {
                    bcf.add(bcfe);
                }
                if(flag == 1 && n.getBacktime() == null) {
                    bcf.add(bcfe);
                }
                if(flag == 2 && n.getBacktime() != null){
                    bcf.add(bcfe);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
       return bcf;
    }
    @Override
    public List<BorrowGFExtends> selectBorrowgfInfo(int uid,int flag) throws Exception{
        //找到用户信息
        User user = adminMapper.findWorkerById(uid);
        //根据用户id找到借阅的文件信息
        List<Borrow> bs = userMapper.selectBorrowgfById(uid);
        List<BorrowGFExtends> bgf = new ArrayList<BorrowGFExtends>();
        bs.forEach(n -> {
            try {
                BorrowGFExtends bgfe = new BorrowGFExtends();
                GetFile gf = userGetFileMapper.selectGetFileById(n.getFileid());
                bgfe.setUser(user);
                bgfe.setGetFile(gf);
                bgfe.setBorrowtime(n.getBorrowtime());
                bgfe.setBacktime(n.getBacktime());
                if(flag == 0) {
                    bgf.add(bgfe);
                }
                if(flag == 1 && n.getBacktime() == null) {
                    bgf.add(bgfe);
                }
                if(flag == 2 && n.getBacktime() != null){
                    bgf.add(bgfe);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bgf;
    }
    /**
     * 查询一个文件所有的借阅数据
     */
    @Override
    public List<BorrowCFExtends> selectBorrowcfInfoByFileid(int fileid) throws Exception{
        List<Borrow> borrows = userMapper.selectBorrowcfInfoByFileid(fileid);
        List<BorrowCFExtends> bcfes = new ArrayList<>();
        CompanyFile companyFile = userCompanyFileMapper.selectCompanyFileById(fileid);
        borrows.forEach(n -> {
            try {
                BorrowCFExtends bcf = new BorrowCFExtends();
              User  user = adminMapper.findWorkerById(n.getUid());
              bcf.setBacktime(n.getBacktime());
              bcf.setBorrowtime(n.getBorrowtime());
              bcf.setCompanyFile(companyFile);
              bcf.setUser(user);
              bcfes.add(bcf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bcfes;
    }
    @Override
    public List<BorrowGFExtends> selectBorrowgfInfoByFileid(int fileid) throws Exception{
        List<Borrow> borrows = userMapper.selectBorrowgfInfoByFileid(fileid);
        List<BorrowGFExtends> bgfes = new ArrayList<>();
        GetFile getFile = userGetFileMapper.selectGetFileById(fileid);
        borrows.forEach(n -> {
            try {
                BorrowGFExtends bgf = new BorrowGFExtends();
                User  user = adminMapper.findWorkerById(n.getUid());
                bgf.setBacktime(n.getBacktime());
                bgf.setBorrowtime(n.getBorrowtime());
                bgf.setGetFile(getFile);
                bgf.setUser(user);
                bgfes.add(bgf);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        return bgfes;
    }
    /**
     * 更新归还时间  ,同时在这里面完成对文件状态的改变
     */
    @Override
    public int updatecfBackTime(int[] fileid) throws Exception{
        int[] isborrow = userMapper.selectcfisBorrow(fileid);
        for(int i = 0;i < isborrow.length; i++) {
            //没有被借出 ,表明 传输的有错误 ,  返回一个-5 ,表示  不对
            if(isborrow[1] != 2) {
                return -5;
            }
        }
        return userMapper.updateCompanyFileBack(fileid) +userMapper.updatecfBackTime(fileid);

//        String time = DateToStringUtils.dataTostring();
//        if(borrow.getBorrowtime() != null) {
//            borrow.setBacktime(time);
//        }
//        return userMapper.updateCompanyFileBack(borrow.getFileid()) +userMapper.updatecfBackTime(borrow);
    }
    @Override
    public int updategfBackTime(int[] fileid) throws Exception{
        int[] isborrow = userMapper.selectgfisBorrow(fileid);
        for(int i = 0;i < isborrow.length; i++) {
            //没有被借出 ,表明 传输的有错误 ,  返回一个-5 ,表示  不对
            if(isborrow[1] != 2) {
                return -5;
            }
        }
        return userMapper.updateGetFileBack(fileid) +userMapper.updategfBackTime(fileid);
//        String time = DateToStringUtils.dataTostring();
//        if(borrow.getBorrowtime() != null) {
//            borrow.setBacktime(time);
//        }
//        return userMapper.updateGetFileBack(borrow.getFileid()) +userMapper.updategfBackTime(borrow);
    }




    /**
     * 用在录入或修改文件信息的时候,,俩文件
     * 查最顶层的分类
     */
    @Override
    public List<Classify> selectClassifyBiggest() throws Exception{
        return userMapper.selectClassifyBiggest();
    }


    /**
     * 用在录入或修改文件信息的时候,,俩文件
     * 根据父类id查找子类 信息
     * @param fatherid
     * @return
     * @throws Exception
     */
    @Override
    public List<Classify> selectClassifyByFatherId(int fatherid) throws Exception{
//        Classify classify = userGetFileMapper.selectClassify(fatherid);
//        classify.setCyid(0);
//        classify.setCyname("0");
//        classify.setCyfather(0);
        List<Classify> classifies = userMapper.selectClassifyByFatherId(fatherid);
//        classifies.add(classify);
        return classifies;
    }

    /***
     * 根据邮箱找到自己的信息 ,包括其中的角色和权限信息
     */
    @Override
    public UserExtend selectMySelf(String email) throws Exception{

        User user = adminMapper.findUserByEmail(email);
        UserExtend userEd = adminService.findWorkerById(user.getUid());
        return userEd;
    }
}
