package cn.pc.exam.service.Impl;

import cn.pc.exam.dao.AdminManager;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;
import cn.pc.exam.service.AdminManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//当一个接口有多个继承类的时候,用这个Service里面的参数来当做特定类的对象名   使用类名来定义对象的时候
@Service
public class AdminManagerServiceImpl implements AdminManagerService {

    @Autowired
    AdminManager adminManager;

    /**
     * queryTeacherForOne  queryStudentForOne 使用id精确单个查找需要的信息
     * 没找到就返回空
     * @param id
     * @return
     * @throws Exception
     */
    public TeacherExtend queryTeacherForOne(String id) throws Exception{
        TeacherExtend teacherExtend = adminManager.queryTeacher(id);
        if(teacherExtend == null ) {
            return null;
        }else{
            return teacherExtend;
        }

    }

    public StudentExtend queryStudentForOne(String id) throws  Exception {
        StudentExtend studentExtend = adminManager.queryStudent(id);
        if(studentExtend == null) {
            return null;
        }else {
            return studentExtend;
        }
    }

    /**
     * 查询全部的老师或者学生的信息,返回list泛型的数组 ,函数没有参数
     * @return
     * @throws Exception
     */
    public List<StudentExtend> queryAllStudent() throws Exception{
        List<StudentExtend> list = adminManager.queryAllStudent();
        if(list != null) {
            return list;
        }else {
            return null;
        }
    }
    public List<TeacherExtend> queryAllTeacher() throws Exception{
        List<TeacherExtend> list = adminManager.queryAllTeacher();
        if(list != null) {
            return list;
        }else {
            return null;
        }
    }

    /**
     * 删除一个学生的信息
     * @param id
     * @return
     * @throws Exception
     */
    public int delectStudentOne(String id) throws Exception{
        int delete = adminManager.delectStudentOne(id);
        return delete;
    }

    /**
     * 删除一个老师的信息
     * @param id
     * @return
     * @throws Exception
     */
    public int delectTeacherOne(String id) throws Exception{
        int delete = adminManager.delectTeacherOne(id);
        return delete;
    }

    /**
     * 根据学生的班级编号查出学生的信息
     * @param Gid  要查询的学生的班级编号
     * @return
     * @throws Exception
     */
    public StudentExtend queryStudentForGrade(String Gid) throws Exception{
       StudentExtend student = adminManager.queryStudentForGrade(Gid);
      if(student != null){
          return student;
      }else{
          return null;
      }

    }

}
