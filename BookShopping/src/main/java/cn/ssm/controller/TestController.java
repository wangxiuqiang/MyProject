package cn.ssm.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

@Controller
public class TestController {
    @RequestMapping(value = "/showIndex")
    public String showIndex() {
        return "jspIndex";
    }

    @RequestMapping("index")
    public String index(){
        return "index";
    }

    @RequestMapping("reg")
    public String reg(String type, HttpServletRequest request){

        if("1".equals(type)){
            request.setAttribute("msg" ,"两次密码不一致");
        }else if ("2".equals(type)){
            request.setAttribute("msg","验证码不对");
        }
        return "register";
    }
}
