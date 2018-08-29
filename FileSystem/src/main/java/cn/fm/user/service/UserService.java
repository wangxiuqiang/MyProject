package cn.fm.user.service;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.Classify;
import cn.fm.pojo.User;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;
import cn.fm.vo.UserExtend;

import java.util.List;

public interface UserService {
    /**
     * 设置密码
     */
    public int updateUserpwd(String upwd,String code) throws Exception;
/**
 * 通过姓名进行查询 信息 ,给下面借文件提供id
 */
    public List<User> selectUserByName(String name) throws Exception;

    /**
     * 添加借阅信息
     * @param uid
     * @return
     * @throws Exception
     */
    public int insertBorrowcfInfo(int uid,int cfid) throws Exception;
    public int insertBorrowgfInfo(int uid,int gfid) throws Exception;
    /**
     * 更新归还时间  ,同时在这里面完成对文件状态的改变
     */
    public int updatecfBackTime(int[] fileid) throws Exception;
    public int updategfBackTime(int[] fileid) throws Exception;

    /**
     * 查询一个用户所有的借阅数据
     */
    public List<BorrowCFExtends> selectBorrowcfInfo(int uid,int flag) throws Exception;
    public List<BorrowGFExtends> selectBorrowgfInfo(int uid,int flag) throws Exception;

    /**
     * 查询一个文件所有的借阅数据
     */
    public List<BorrowCFExtends> selectBorrowcfInfoByFileid(int fileid) throws Exception;
    public List<BorrowGFExtends> selectBorrowgfInfoByFileid(int fileid) throws Exception;

    /**
     * 查最顶层的分类
     */
    public List<Classify> selectClassifyBiggest() throws Exception;
    /**
     * 根据父类id查找子类 信息
     * @param fatherid
     * @return
     * @throws Exception
     */
    public List<Classify> selectClassifyByFatherId(int fatherid) throws Exception;

    /***
     * 根据邮箱找到自己的信息
     */
    public UserExtend selectMySelf(String email) throws Exception;
}
