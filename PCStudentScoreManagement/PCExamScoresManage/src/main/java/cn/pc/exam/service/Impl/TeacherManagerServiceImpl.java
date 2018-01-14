package cn.pc.exam.service.Impl;
import cn.pc.exam.dao.TeacherManager;
import cn.pc.exam.md5.Md5Salt;
import cn.pc.exam.pojoExtends.TeacherExtend;
import cn.pc.exam.service.TeacherManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherManagerServiceImpl implements TeacherManagerService {
    @Autowired
    TeacherManager teacherManager;
    public int updatePassword(TeacherExtend teacherExtend) throws Exception {
        teacherExtend.setTpassword(Md5Salt.md5(teacherExtend.getTpassword()));
        teacherManager.updatePassword(teacherExtend);
        return 0;
    }
}
