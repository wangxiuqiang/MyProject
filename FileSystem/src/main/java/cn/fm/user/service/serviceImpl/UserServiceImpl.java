package cn.fm.user.service.serviceImpl;

import cn.fm.admin.dao.AdminMapper;
import cn.fm.pojo.Borrow;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.pojo.User;
import cn.fm.user.dao.UserCompanyFileMapper;
import cn.fm.user.dao.UserGetFileMapper;
import cn.fm.user.dao.UserMapper;
import cn.fm.user.service.UserService;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.PassWordHelper;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;
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
     * 添加借阅信息,同时在这里修改借出的状态
     * @param ucompany
     * @return
     * @throws Exception
     */
    @Override
    public int insertBorrowcfInfo(String uname,String ucompany,int cfid) throws Exception{
        int uid = userMapper.selectUserId(uname,ucompany);
        Borrow borrow = new Borrow();
        borrow.setUid(uid);
        borrow.setFileid(cfid);
        if(borrow.getBorrowtime() != null) {
            borrow.setBorrowtime(DateToStringUtils.dataTostring());
        }

        return  userMapper.insertBorrowcfInfo(borrow) + userMapper.updateCompanyFileIsBorrow(cfid);

    }
    @Override
    public int insertBorrowgfInfo(String uname,String ucompany,int gfid) throws Exception{

        int uid = userMapper.selectUserId(uname,ucompany);
        Borrow borrow = new Borrow();
        borrow.setUid(uid);
        borrow.setFileid(gfid);
        if(borrow.getBorrowtime() != null) {
            borrow.setBorrowtime(DateToStringUtils.dataTostring());
        }
        return userMapper.insertBorrowgfInfo(borrow) + userMapper.updateGetFileIsBorrow(gfid);
    }

    /**
     * 更新归还时间  ,同时在这里面完成对文件状态的改变
     */
    @Override
    public int updatecfBackTime(Borrow borrow) throws Exception{
        String time = DateToStringUtils.dataTostring();
        if(borrow.getBorrowtime() != null) {
            borrow.setBacktime(time);
        }
        return userMapper.updateCompanyFileBack(borrow.getFileid()) +userMapper.updatecfBackTime(borrow);
    }
    @Override
    public int updategfBackTime(Borrow borrow) throws Exception{
        String time = DateToStringUtils.dataTostring();
        if(borrow.getBorrowtime() != null) {
            borrow.setBacktime(time);
        }
        return userMapper.updateGetFileBack(borrow.getFileid()) +userMapper.updategfBackTime(borrow);
    }

    /**
     * 传过来 用户名和单位,根据这个找到id, 根据id 去查找相应的borrow ,找到之后查找根据uid 找相应的User,
     * 根据fileid找相应的 文件找到之后放在Extends类中,最后加入List
     * @param uname
     * @param ucompany
     * @return
     * @throws Exception
     */
    @Override
    public List<BorrowCFExtends> selectBorrowcfInfo(String uname,String ucompany ,int flag) throws Exception{
        int uid;
        User user = null;
//        如果传过来的值为空 那么 就没必要查id,查user了,直接uid = 0,查全部的借阅信息
        if(uname != null && ucompany != null) {
            uid = userMapper.selectUserId(uname,ucompany);
            user = adminMapper.findWorkerById(uid);
        }else {
            uid = 0;
        }


        List<BorrowCFExtends> bcfes = new ArrayList<>();
        List<Borrow> borrows = userMapper.selectBorrowcfById(uid);


        System.out.println(borrows);
//        如果查出来借阅信息没有, 表示这个用户或者还没有文件外借 就返回
        if (borrows.size() <= 0) {
            return null;
        }



        for(Borrow b:borrows) {
            if(flag == 1) {
                if(b.getBacktime() != null) {
                    continue;
                }
            }
            if(flag == 2){
                if(b.getBacktime() == null) {
                    continue;
                }
            }
//      根据文件id查找文件信息
            CompanyFile cf = userCompanyFileMapper.selectCompanyFileById(b.getFileid());
            BorrowCFExtends borrowCFExtends = new BorrowCFExtends();
            borrowCFExtends.setBacktime(b.getBacktime());
            borrowCFExtends.setBorrowtime(b.getBorrowtime());
            borrowCFExtends.setCompanyFile(cf);
//如果 传过来的值为空,那么就需要从查出来的借阅信息来查找用户的信息.
            if(uname == null && ucompany == null) {
                user = adminMapper.findWorkerById(b.getUid());
            }
            borrowCFExtends.setUser(user);
            bcfes.add(borrowCFExtends);
        }
        return bcfes;
    }

    @Override
    public List<BorrowGFExtends> selectBorrowgfInfo(String uname,String ucompany,int flag) throws Exception{
        int uid;
        User user = null;
        if(uname != null && ucompany != null) {
           uid = userMapper.selectUserId(uname,ucompany);
            user = adminMapper.findWorkerById(uid);
        }else {
            uid = 0;
        }

        System.out.println(uid);

        List<BorrowGFExtends> bgfes = new ArrayList<>();
        List<Borrow> borrows = userMapper.selectBorrowgfById(uid);

        System.out.println(borrows);

        if (borrows.size() <= 0) {
            return null;
        }

        for(Borrow b:borrows) {
            if(flag == 1) {
                if(b.getBacktime() != null) {
                    continue;
                }
            }
            if(flag == 2){
                if(b.getBacktime() == null) {
                    continue;
                }
            }
            GetFile cf = userGetFileMapper.selectGetFileById(b.getFileid());
            BorrowGFExtends borrowGFExtends = new BorrowGFExtends();
            borrowGFExtends.setBacktime(b.getBacktime());
            borrowGFExtends.setBorrowtime(b.getBorrowtime());
            borrowGFExtends.setGetFile(cf);
//            如果 没有传参数的话,就是查询全部的借阅信息,那就从查出来的借阅信息中找用户
            if(uname == null && ucompany == null) {
                user = adminMapper.findWorkerById(b.getUid());
            }
            borrowGFExtends.setUser(user);
            bgfes.add(borrowGFExtends);
        }
        return bgfes;
    }

}
