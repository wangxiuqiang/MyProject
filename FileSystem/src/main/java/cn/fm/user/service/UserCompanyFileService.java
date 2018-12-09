package cn.fm.user.service;

import cn.fm.pojo.Classify;
import cn.fm.pojo.CompanyFile;
import cn.fm.vo.CompanyFileExtends;


import java.util.List;

public interface UserCompanyFileService {

    /**
     * 录入单位制发文件信息
     * @param companyFile
     * @return
     * @throws Exception
     */
    public int insertCompanyFile( CompanyFile companyFile) throws Exception;


    /**
     * 总的包揽下面几个查询的函数
     */
    public List<CompanyFile> findTypeFiles(CompanyFile companyFile , String endtime ,int level ) throws Exception;
    /**
     * 根据id 查找分类信息
     * @param classifyid
     * @return
     * @throws Exception
     */
    public Classify selectClassify(int classifyid) throws Exception;

    /**
     * 多项组合查询
     */
    public List<CompanyFile> selectCompanyFileByTwoOrMore(CompanyFile companyFile , String endtime ,int level) throws Exception;

    /**
     * 单独用名字来做模糊查询
     * @param cfname
     * @return
     * @throws Exception
     */
    public List<CompanyFile> selectCompanyFileByName(String cfname,int level) throws Exception;

    /**
     * 根据单位来查找文件信息
     * @param language
     * @return
     * @throws Exception
     */
    public List<CompanyFile> selectCompanyFileByLanguage(String language,int level) throws Exception;

    /**
     * 根据最后的一个分类的id查询,因为最后的一个分类是叶子
     * @param classifyid
     * @return
     * @throws Exception
     */
    public List<CompanyFile> selectCompanyFileByClassifyId(int classifyid,int level) throws Exception;

    /**
     * 根据录入时间查询文件信息
     * @param date
     * @return
     * @throws Exception
     */
    public List<CompanyFile> selectCompanyFileByDateTime(String date , String endtime,int level ) throws Exception;

    /**
     *根据文号查询
     */
    public List<CompanyFile> selectCompanyFileByFontid(String cffontid,int level) throws Exception;

    /**
     * 查询全部的单位制发文件文件信息
     */
    public List<CompanyFile> selectAllCompanyFile(int level) throws  Exception;
    /**
     * 根据id找文件
     * @param cfid
     * @return
     * @throws Exception
     */
    public CompanyFile selectCompanyFileById(int cfid) throws Exception;
    /**
     * 更改数据
     */
    public int updateCompanyFileById(CompanyFile companyFile) throws Exception;

    /**
     *根据id删除单行数据
     */
    public int deleteCompanyFileById(int[] cfid) throws Exception;

    /**
     * 同时删除这个文件留下的借阅信息
     */
    public int deleteCompanyFileBorrowInfo(int cfid) throws Exception;
    /**
     * 查看被删除的发文
     */
    public List<CompanyFile> selectTheCFileIsDel() throws Exception;


    /**
     * 销毁一个文件,更新销毁日期
     * @param cfids
     * @return
     * @throws Exception
     */
    public int delCompanyFileDestroy( String[] cfids) throws Exception;

    /**
     * 清退一个文件,更新清退日期
     * @param cfids
     * @return
     * @throws Exception
     */
    public int delCompanyFileBack(  String[] cfids  ) throws Exception;
}
