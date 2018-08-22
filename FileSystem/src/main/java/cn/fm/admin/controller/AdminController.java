package cn.fm.admin.controller;

import cn.fm.admin.service.AdminService;
import cn.fm.pojo.User;
import cn.fm.pojo.WorkPlace;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.PassWordHelper;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.UserExtend;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * 管理员用于对用户的增删改查操作
 */
@Controller
@RequestMapping(value = "/admin" ,produces = "application/json;charset=utf-8" ,method = RequestMethod.POST)
//@RequestMapping(value = "/admin" ,produces = "application/json;charset=utf-8")
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
    @RequiresRoles("admin")
    @RequestMapping(value = "/regSub")
    @ResponseBody
    public String addUser(@Validated UserExtend user , BindingResult bindingResult) throws Exception{

        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return JSON.toJSONString(allErrors);
        }


//        System.out.println(user.getUcompany());
        user.setUupdatetime(DateToStringUtils.dataTostring());
        if(adminService.addUser(user) != 0 ){
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_REG);
            return JSON.toJSONString(map);
        }else {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_REG);
            return JSON.toJSONString(map);
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
    public String selectEmail(String uemail) throws Exception {
        String result = adminService.selectEmailIfExist(uemail);
         if(result != null ) {
             HashMap<String,Integer> map = new HashMap<>();
             map.put(StatusUtils.statecode,StatusUtils.SUCCESS_FIND);
             return JSON.toJSONString(map);
         }else  {
             HashMap<String,Integer> map = new HashMap<>();
             map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
             return JSON.toJSONString(map);
         }

    }

    /**
     *  查找所有的用户信息
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/findWorkers/{page}")
    @ResponseBody
    public String findWorkers(@PathVariable Integer page) throws Exception{
        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);

        List<User> users= adminService.findAllWorker();

        PageInfo<User> pageInfo = new PageInfo<User>(users);

        if(users.size() > 0)
        {
            return JSON.toJSONString(pageInfo);
        }else {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);
        }
    }
    /**
     * 根据id查找用户信息
     */
    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
    @RequestMapping(value = "/findWorker/{uid}")
    @ResponseBody
    public String findWorker(@PathVariable int uid) throws Exception{
        UserExtend user = adminService.findWorkerById(uid);
       // System.out.println(user);
        if(user != null) {
            return JSON.toJSONString(adminService.findWorkerById(uid), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);
        }

    }

    /**
     * 删除信息,,根据工作人员id
     * IllegalStateException
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/delWorker/{uid}")
    @ResponseBody
    public String delWorker(@PathVariable int uid) throws Exception{

        if(adminService.deleteWorkerById(uid) == 2) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_DEL);
            return JSON.toJSONString(map);
        }else {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_DEL);
            return JSON.toJSONString(map);
        }
    }

    /**
     * 根据id进行修改
     * @param user
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/updateWorker")
    @ResponseBody
    public String updateWorker( User user,Integer rid) throws Exception{
        HashMap<String,Integer> map = new HashMap<>();

        if(user.getUpwd() != null) {
            PassWordHelper helper = new PassWordHelper();
            user.setUpwd(helper.SHA256(user.getUpwd()));
        }
        if(rid != null  ) {
            if(adminService.updateUser_Role(user.getUid(),rid) == 0) {
                map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
                return JSON.toJSONString(map);
            }
        }
        if(adminService.updateWorkerById(user) != 0) {
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
    }

    /**
     * 返回全部的权限
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
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
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/selectAllRoles")
    @ResponseBody
    public String selectAllRoles() throws Exception {
        return JSON.toJSONString(adminService.selectAllRoles());
    }

//
//    /**
//     * 根据id返回角色和权限信息
//     * @param uid
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/selectRoleById")
//    @ResponseBody
//    public String selectRoleById(int uid) throws Exception {
//        int[] rids = adminService.selectRid(uid);
//        return JSON.toJSONString(adminService.findRolesShow(rids));
//    }
//
//    @RequestMapping(value = "/selectPermissionById")
//    @ResponseBody
//    public String selectPermissionById(int uid) throws Exception {
//        int[] rids = adminService.selectRid(uid);
//        int[] pids = adminService.selectPids(rids);
//        return JSON.toJSONString(adminService.findPermissionsShow(pids));
//    }
}
