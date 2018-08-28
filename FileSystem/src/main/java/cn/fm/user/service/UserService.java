package cn.fm.user.service;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.Classify;
import cn.fm.pojo.User;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;

import java.util.List;

public interface UserService {
    /**
     * 设置密码
     */
    public int updateUserpwd(String upwd,String code) throws Exception;


    /**
     * 添加借阅信息
     * @param ucompany
     * @return
     * @throws Exception
     */
    public int insertBorrowcfInfo(String uname,String ucompany,int cfid) throws Exception;
    public int insertBorrowgfInfo(String uname,String ucompany,int gfid) throws Exception;
    /**
     * 更新归还时间  ,同时在这里面完成对文件状态的改变
     */
    public int updatecfBackTime(int[] fileid) throws Exception;
    public int updategfBackTime(int[] fileid) throws Exception;

    /**
     * 传过来 用户名和单位,根据这个找到id, 根据id 去查找相应的borrow ,找到之后查找根据uid 找相应的User,
     * 根据fileid找相应的 文件找到之后放在Extends类中,最后加入List
     * @param uname
     * @param ucompany
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectBorrowcfInfo(String uname,String ucompany ,int flag) throws Exception;

    public List<BorrowGFExtends> selectBorrowgfInfo(String uname,String ucompany,int flag) throws Exception;
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
}
