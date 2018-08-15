package cn.fm.user.dao;

import cn.fm.pojo.Classify;
import cn.fm.pojo.GetFile;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserGetFileMapper {
    /**
     * 录入收文信息
     * @param getFile
     * @return
     * @throws Exception
     */
    public int insertGetFile( GetFile getFile) throws Exception;


    /**
     * 根据id 查找分类信息
     * @param classifyid
     * @return
     * @throws Exception
     */
    public Classify selectClassify(int classifyid) throws Exception;

    /**
     * 根据id找文件
     * @param gfid
     * @return
     * @throws Exception
     */
    public GetFile selectGetFileById(int gfid) throws Exception;
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

    /**
     * 通过两个或以上进行查询
     * @param getFile
     * @return
     */
    public List<GetFile> selectGetFileByTwoAndMore( GetFile getFile);

}
