package cn.pc.exam.controller;


import cn.pc.exam.pojo.Admin;
import cn.pc.exam.service.Impl.LoginSelectServiceImpl;
import cn.pc.exam.service.LoginSelectService;
import com.sun.org.apache.xpath.internal.operations.Mod;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {
    @Autowired
    LoginSelectService loginSelectService ;

    @RequestMapping(value = "/")
    public String login(Admin admin , Model model) {
        model.addAttribute("admin",admin);
       return "index";
    }


    @RequestMapping(value = "/success")
    public String success(Admin admin, Model model) throws Exception {
        Admin admin1 = loginSelectService.queryAdminIDAndPassWd(admin.getAname());
        if(admin1 != null) {
            model.addAttribute("admin1",admin1);
            return "success";
        }else {
            model.addAttribute("no","no");
            return "index";
        }


    }
}
