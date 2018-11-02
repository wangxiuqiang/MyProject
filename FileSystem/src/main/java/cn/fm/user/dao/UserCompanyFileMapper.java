package cn.fm.user.dao;

import cn.fm.pojo.Borrow;
import cn.fm.pojo.Classify;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserCompanyFileMapper {


        /**
         * 录入单位制发文件信息
         * @param companyFile
         * @return
         * @throws Exception
         */
        public int insertCompanyFile( CompanyFile companyFile) throws Exception;


        /**
         * 根据id 查找分类信息
         * @param classifyid
         * @return
         * @throws Exception
         */
        public Classify selectClassify(int classifyid) throws Exception;

        /**
         * 根据id找文件
         * @param cfid
         * @return
         * @throws Exception
         */
        public CompanyFile selectCompanyFileById(int cfid) throws Exception;
        /**
         * 单独用名字来做模糊查询
         * @param cfname
         * @return
         * @throws Exception
         */
        public List<CompanyFile> selectCompanyFileByName(String cfname) throws Exception;

        /**
         * 根据单位来查找文件信息
         * @param language
         * @return
         * @throws Exception
         */
        public List<CompanyFile> selectCompanyFileByLanguage(String language) throws Exception;

        /**
         * 根据最后的一个分类的id查询,因为最后的一个分类是叶子
         * @param classifyid
         * @return
         * @throws Exception
         */
        public List<CompanyFile> selectCompanyFileByClassifyId(int classifyid) throws Exception;

        /**
         * 根据录入时间查询文件信息
         * @param date
         * @return
         * @throws Exception
         */
        public List<CompanyFile> selectCompanyFileByDateTime(@Param(value = "date") String date ,@Param(value = "endtime") String endtime ) throws Exception;

        /**
         *根据文号查询
         */
        public List<CompanyFile> selectCompanyFileByFontid(String cffontid) throws Exception;

        /**
         * 查询全部的单位制发文件文件信息
         */
        public List<CompanyFile> selectAllCompanyFile() throws  Exception;


        /**
         * 更改数据
         */
         public int updateCompanyFileById(CompanyFile file) throws Exception;

        /**
         * 查询这个文件是不是有借阅信息，如果有，删除 借阅信息，如果没有就算了
         * 如果没有归还 一样不能删除，返回有文件没有归还
         */
        public List<Borrow> selectCFhasBorrowInfo(int id) throws Exception;
        /**
         *根据id删除多行数据
         */
        public int deleteCompanyFileById(@Param(value = "cfid") int[] cfid) throws Exception;
        /**
         * 同时删除这个文件留下的借阅信息
         */
        public int deleteCompanyFileBorrowInfo(@Param(value = "cfid") int cfid) throws Exception;
        /**
         * 多项组合查询
         */
        public List<CompanyFile> selectCompanyFileByTwoOrMore(@Param(value = "companyFile") CompanyFile companyFile ,@Param(value = "endtime") String endtime) throws Exception;

        /**
         * 查看被删除的发文
         */
        public List<CompanyFile> selectTheCFileIsDel() throws Exception;


        /**
         * 销毁一个文件
         * @param cfid
         * @return
         * @throws Exception
         */
        public int delCompanyFileDestroy( @Param( value = "cfid") int cfid) throws Exception;
}
