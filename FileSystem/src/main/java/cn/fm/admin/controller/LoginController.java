package cn.fm.admin.controller;

/**
 * 用户的登录的Controller
 */

import cn.fm.admin.service.AdminService;
import cn.fm.pojo.User;
import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;


import java.util.HashMap;
import java.util.List;

@Controller
//@RequestMapping( produces = "application/json;charset=utf-8")
@RequestMapping( produces = "application/json;charset=utf-8" , method = RequestMethod.POST)
public class LoginController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/login" )
    @ResponseBody
    public String login(@Validated User user, BindingResult bindingResult) throws Exception {

        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError:allErrors) {
                System.out.println(objectError.getDefaultMessage());
            }
        }
        System.out.println(user.getUemail()+ "----------------" +user.getUpwd());
        /**
         * 用户登录
         */
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token =  new UsernamePasswordToken(user.getUemail(),user.getUpwd());

        subject.login(token);

        HashMap<String ,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.SUCCESS_LOGIN);
        return JSON.toJSONString(map);

    }
    @RequestMapping(value = "/logout" )
    @ResponseBody
    public String logout() throws Exception {
        Subject subject = SecurityUtils.getSubject();
        subject.logout();
        HashMap<String ,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.SUCCESS_LOGOUT);
        return JSON.toJSONString(map);
    }
    @RequestMapping(value = "/loginBefore")
    @ResponseBody
    public String loginBefore() {
        return JSON.toJSONString(StatusUtils.FAILURE_LOGIN);
    }

    @RequestMapping(value = "/authcFailure")
    public String authcFailure() {
        return JSON.toJSONString(StatusUtils.NO_ROLE_PERMISSION);
    }

//    @RequestMapping(value = "/")
//    public String loginBefore() throws  Exception {
//        return "/admin/login";
//    }


}
