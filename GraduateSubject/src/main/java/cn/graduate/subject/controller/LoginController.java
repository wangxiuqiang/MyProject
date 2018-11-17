package cn.graduate.subject.controller;

import cn.graduate.subject.Realm.MyRealm;
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

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;


@Controller
@RequestMapping(produces = "application/json;charset=utf-8")
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
    public String userLogin(HttpServletRequest request, String uaccount, String upwd ,int ifAdmin , String checkImage) throws Exception {
        HashMap<String ,Integer> map = new HashMap<>();
        // 验证验证码是否正确
        // 从session中获取生成的验证码

        String checkcode_session = (String) request.getSession().getAttribute("checkcode_session");
        if( !checkImage.equals( checkcode_session ) ) {
            map.put( StatusUtils.STATUSCODE , StatusUtils.CHECKIMAGE_NORIGHT );
            return JSON.toJSONString( map );
        }

        Subject subject = SecurityUtils.getSubject();
//是否为管理员
        MyRealm.ifAdmin = ifAdmin;
//        PassWordHelper pwh = new PassWordHelper();
//        upwd = pwh.SHA256( upwd );
        UsernamePasswordToken token = new UsernamePasswordToken( uaccount , upwd );
//        token.setRememberMe(true);
        subject.login( token );

        //添加状态码
        map.put( StatusUtils.STATUSCODE , StatusUtils.LOGIN_SUCCESS  );
        MyRealm.user.setStatuscode( StatusUtils.LOGIN_SUCCESS );
//        MyRealm.user.setMap( map );
//        map.put( "uname" , Integer.valueOf( uaccount ) );
//        model.addAttribute( "a" , StatusUtils.LOGIN_SUCCESS );
        return JSON.toJSONString(MyRealm.user)  ;
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
