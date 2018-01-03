package cn.pc.exam.service.Impl;

import cn.pc.exam.dao.LoginSelect;
import cn.pc.exam.pojo.Admin;
import cn.pc.exam.pojo.Teacher;
import cn.pc.exam.service.LoginSelectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LoginSelectServiceImpl implements LoginSelectService {

    @Autowired
    LoginSelect loginSelect;

    public Admin queryAdminIDAndPassWd(String name) throws Exception {
        Admin admin = loginSelect.queryAdmin(name);
        if(admin != null ){
            return admin ;
        }else {
            System.out.println( "没有找到");
            return null ;
        }
    }
    public Teacher queryTeacher(String id) throws Exception {
        Teacher teacher = loginSelect.queryTeacher(id);
        if(teacher != null) {
            return teacher;
        }else {
            System.out.println( "没有找到");
            return null ;
        }
    }

}
