package cn.fm.fileSystemChange.service;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileService {
    /**
     * 录入用户 ,对应一个mapper
     *
     * @param companyFile
     * @param uid
     * @return
     * @throws Exception
     */
    public int insertCompanyFile(CompanyFile companyFile, String uid) throws Exception;

    /**
     * 修改文件,一般是根据id  ,对应一个mapper,先查询是不是已经有待领取人了,如果有了就提示存在,
     * 可以强制修改. 直接把上一个的借阅表中uid改掉.
     *
     * @param companyFile
     * @param uid
     * @return
     * @throws Exception
     */
    public int updateCompanyFile(CompanyFile companyFile, String uid, int doIt) throws Exception;

    /**
     * 根据文件的id查找文件的信息
     * @param cfid
     * @return
     * @throws Exception
     */
    public CompanyFile selectCompanyFileById( int cfid ) throws Exception;

    /**
     * 根据文件id删除文件信息, state=0
     * @param cfid
     * @return
     * @throws Exception
     */
    public int delCompanyFileById(String cfid ) throws Exception;

    /**
     * 根据文件信息 查出文件信息
     * @param companyFile
     * @param endtime
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectCompanyFile( CompanyFile companyFile , String endtime , int level ,int wid  ) throws Exception;
    /**
     * 根据用户的id 查出文件信息,包括他领取的,分配给他的 ,涉密和非涉密都算
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectCompanyFileByUid( int uid , int state ) throws Exception;

    /**
     * 分配文件
     */
    public int insertCompanyFilsWaitBorrow( int uid,int wid,int fileid) throws Exception;

    /**
     * 归还或领走文件
     * @param uid
     * @param fileid
     * @return
     * @throws Exception
     */
    public int updateCompanyFileIsBorrowOrBackNew( int uid, String fileid, int flag ) throws Exception;

    /**
     * 清退一个文件
     */
    public int updateCompanyFileIsBack( String fileid ) throws Exception ;

    /**
     * 销毁一个文件
     * @param fileid
     * @return
     * @throws Exception
     */
    public int updateCompanyFileIsDestroy( String fileid ) throws Exception;

    /**
     * 查询出所有的借阅信息, 或者根据用户的id查找指定用户的借阅信息.所有状态的,归还,未借,借走,
     * 用户id 可以是领取人或实际领取人
     * @param flag
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectCompanyFileBorrowInfo(  int wid , int flag) throws Exception;

    // 下面是用户的收文的接口
    //只查找文件的信息,, 返回list
    public List<GetFile> selectGetFile(@Param(value = "level") int level, @Param(value = "getFile") GetFile getFile,String endtime ) throws Exception;

    /**
     * 查找单个的文件的id
     * @param gfid
     * @return
     * @throws Exception
     */
    public GetFile selectGetFileByGFid(@Param(value = "gfid") int gfid ) throws Exception;

    /**
     * 修改文件的信息
     * @param getFile
     * @return
     * @throws Exception
     */
    public int updateGetFileByGFid( GetFile getFile ) throws Exception;
    /**
     * 添加文件信息
     */
    public int insertGetFile( GetFile getFile ) throws Exception;

    /**
     * 修改文件的借阅表的信息 ,根据文件的id和单位id
     * @param wid
     * @return
     * @throws Exception
     */
    public int updateBorrow_GetFileByGFId( int wid ,int fileid, int uid , int flag ) throws Exception;

    /**
     * 设置文件的标记位为0
     * @param gfids
     * @return
     * @throws Exception
     */
    public int delGetFileByGfid(String gfids) throws Exception;
    /**
     * 设置借阅信息的标记位为0
     */
//    public int delGetFileBorrowInfoByGfid( @Param(value = "gfids") int gfids , @Param(value = "state") int state ) throws Exception;

    /**
     * 添加借阅表的信息
     * @param borrow
     * @return
     * @throws Exception
     */
//    public int insertBorrow_GetFile( Borrow borrow ) throws Exception;

    /**
     * 查询借阅的信息,包含文件信息 ,根据单位查,flag是要查的类型 0全部 1未借 2未还 3归还
     * @param wid
     * @return
     * @throws Exception
     */
    public List<BorrowGFExtends> selectGetFileAndBorrowInfo(  int wid , int flag ) throws Exception;

    /**
     *
     * @param uid
     * @return
     * @throws Exception
     */
    public List<BorrowGFExtends> selectGetFileByUid( int uid , int flag ) throws Exception;

}