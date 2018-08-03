package cn.fm.user.service;

import cn.fm.pojo.Classify;
import cn.fm.pojo.GetFile;
import cn.fm.vo.GetFileExtends;

import java.util.List;

public interface UserGetFileService {
    /**
     * 一层一层的查询
     * @param classifyid
     * @return
     * @throws Exception
     */

    public Classify selectClassify(int classifyid) throws Exception;

    /**
     * 将getFile的信息录入进数据库
     * @param getFile
     * @return
     * @throws Exception
     */
    public int insertGetFile(GetFile getFile) throws Exception;


    /**
     * 总的包揽下面几个查询的函数
     */
    public GetFileExtends findTypeFiles(GetFile getFile) throws Exception;

    /**
     * 单独用名字来做模糊查询
     * @param gfname
     * @return
     * @throws Exception
     */
    public List<GetFile> selectGetFileByName(String gfname) throws Exception;

    /**
     * 根据单位来查找文件信息
     * @param gfcompany
     * @return
     * @throws Exception
     */
    public List<GetFile> selectGetFileByCompany(String gfcompany) throws Exception;

    /**
     * 根据最后的一个分类的id查询,因为最后的一个分类是叶子
     * @param classifyid
     * @return
     * @throws Exception
     */
    public List<GetFile> selectGetFileByClassifyId(int classifyid) throws Exception;

    /**
     * 根据录入时间查询文件信息
     * @param datetime
     * @return
     * @throws Exception
     */
    public List<GetFile> selectGetFileByDateTime(String datetime) throws Exception;

    /**
     *根据文号查询
     */
    public List<GetFile> selectGetFileByNumber(int num) throws Exception;

    /**
     * 查询全部的收文文件信息
     */
    public List<GetFile> selectAllGetFile() throws  Exception;


    /**
     * 根据id更新 文件
     * @param file
     * @return
     * @throws Exception
     */
    public int updateGetFileById(GetFile file) throws Exception;
    /**
     * 根据id删除相关的信息,单条删除
     * @param id
     * @return
     * @throws Exception
     */
    public int deleteGetFileById(int id) throws Exception;

}