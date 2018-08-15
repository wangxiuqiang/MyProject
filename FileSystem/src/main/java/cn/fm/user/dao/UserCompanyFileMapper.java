package cn.fm.user.dao;

import cn.fm.pojo.Classify;
import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;

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
        public List<CompanyFile> selectCompanyFileByDateTime(String date) throws Exception;

        /**
         *根据文号查询
         */
        public List<CompanyFile> selectCompanyFileByNumber(int num) throws Exception;

        /**
         * 查询全部的单位制发文件文件信息
         */
        public List<CompanyFile> selectAllCompanyFile() throws  Exception;


        /**
         * 更改数据
         */
         public int updateCompanyFileById(CompanyFile file) throws Exception;

        /**
         *根据id删除单行数据
         */
        public int deleteCompanyFileById(int cfid) throws Exception;

        /**
         * 多项组合查询
         */
        public List<CompanyFile> selectCompanyFileByTwoOrMore(CompanyFile companyFile) throws Exception;

}
