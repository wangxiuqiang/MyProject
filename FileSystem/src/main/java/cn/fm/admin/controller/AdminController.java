package cn.fm.admin.controller;

import cn.fm.admin.service.AdminService;
import cn.fm.pojo.Admin;
import cn.fm.pojo.User;
import cn.fm.pojo.WorkPlace;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 管理员用于对用户的增删改查操作
 */
@Controller
@RequestMapping(value = "/admin" ,produces = "application/json;charset=utf-8")
public class AdminController {
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
     * 录入用户的信息
     * 接受前端的数据,进行数据的写入
     * @param user
     * @return
     */
    @RequestMapping(value = "/regSub")
    @ResponseBody
    public String addUser(@RequestBody User user) throws Exception{

        user.setUupdatetime(DateToStringUtils.dataTostring());
        if(adminService.addUser(user) != 0 ){
            return JSON.toJSONString(StatusUtils.SUCCESS_REG);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_REG);
        }
    }
//    @RequestMapping(value = "/regAdmin")
//    @ResponseBody
//    public String regAdmin(@RequestBody Admin admin) throws Exception {
//        if(adminService.addAdmin(admin) != 0) {
//            return JSON.toJSONString(StatusUtils.SUCCESS_REG);
//        }else {
//            return JSON.toJSONString(StatusUtils.FAILURE_REG);
//        }
//    }

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
    public String selectEmail(@RequestBody String uemail) throws Exception {
        String result = adminService.selectEmailIfExist(uemail);
         if(result != null ) {
             return JSON.toJSONString("1");
         }else  {
             return JSON.toJSONString("0");
         }

    }

    /**
     *  查找所有的用户信息
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/findWorkers")
    @ResponseBody
    public String findWorkers() throws Exception{
        return JSON.toJSONString(adminService.findAllWorker());
    }
    /**
     * 根据id查找用户信息
     */
    @RequestMapping(value = "/findWorker")
    @ResponseBody
    public String findWorker(int uid) throws Exception{
        return JSON.toJSONString(adminService.findWorkerById(uid));
    }

    /**
     * 删除信息,,根据工作人员id
     */
    @RequestMapping(value = "/delWorker")
    @ResponseBody
    public String delWorker(int uid) throws Exception{
        if(adminService.deleteWorkerById(uid) != 0) {
            return JSON.toJSONString(StatusUtils.SUCCESS_DEL);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_DEL);
        }
    }

    /**
     * 根据id进行修改
     * @param user
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateWorker")
    @ResponseBody
    public String updateWorker(User user) throws Exception{
        if(adminService.updateWorkerById(user) != 0) {
            return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_INSERT);
        }
    }

    /**
     * 返回全部的权限
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectAllPermissions")
    @ResponseBody
    public String selectAllPermissions() throws Exception {
        return JSON.toJSONString(adminService.selectAllPermissions());
    }

    /**
     * 返回全部的角色
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectAllRoles")
    @ResponseBody
    public String selectAllRoles() throws Exception {
        return JSON.toJSONString(adminService.selectAllRoles());
    }

}
