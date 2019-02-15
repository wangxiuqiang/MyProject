package cn.fm.user.controller;

import cn.fm.pojo.User;
import cn.fm.user.service.UserRegService;
import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping(value = "user")
public class UserRegController {
    @Autowired
    UserRegService userRegService;

    /**
     * 用户在邮箱中跳转,实现激活
     * @param code
     * @param model
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/activecode" )
//    public String activecode(String code,Model model) throws Exception {
//        model.addAttribute("code",code);
//
//        return "/user/userReg";
//    }

    /**
     * 发送激活的code 和设置密码
     */
    @RequestMapping(value = "/reg")
    public String reg(String upwd,String code) throws Exception {
        if(userRegService.updateUserpwd(upwd,code) != 0){
            return JSON.toJSONString(StatusUtils.SUCCESS_REG);
        }
        return JSON.toJSONString(StatusUtils.FAILURE_REG);
    }


}
