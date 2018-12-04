package cn.fm.fileSystemChange.service;

import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.vo.BorrowCFExtends;

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
    public List<BorrowCFExtends> selectCompanyFile( CompanyFile companyFile , String endtime , int level ,int state ) throws Exception;
    /**
     * 根据用户的id 查出文件信息,包括他领取的,分配给他的 ,涉密和非涉密都算
     * @return
     * @throws Exception
     */
    public List<BorrowCFExtends> selectCompanyFileByUid( int uid , int state ) throws Exception;

}