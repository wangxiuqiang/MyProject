package cn.ssm.controller;

import cn.ssm.pojo.Users;
import cn.ssm.service.UserService;
import cn.ssm.utils.DateUtils;
import cn.ssm.utils.MailUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.UUID;

@Controller
public class UserController {


    @Autowired
    UserService userService;
    @RequestMapping(value = "/insertUser")
    public String insertUser(Users users,String checkcode ,String repassword, HttpServletRequest request) {

        users.setState(0);
        String uuid =UUID.randomUUID().toString();
        users.setActivecode(uuid);
        users.setUpdatetime(DateUtils.format(new Date()));
        try{
            MailUtils.sendMail(uuid,users.getEmail());
        }catch (Exception e){
            e.printStackTrace();
        }

        /**
         * 从session中获取  保存的验证码,,然后根据用户提交的进行比较
         *借助request获取session
         */
        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
        if(!users.getPassword().equals(repassword)){
            return "redirect:reg?type=1";
        }
        if(!checkcode.equals(checkcode_session) ){
            return "redirect:reg?type=2";
        }


        int  num = userService.saveUser(users);
       if(num != 0){
           System.out.println("注册成功");
       }
       return "redirect:/requestLogin";
    }

    @RequestMapping("/requestLogin")
    public String requestLogin(){
        return "login";
    }

    @RequestMapping("/activecode")
    public String activecode(String code) {
        userService.updateState(code);
        return "success";
    }

    /**
     * 校验name是不是重复
     */
    @RequestMapping(value = "/checkName" )
    @ResponseBody
    public String checkName(String name) {
//        System.out.println(name);
        String username = userService.selectUsername(name);

            System.out.println(username);
        if(username != null) {
            return "{\"msg\":\"false\"}";
        }else {
            return "{\"msg\":\"true\"}";
        }

    }
}
