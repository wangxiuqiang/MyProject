package cn.pc.exam.service.Impl;

import cn.pc.exam.mapper.LoginSelect;
import cn.pc.exam.pojo.Admin;
import cn.pc.exam.service.LoginSelectService;
import org.springframework.beans.factory.annotation.Autowired;

public class LoginSelectServiceImpl implements LoginSelectService {

    @Autowired
    LoginSelect loginSelect;

    public Admin queryAdminIDAndPassWd(String name) throws Exception {
        Admin admin = new Admin();
        admin = loginSelect.queryAdmin(name);
        if(admin != null ){
            return admin ;
        }else {
            return null ;
        }
    }
}
