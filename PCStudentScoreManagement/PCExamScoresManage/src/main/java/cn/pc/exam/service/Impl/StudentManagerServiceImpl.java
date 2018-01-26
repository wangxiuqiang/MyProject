package cn.pc.exam.service.Impl;

import cn.pc.exam.dao.StudentManager;
import cn.pc.exam.md5.Md5Salt;
import cn.pc.exam.pojo.Course;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.service.StudentManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentManagerServiceImpl implements StudentManagerService {
    @Autowired
    StudentManager studentManager;

    /**
     *
     * @param password  更改学生密码,先将password改为md5 的编码
     * @param Sid
     * @return
     * @throws Exception
     */
    public int updatePassword(String firstPassword,String password, String Sid) throws Exception {
        StudentExtend studentExtend = studentManager.selectStudent(Sid);
        String first = Md5Salt.md5(firstPassword);
        String passwordMd5 = Md5Salt.md5(password);
        if(first.equals(studentExtend.getSpassword())){
            return studentManager.updatePassword(passwordMd5,Sid);
        }else{
            return 0;
        }
    }

    /**
     * 查询该学生的选课记录
     * @param Sid
     * @return
     * @throws Exception
     */
    public List<Course> selectCourses(String Sid) throws Exception {
        return studentManager.selectCourses(Sid);
    }

    /**
     * 查询学生对应的课程成绩
     * @param Cid
     * @param Sid
     * @return
     * @throws Exception
     */
    public Float selectScore(String Cid, String Sid) throws Exception {
        return studentManager.selectScore(Cid,Sid);
    }
}
