package cn.fm.user.dao;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.Classify;
import cn.fm.pojo.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    /**
     * 用于设置用户的密码
     */
    public int updateUserpwd(String upwd,String code) throws Exception;

    /**
     * 查找 用户的id
     * @param uname,ucompany
     * @return
     * @throws Exception
     */
    public List<User> selectUserId(@Param(value = "uname") String uname) throws Exception;

    /**
     * 添加借阅信息
     * @param borrow
     * @return
     * @throws Exception
     */
    public int insertBorrowcfInfo(Borrow borrow) throws Exception;
    public int insertBorrowgfInfo(Borrow borrow) throws Exception;

    /**
     * 设置 companyFile的文件被借出
     * @param cfid
     * @return
     * @throws Exception
     */
    public int updateCompanyFileIsBorrow(int cfid) throws Exception;

    /**
     * 设置 cf 文件归还
     * @param
     * @return
     * @throws Exception
     */
    public int updateCompanyFileBack(@Param(value = "fileid") int[] fileid) throws Exception;

    /**
     * 设置gf 文件借出归还
     * @param
     * @return
     * @throws Exception
     */
    public int updateGetFileIsBorrow(int fileid) throws Exception;
    public int updateGetFileBack(@Param(value = "fileid") int[] fileid) throws Exception;

    /**
     * 更新归还时间
     */
    public int updatecfBackTime(@Param(value = "fileid") int[] fileid) throws Exception;
    public int updategfBackTime(@Param(value = "fileid") int[] fileid) throws Exception;


    /**
     * 根据用户id 查询他所有的借阅,或者让id = 0 查询该类所有的借阅  cf 类
     * @param uid
     * @return
     * @throws Exception
     */
    public List<Borrow> selectBorrowcfById(@Param(value = "uid") int uid) throws Exception;
    /**
     * 根据用户id 查询他所有的借阅,或者让id = 0 查询该类所有的借阅  gf 类
     * @param uid
     * @return
     * @throws Exception
     */
    public List<Borrow> selectBorrowgfById(@Param(value = "uid") int uid) throws Exception;

    /**
     * 查询一个文件所有的借阅数据
     */
    public List<Borrow> selectBorrowcfInfoByFileid(@Param(value = "fileid") int fileid) throws Exception;
    public List<Borrow> selectBorrowgfInfoByFileid(@Param(value = "fileid") int fileid) throws Exception;
    /**
     * 查看是不是 被借出去了
     * @param cfid
     * @return
     * @throws Exception
     */
    public int[] selectcfisBorrow(@Param(value = "cfid") int[] cfid) throws Exception;
    public int[] selectgfisBorrow(@Param(value = "gfid") int[] gfid) throws Exception;

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
