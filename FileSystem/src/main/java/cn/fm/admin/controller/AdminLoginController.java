package cn.fm.admin.controller;

/**
 * 用户的登录的Controller
 */

import cn.fm.admin.service.AdminService;
import cn.fm.pojo.Admin;
import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping( produces = "application/json;charset=utf-8")
public class AdminLoginController {

    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/login" )
    @ResponseBody
    public String login(@Validated Admin admin, BindingResult bindingResult) throws Exception {

        if(bindingResult.hasErrors()){
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            for (ObjectError objectError:allErrors) {
                System.out.println(objectError.getDefaultMessage());
            }
        }

        Admin admin1 = adminService.selectAdmin(admin);
        if (admin1 != null) {
            return JSON.toJSONString(StatusUtils.SUCCESS_LOGIN);
        }
        return JSON.toJSONString(StatusUtils.FAILURE_LOGIN);
    }

//    @RequestMapping(value = "/")
//    public String loginBefore() throws  Exception {
//        return "/admin/login";
//    }
}
