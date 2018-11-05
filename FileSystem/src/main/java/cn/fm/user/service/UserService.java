package cn.fm.user.service;

import cn.fm.pojo.*;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;
import cn.fm.vo.UserExtend;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserService {
    /**
     * 检查是不是激活过了
     * @param code
     * @return
     * @throws Exception
     */
    public int selectState( String code ) throws  Exception;
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
    public int insertBorrowcfInfo(int uid,int cfid , int wid )  throws Exception;
    public int insertBorrowgfInfo(int uid,int gfid , int wid ) throws Exception;
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


    /**
     * 根据code 查找用户了录入时间
     */
    public String selectUserupdatetime(String code) throws Exception;

    /**
     * 查看是不是 被借出去了
     * @param cfid
     * @return
     * @throws Exception
     */
    public int selectcfisBorrow( int cfid) throws Exception;
    public int selectgfisBorrow( int gfid) throws Exception;

    /**
     * 根据传入的开始时间和终止时间查询 没有归还的文件信息, 范围查询,并通过这里面的用户id和文件id去查询用户和文件信息
     * @param starttime
     * @param endtime
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectBorrowcfByborrowtime(  String starttime ,  String endtime ) throws Exception;
    public List<BorrowGFExtends> selectBorrowgfByborrowtime( String starttime ,  String endtime ) throws Exception;


    /**
     * 根据用户id查找没有领取的文件
     * @param uid
     * @return
     * @throws Exception
     */
    public List<CompanyFile> selectcfWaitBorrow(int uid , int wid ) throws Exception;
    public List<GetFile> selectgfWaitBorrow(int uid ,int wid ) throws Exception;
    /**
     * 查找借出的文件,以便后面进行比较
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectcfIsPassTime () throws Exception;
    public List<BorrowGFExtends> selectgfIsPassTime () throws Exception;

    /**
     * 整合下面的四个方法 ,预分配
     */
    public int addBorrowInfo( Borrow borrow , int type  ) throws Exception;
    /**
     * 下面的四个方法是预分配接口调用的
     * 更新文件的待借阅标记,默认为0 表示没有被分配, 1表示已经分配
     * @return
     * @throws Exception
     */
    public int updatecfWaitBorrow(int cfid) throws Exception;
    public int updategfWaitBorrow( int gfid ) throws Exception;

    /**
     * 预分配的接口实现将预分配的文件信息录入
     * @param borrow
     * @return
     * @throws Exception
     */
    public int insertgfWaitBorrowInfo( Borrow borrow ) throws  Exception;
    public int insertcfWaitBorrowInfo( Borrow borrow ) throws  Exception;
}
