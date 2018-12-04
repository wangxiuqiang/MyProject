package cn.fm.fileSystemChange.dao;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.CompanyFile;
import cn.fm.vo.BorrowCFExtends;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FileDao {

    /**
     * 录入文件的信息,全部都录入,录入的时候如果有就录入,并且返回文件的id
     */
    public int insertCompanyFile(CompanyFile companyFile) throws Exception;

    /**
     * 录入文件的借阅信息
     */
    public int insertBorrow_CompanyFile(@Param(value = "fileid") int fileid
            , @Param(value = "uid") int uid, @Param(value = "givetime") String givetime
            , @Param(value = "wid") int wid);

    /**
     * 根据文件id查看文件信息
     */
    public CompanyFile selectCompanyFileByCFId(@Param(value = "cfid") int cfid) throws Exception;

    /**
     * 根据文件id,用户id,用户单位id查看文件的借阅信息,使用if可以单个值,可以通过uid,wid,fileid,backtime
     * 等进行联合或单个查询
     */
    public Borrow selectBorrow_CompanyFile( Borrow borrow ) throws Exception;

    /**
     * 修改借阅表的信息,根据文件信息修改
     */
    public int updateBorrow_CompanyFileByCFId(Borrow borrow) throws Exception;

    /**
     * 更新文件的 ,根据文件的id
     */
    public int updateCompanyFileById(CompanyFile companyFile) throws Exception;
    /**
     * 根据文件id删除文件信息, state=0
     * @param cfid
     * @return
     * @throws Exception
     */
    public int delCompanyFileById(@Param(value = "cfid") int cfid ) throws Exception;

    /**
     * 根据文件id删除文件借阅信息, state=0
     * @param cfid
     * @return
     * @throws Exception
     */
    public int delBorrow_CompanyFileByCFId(@Param(value = "cfid") int cfid ) throws Exception;

    /**
     * 查询文件信息,同时查询文件的借阅信息
     * @param companyFile
     * @param endtime
     * @param level
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectCompanyFile(@Param(value = "companyFile") CompanyFile companyFile
            , @Param(value = "endtime") String endtime,@Param(value = "level") int level,@Param(value = "state") int state ) throws Exception;
    /**
     * 根据用户的id 查出文件信息,包括他领取的,分配给他的 ,涉密和非涉密都算
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectCompanyFileByUid(@Param( value = "uid") int uid  ,@Param(value = "state") int state) throws Exception;

}
