package cn.pc.exam.controller;


import cn.pc.exam.pojo.Admin;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class loginController {
    /**
     * 初始登录页面,直接返回主页面
     * @return
     */
    @RequestMapping(value = "/login")
    public String Login() {
        return "index";
    }

    @RequestMapping(value = "/adminLogin")
    public String adminLogin(Admin admin) throws  Exception{
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(admin.getAname(),admin.getApassword());
        try {
            subject.login(token);
            return "redirect:success";
        }catch ( Exception e){
            e.printStackTrace();
            return null;
        }
    }

}
