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

    public void selectTcAndCourse(String Tid) throws Exception {
        TeacherExtend teacherExtend = new TeacherExtend();
        teacherExtend.setTcList(teacherManager.selectTcWhereTid(Tid));
        String array[] = new String[10];
        for (int i = 0;i < teacherExtend.getTcList().size(); i++){
            array[i] = teacherExtend.getTcList().get(i).getCourseId();
        }

        teacherExtend.setCourseList(teacherManager.selectCourse(array));

        System.out.println(teacherExtend.getCourseList().get(0).getCid());
        System.out.println(teacherExtend.getCourseList().get(0).getCname());

    }

    /**
     * 修改教师的密码,使用Md5编码进行转码,然后存入
     * @param teacherExtend
     * @return
     * @throws Exception
     */
    public int updatePassword(TeacherExtend teacherExtend) throws Exception {
        teacherExtend.setTpassword(Md5Salt.md5(teacherExtend.getTpassword()));
        teacherManager.updatePassword(teacherExtend);
        return 0;
    }
}
