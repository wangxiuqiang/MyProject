package cn.pc.exam.service.Impl;
import cn.pc.exam.dao.TeacherManager;
import cn.pc.exam.md5.Md5Salt;
import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojo.Grade;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;
import cn.pc.exam.service.TeacherManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TeacherManagerServiceImpl implements TeacherManagerService {
    @Autowired
    TeacherManager teacherManager;
    //查询课程和班级编号使用的 教师扩展类
    private TeacherExtend teacherExtend ;
    //查询课程和班级使用的,用来存放查询出来的班级编号或者课程编号
    private String array[] = new String[100];

    /**
     *   通过老师的id查出来教授的班级的编号
     * @param Tid
     * @return
     * @throws Exception
     */
    public List<Grade> selectTcAndGrade(String Tid) throws Exception {
        for (int i = 0;i < teacherExtend.getTcList().size(); i++){
            array[i] = teacherExtend.getTcList().get(i).getGradeId();
        }
        teacherExtend.setGradeList(teacherManager.selectGrade(array));
        return teacherExtend.getGradeList();
    }

    /**
     *   通过老师的编号查出 教授的课程编号
     * @param Tid
     * @return
     * @throws Exception
     */
    public List<Course> selectTcAndCourse(String Tid) throws Exception {
        teacherExtend = new TeacherExtend();
        teacherExtend.setTcList(teacherManager.selectTcWhereTid(Tid));

        for (int i = 0;i < teacherExtend.getTcList().size(); i++){
            array[i] = teacherExtend.getTcList().get(i).getCourseId();
        }

        teacherExtend.setCourseList(teacherManager.selectCourse(array));
//        for(int i = 0 ;i < teacherExtend.getCourseList().size(); i++) {
//            System.out.println(teacherExtend.getCourseList().get(i).getCid());
//            System.out.println(teacherExtend.getCourseList().get(i).getCname());
//        }
        return teacherExtend.getCourseList();
    }


    /**
     * 通过课程和班级号查学生
     * @param Cid
     * @param Gid
     * @return
     * @throws Exception
     */
    public List<StudentExtend> selectStudent(String Cid, String Gid) throws Exception {

        return teacherManager.selectStudentForCidGid(Cid,Gid);
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

    /**
     * 录入学生的成绩 , 将成绩放在mark表中,
     * 所以需要使用学号和课程号进行条件判断
     * @param score 单精度成绩
     * @param Sid 学号
     * @param Cid 课程号
     * @return
     * @throws Exception
     */
    public int insertScore(float score, String Sid, String Cid) throws Exception {
        return teacherManager.insertScore(score,Sid,Cid);
    }

}
