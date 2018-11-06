package cn.graduate.subject.service.serviceImpl;

import cn.graduate.subject.dao.AdminMapper;
import cn.graduate.subject.pojo.Subject;
import cn.graduate.subject.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AmdinServiceImpl implements AdminService{

    @Autowired
    AdminMapper adminMapperMapper;
    /**
     * 添加一个题目
     * @param subject
     * @return
     * @throws Exception
     */
    @Override
    public int addSubject(Subject subject) throws Exception {
        return adminMapperMapper.addSubject( subject );
    }

    /**
     * 根据 更新一个题目
     * @param subject
     * @return
     * @throws Exception
     */
    public int updateSubject ( Subject subject ) throws Exception{
        return adminMapperMapper.updateSubject( subject );
    }

    /**
     * 根据sid 删除一个题目
     * @param sid
     * @return
     * @throws Exception
     */
    public int delSubject(int sid )throws Exception{
        return adminMapperMapper.delSubject( sid );
    }

    /**
     * 查看所有的题目信息
     * @return
     * @throws Exception
     */
    public List<Subject> selectAllSubject() throws Exception{
        return adminMapperMapper.selectAllSubject();
    }

    /**
     * 根据id查看某一个的题目
     * @param sid
     * @return
     * @throws Exception
     */
    public Subject selectSubjectById(  int sid ) throws Exception{
        return adminMapperMapper.selectSubjectById( sid );
    }

    /**
     * 根据题目的名称进行模糊查询
     * @param sname
     * @return
     * @throws Exception
     */
    public List<Subject> selectSubjectByName (String sname ) throws Exception{
        return adminMapperMapper.selectSubjectByName( sname );
    }
}
