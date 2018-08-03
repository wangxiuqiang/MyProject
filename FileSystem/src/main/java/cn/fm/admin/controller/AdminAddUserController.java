package cn.fm.admin.controller;

import cn.fm.admin.service.AdminService;
import cn.fm.pojo.User;
import cn.fm.pojo.WorkPlace;
import cn.fm.utils.DateToStringUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping(value = "/admin" ,produces = "application/json;charset=utf-8")
public class AdminAddUserController {
    /**
     * 引入service接口的实现类
     */
    @Autowired
    AdminService adminService;

    /**
     * 展示主页
     * @return
     */
//    @RequestMapping(value = "/index")
//    public String index() {
//        return "/admin/adminAddWork";
//    }

    /**
     * 接受前端的数据,进行数据的写入
     * @param user
     * @return
     */
    @RequestMapping(value = "/addUser")
    @ResponseBody
    public String addUser(@RequestBody User user) throws Exception{

        user.setUupdatetime(DateToStringUtils.dataTostring());
        adminService.addUser(user);
        return "/test";
    }

    /**
     * 查找相应的单位
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectWorkPlace")
    @ResponseBody
    public String selectWorkPlace() throws Exception{
        List<WorkPlace> list = adminService.selectWorkPlace();
       // System.out.println(list.get(0).getWid());
         return JSON.toJSONString(list);
    }

    /**
     * 判断是否存在这个用户
     * @param uemail
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectEmail")
    @ResponseBody
    public String selectEmail(String uemail) throws Exception {
        String result = adminService.selectEmailIfExist(uemail);
         if(result != null ) {
             return JSON.toJSONString("1");
         }else  {
             return JSON.toJSONString("0");
         }

    }
}
