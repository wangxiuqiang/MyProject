package cn.fm.user.dao;

import cn.fm.pojo.Borrow;
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
    public int selectUserId(@Param(value = "uname") String uname,
                            @Param(value = "ucompany") String ucompany) throws Exception;

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
     * @param cfid
     * @return
     * @throws Exception
     */
    public int updateCompanyFileBack(int cfid) throws Exception;

    /**
     * 设置gf 文件借出归还
     * @param gfid
     * @return
     * @throws Exception
     */
    public int updateGetFileIsBorrow(int gfid) throws Exception;
    public int updateGetFileBack(int gfid) throws Exception;

    /**
     * 更新归还时间
     */
    public int updatecfBackTime(Borrow borrow) throws Exception;
    public int updategfBackTime(Borrow borrow) throws Exception;


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

}
