package cn.graduate.subject.controller;

import cn.graduate.subject.service.UserService;
import cn.graduate.subject.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 修改密码,根据学号,不是用户编号
     * @param upwd
     * @param uaccount
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updatePwd")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String updatePwd( String upwd , String uaccount ) throws Exception {
        HashMap< String , Integer > map = new HashMap<>();
        int result = userService.updatePwd( upwd , uaccount );
        if( result > 0 ) {
            map.put(StatusUtils.STATUSCODE , StatusUtils.UPDATE_SUCCESS );
        } else {
            map.put(StatusUtils.STATUSCODE , StatusUtils.UPDATE_FAILURE );
        }
        return JSON.toJSONString( map );
    }

    @RequestMapping(value = "/addSubjectForSelf")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String addSubjectForSelf ( int sid, int uid ) throws Exception {
        HashMap< String , Integer > map = new HashMap<>();
        int result = userService.addSubjectForSelf( sid , uid );
        if( result > 0 ) {
            map.put(StatusUtils.STATUSCODE , StatusUtils.UPDATE_SUCCESS );
        } else {
            map.put(StatusUtils.STATUSCODE , StatusUtils.UPDATE_FAILURE );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 删除自己的选题信息
     * @param uid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delSubjectForSelf")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String delSubjectForSelf (  int uid ) throws Exception {
        HashMap< String , Integer > map = new HashMap<>();
        int result = userService.delSubjectForSelf(  uid );
        if( result > 0 ) {
            map.put(StatusUtils.STATUSCODE , StatusUtils.DEL_SUCCESS );
        } else {
            map.put(StatusUtils.STATUSCODE , StatusUtils.DEL_FAILURE );
        }
        return JSON.toJSONString( map );
    }
}
