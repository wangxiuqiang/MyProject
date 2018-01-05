package cn.pc.exam.service.Impl;

import cn.pc.exam.dao.AdminManager;
import cn.pc.exam.pojoExtends.StudentExtend;
import cn.pc.exam.pojoExtends.TeacherExtend;
import cn.pc.exam.service.AdminManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
}
