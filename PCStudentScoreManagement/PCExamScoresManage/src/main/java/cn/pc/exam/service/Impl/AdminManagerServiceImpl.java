package cn.pc.exam.service.Impl;

import cn.pc.exam.dao.AdminManager;
import cn.pc.exam.pojo.Student;
import cn.pc.exam.pojo.Teacher;
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

    /**
     * 删除一个学生的信息
     * @param id
     * @return
     * @throws Exception
     */
    public int delectStudentOne(String id) throws Exception{
        int delete = adminManager.deleteStudentOne(id);
        return delete;
    }

    /**
     * 删除一个老师的信息
     * @param id
     * @return
     * @throws Exception
     */
    public int delectTeacherOne(String id) throws Exception{
        int delete = adminManager.deleteTeacherOne(id);
        return delete;
    }

    /**
     * 删除部分教师和学生的信息,返回int类型来确定语句执行成功
     * @param deleteSome
     * @return
     * @throws Exception
     */
    public int deleteSomeStudent(String[] deleteSome) throws Exception{
        int deleteSomeStudent = adminManager.deleteSomeStudent(deleteSome);
        return deleteSomeStudent;
    }
    public int deleteSomeTeacher(String[] deleteSome) throws Exception{
        int deleteSomeTeacher = adminManager.deleteSomeTeacher(deleteSome);
        return deleteSomeTeacher;
    }


    /**
     *   录入老师和学生的信息
     * @param
     * @return
     * @throws Exception
     */
    public int insertStudent(Student student) throws Exception{
        int count = 0;
        count = adminManager.InsertStudent(student);
        if(count != 0 ){
            return count;
        }else{
            return 0;
        }

    }
    public int insertTeacher(Teacher teacher) throws Exception{
        int count = 0;
        count = adminManager.InsertTeacher(teacher);
        if(count != 0 ){
            return count;
        }else{
            return 0;
        }
    }

    /**
     * 用来更新教师和学生的信息
     * @param teacher
     * @return
     * @throws Exception
     */
    public int updateTeacherOne(Teacher teacher) throws Exception {
        adminManager.updateTeacherOne(teacher);
        return 0;
    }

    public int updateStudentOne(Student student) throws Exception {
        adminManager.updateStudentOne(student);
        return 0;
    }

}
