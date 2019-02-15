//package cn.fm.user.dao;
//
//import cn.fm.pojo.*;
//import org.apache.ibatis.annotations.Param;
//
//import java.util.List;
//
//public interface UserMapper {
//    /**
//     * 检查是不是激活过了
//     * @param code
//     * @return
//     * @throws Exception
//     */
//    public int selectState( @Param( value = "code") String code ) throws  Exception;
//    /**
//     * 用于设置用户的密码
//     */
//    public int updateUserpwd(String upwd,String code) throws Exception;
//
//    /**
//     * 查找 用户的id
//     * @param uname,ucompany
//     * @return
//     * @throws Exception
//     */
//    public List<User> selectUserId(@Param(value = "uname") String uname , @Param( value="wid") int wid ) throws Exception;
//
//    /**
//     * 添加借阅信息
//     * @param borrow
//     * @return
//     * @throws Exception
//     */
//    public int insertBorrowcfInfo(Borrow borrow) throws Exception;
//    public int insertBorrowgfInfo(Borrow borrow) throws Exception;
//
//    /**
//     * 设置 companyFile的文件被借出
//     * @param cfid
//     * @return
//     * @throws Exception
//     */
//    public int updateCompanyFileIsBorrow(@Param(value = "cfid") int cfid) throws Exception;
//
//    /**
//     * 设置 cf 文件归还
//     * @param
//     * @return
//     * @throws Exception
//     */
//    public int updateCompanyFileBack(@Param(value = "fileid") int[] fileid) throws Exception;
//
//    /**
//     * 设置gf 文件借出归还
//     * @param
//     * @return
//     * @throws Exception
//     */
//    public int updateGetFileIsBorrow(@Param(value = "gfid") int gfid) throws Exception;
//    public int updateGetFileBack(@Param(value = "fileid") int fileid,@Param(value = "isborrow") int isborrow) throws Exception;
//
//    /**
//     * 更新归还时间
//     */
//    public int updatecfBackTime(@Param(value = "fileid") int[] fileid) throws Exception;
//    public int updategfBackTime(@Param(value = "fileid") int[] fileid,@Param(value = "wid") int wid,@Param(value = "uid") int uid) throws Exception;
//
//    /**
//     * 根据传入的开始时间和终止时间查询 没有归还的文件信息, 范围查询,并通过这里面的用户id和文件id去查询用户和文件信息
//     * @param starttime
//     * @param endtime
//     * @return
//     * @throws Exception
//     */
//    public List<Borrow> selectBorrowcfByborrowtime( @Param(value = "starttime") String starttime , @Param(value = "endtime" ) String endtime ) throws Exception;
//    public List<Borrow> selectBorrowgfByborrowtime( @Param(value = "starttime") String starttime , @Param(value = "endtime" ) String endtime ) throws Exception;
//
//    /**
//     * 根据用户id 查询他所有的借阅,或者让id = 0 查询该类所有的借阅  cf 类
//     * @param wid
//     * @return
//     * @throws Exception
//     */
//    public List<Borrow> selectBorrowcfById(@Param(value = "wid") int wid) throws Exception;
//    /**
//     * 根据用户id 查询他所有的借阅,或者让id = 0 查询该类所有的借阅  gf 类
//     * @param wid
//     * @return
//     * @throws Exception
//     */
//    public List<Borrow> selectBorrowgfById(@Param(value = "wid") int wid) throws Exception;
//
//    /**
//     * 查询一个文件所有的借阅数据
//     */
//    public List<Borrow> selectBorrowcfInfoByFileid(@Param(value = "fileid") int fileid) throws Exception;
//    public List<Borrow> selectBorrowgfInfoByFileid(@Param(value = "fileid") int fileid) throws Exception;
//    /**
//     * 查看是不是 被借出去了
//     * @param cfid
//     * @return
//     * @throws Exception
//     */
//    public Integer selectcfisBorrow(@Param(value = "cfid") int cfid) throws Exception;
//    public Integer selectgfisBorrow(@Param(value = "gfid") int gfid) throws Exception;
//    /**
//     * 查看是不是 删除了
//     * @param cfid
//     * @return
//     * @throws Exception
//     */
//    public Integer selectcfisExist(@Param(value = "cfid") int cfid) throws Exception;
//    public Integer selectgfisExist(@Param(value = "gfid") int gfid) throws Exception;
//    /**
//     * 查最顶层的分类
//     */
//    public List<Classify> selectClassifyBiggest() throws Exception;
//
//    /**
//     * 根据父类id查找子类 信息
//     * @param fatherid
//     * @return
//     * @throws Exception
//     */
//    public List<Classify> selectClassifyByFatherId(int fatherid) throws Exception;
//
//    /**
//     * 根据code 查找用户了录入时间
//     */
//    public String selectUserupdatetime(@Param(value = "code") String code) throws Exception;
//
//    /**
//     * 根据用户id查找没有领取的文件
//     * @param wid
//     * @return
//     * @throws Exception
//     */
//    public int[] selectcfWaitBorrow( @Param( value = "wid") int wid ) throws Exception;
//    public int[] selectgfWaitBorrow(  @Param( value = "wid") int wid ) throws Exception;
//
//    /**
//     * 查找借出的文件,以便后面进行比较
//     * @return
//     * @throws Exception
//     */
//    public List<Borrow> selectgfIsPassTime () throws Exception;
//    public List<Borrow> selectcfIsPassTime () throws Exception;
//
//    /**
//     * 下面的四个方法是预分配接口调用的
//     * 更新文件的待借阅标记,默认为0 表示没有被分配, 1表示已经分配
//     * @return
//     * @throws Exception
//     */
//    public int updatecfWaitBorrow(@Param(value = "cfid") int cfid ) throws Exception;
//    public int updategfWaitBorrow(@Param(value = "gfid") int gfid ) throws Exception;
//
//    /**
//     * 预分配的接口实现将预分配的文件信息录入
//     * @param borrow
//     * @return
//     * @throws Exception
//     */
//    public int insertgfWaitBorrowInfo( Borrow borrow ) throws  Exception;
//    public int insertcfWaitBorrowInfo( Borrow borrow ) throws  Exception;
//
//
//
//}
