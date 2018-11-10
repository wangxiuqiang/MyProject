package cn.graduate.subject.controller;

import cn.graduate.subject.pojo.User;
import cn.graduate.subject.utils.PassWordHelper;
import cn.graduate.subject.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;


@Controller

public class LoginController {
//    @RequestMapping(value = "/userLogin" )
//    @ResponseBody
//    public String userLogin(@RequestBody(required = false)User user) throws Exception {
////       System.out.println( uaccount );
//               System.out.println( user.getUaccount() );
//
//        HashMap<String ,Integer> map = new HashMap<>();
//        //添加状态码
//        map.put( StatusUtils.STATUSCODE , StatusUtils.LOGIN_SUCCESS  );
////        model.addAttribute( "a" , StatusUtils.LOGIN_SUCCESS );
//        return JSON.toJSONString(map);
////        return "user/index";
//    }

//    @RequestMapping(value = "/userLogin" )
//    @ResponseBody
//    public String userLogin( String uaccount, String upwd  ) throws Exception {
//       System.out.println( uaccount );
////        System.out.println( user.getUaccount() );
//
//        HashMap<String ,Integer> map = new HashMap<>();
//        //添加状态码
//        map.put( StatusUtils.STATUSCODE , StatusUtils.LOGIN_SUCCESS  );
////        model.addAttribute( "a" , StatusUtils.LOGIN_SUCCESS );
//        return JSON.toJSONString(map);
////        return "user/index";
//    }

    @RequestMapping(value = "/userLogin" )
    @ResponseBody
    public String userLogin( String uaccount, String upwd ) throws Exception {
        Subject subject = SecurityUtils.getSubject();

//        PassWordHelper pwh = new PassWordHelper();
//        upwd = pwh.SHA256( upwd );
        UsernamePasswordToken token = new UsernamePasswordToken( uaccount , upwd );
        subject.login( token );
        HashMap<String ,Integer> map = new HashMap<>();
        //添加状态码
        map.put( StatusUtils.STATUSCODE , StatusUtils.LOGIN_SUCCESS  );
//        model.addAttribute( "a" , StatusUtils.LOGIN_SUCCESS );
        return JSON.toJSONString(map);
//        return "user/index";
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

//    @RequestMapping(value = "/login")
////    @ResponseBody
//    public String index(Model model) throws Exception {
//        model.addAttribute( StatusUtils.STATUSCODE ,1);
//        return "login/login";
//
//    }

}
