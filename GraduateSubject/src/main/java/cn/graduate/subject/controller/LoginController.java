package cn.graduate.subject.controller;

import cn.graduate.subject.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller
public class LoginController {

    @RequestMapping(value = "/userLogin")
    @ResponseBody
    public String userLoing( String uaccount, String upwd ) throws Exception {
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken( uaccount , upwd );
        subject.login( token );
        HashMap<String ,Integer> map = new HashMap<>();
        //添加状态码
        map.put(StatusUtils.STATUSCODE , StatusUtils.LOGIN_SUCCESS );
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/logout")
    @ResponseBody
    public String logout() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        HashMap<String ,Integer> map = new HashMap<>();
        //添加状态码
        map.put(StatusUtils.STATUSCODE , StatusUtils.LOGOUT );
        return JSON.toJSONString( map );

    }

    @RequestMapping(value = "/index")
    @ResponseBody
    public String index() throws Exception {

        return "index";

    }

}
