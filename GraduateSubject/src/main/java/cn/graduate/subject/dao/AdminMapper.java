package cn.graduate.subject.dao;

import cn.graduate.subject.pojo.Subject;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AdminMapper {
    /**
     * 添加一个题目
     * @param subject
     * @return
     * @throws Exception
     */
    public int addSubject(Subject subject ) throws Exception;

    /**
     * 根据 更新一个题目
     * @param subject
     * @return
     * @throws Exception
     */
    public int updateSubject ( Subject subject ) throws Exception;

    /**
     * 根据sid 删除一个题目
     * @param sid
     * @return
     * @throws Exception
     */
    public int delSubject(@Param( value = "sid") int sid )throws Exception;

    /**
     * 查看所有的题目信息
     * @return
     * @throws Exception
     */
    public List<Subject> selectAllSubject() throws Exception;

    /**
     * 根据id查看某一个的题目
     * @param sid
     * @return
     * @throws Exception
     */
    public Subject selectSubjectById( @Param(value = "sid") int sid ) throws Exception;

    /**
     * 根据题目的名称进行模糊查询
     * @param sname
     * @return
     * @throws Exception
     */
    public List<Subject> selectSubjectByName (@Param( value = "sname") String sname ) throws Exception;
}
