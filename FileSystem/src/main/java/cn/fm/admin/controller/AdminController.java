package cn.fm.admin.controller;

import cn.fm.admin.service.AdminService;
import cn.fm.pojo.*;
import cn.fm.user.service.UserService;
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


    @Autowired
    UserService userService;
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
        int id  = 0;
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return JSON.toJSONString(allErrors);
        }
//        System.out.println(user.getUcompany());
        user.setUupdatetime(DateToStringUtils.dataTostring());
        if(  ( id = adminService.addUser(user) ) != 0 ){
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_REG);
            map.put("userId" , id );
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
     * 删除信息,,根据工作人员id ,同时删除用户的权限信息,和指纹信息
     * 权限和指纹是要删除的,用户信息为了方便查他以前的借阅记录,所以是要将标志位改掉
     * IllegalStateException
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/delWorker")
    @ResponseBody
    public String delWorker(String uid) throws Exception{

        String[] uidstring = uid.split(",");
        int[] uids = new int[uidstring.length];
        for (int i = 0; i < uidstring.length; i++) {
            uids[i] = Integer.parseInt(uidstring[i]);
            System.out.println(uids[i]);
        }
        if(adminService.deleteWorkerById(uids) != 0) {
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
    public String updateWorker( User user,int rid) throws Exception{
        HashMap<String,Integer> map = new HashMap<>();

        if(user.getUpwd() != null) {
            PassWordHelper helper = new PassWordHelper();
            user.setUpwd(helper.SHA256(user.getUpwd()));
        }
        //如果对角色插入失败, 就返回

            if(adminService.updateUser_Role(user.getUid(),rid) == 0) {
                map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
                return JSON.toJSONString(map);
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

    /**
     * 添加一个 单位
     * @param workPlace
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/addWorkPlace")
    @ResponseBody
    public String addWorkPlace(WorkPlace workPlace) throws Exception {
        int flag = adminService.insertCompany(workPlace);
        HashMap<String,Integer> map = new HashMap<>();

        if(flag != 0) {
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }

    }

    /**
     * 添加一个分类
     * @param classify
     * @param fatherid
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/addClassify/{fatherid}")
    @ResponseBody
    public String addClassify(Classify classify ,@PathVariable int fatherid)throws Exception{
        classify.setCyfather(fatherid);
        HashMap<String,Integer> map = new HashMap<>();
        if(adminService.insertClassify(classify) != 0) {
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }

    }
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

    /**
     * 录入密级信息
     * @param lname
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/addLevelInfo")
    @ResponseBody
    public String addLevelInfo(String lname) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        if(lname == null ) {
            map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
            return JSON.toJSONString(map);
        }
        int result = adminService.addLevelInfo(lname);
        if( result > 0 ) {
            map.put( StatusUtils.statecode, StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode, StatusUtils.FAILURE_INSERT );
        }
        return JSON.toJSONString(map);
    }

    /**
     * 查找 全部的密级
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/selectAllLevel")
    @ResponseBody
    public String selectAllLevel() throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        List<Level> levels = adminService.selectAllLevel();
        if( levels !=null && levels.size() != 0 ) {
            return JSON.toJSONString(levels);
        } else {
            map.put(StatusUtils.statecode , StatusUtils.FAILURE_FIND );
            return JSON.toJSONString(map);
        }
    }

    /**
     * 录入指纹信息
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/addFingerInfo")
    @ResponseBody
    public String addFingerInfo(int uid , int fid, String finger ) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        if(finger != null) {
            Fingerprint fingerprint = new Fingerprint();
            fingerprint.setFid(fid);
            fingerprint.setUid(uid);
            fingerprint.setFinger(finger);
            int result = adminService.addFingerInfo( fingerprint );
            if(result > 0 ) {
                map.put(StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
            } else {
                map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
            }
        }else{
            map.put(StatusUtils.statecode , StatusUtils.IS_NULL );
        }
        return JSON.toJSONString(map);
    }

    /**
     * 根据id删除指纹信息
     * @param uid
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/delFingerInfo")
    @ResponseBody
    public String delFingerInfo( int[] uid ) throws Exception {
        HashMap<String , Integer > map = new HashMap<>();
        int result =  adminService.delFingerInfoByUid(uid);
        if( result > 0) {
           map.put( StatusUtils.statecode , StatusUtils.SUCCESS_DEL );
        }else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_DEL );
        }
        return JSON.toJSONString(map);
    }

    /**
     *  比较两个指纹是不是一致
     * @param finger
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/compareFP")
    @ResponseBody
    public String compareFP( String finger ) throws Exception {
        HashMap< String , Integer > map = new HashMap<>();
        if( finger == null) {
            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        User  user = adminService.selectAllFingerInfoAndCompare( finger );
        if( user != null ) {
            return JSON.toJSONString( user );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
            return JSON.toJSONString( map );
        }
    }

}
