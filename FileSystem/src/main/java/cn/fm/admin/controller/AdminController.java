package cn.fm.admin.controller;

import cn.fm.admin.service.AdminService;
import cn.fm.pojo.*;
import cn.fm.user.service.UserService;
import cn.fm.utils.*;
import cn.fm.vo.UserExtend;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.machinezoo.sourceafis.FingerprintTemplate;
import org.apache.ibatis.annotations.Param;
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
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Paths;
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
     * 接受前端的数据,进行数据的写入,需要系统生成录入时间,返回录入的用户的id和状态码
     * @param user
     * @return
     */
    @RequiresRoles("admin")
    @RequestMapping(value = "/regSub")
    @ResponseBody
    public String addUser(@Validated UserExtend user , BindingResult bindingResult) throws Exception{
        int id  = 0;
        HashMap<String,Integer> map = new HashMap<>();
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return JSON.toJSONString(allErrors);
        }
//        System.out.println(user.getUcompany());
        user.setUupdatetime(DateToStringUtils.dataTostring());
        if(  ( id = adminService.addUser(user) ) != 0 ){

            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_REG);
            map.put("userId" , id );
            return JSON.toJSONString(map);
        }else {
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
     * 查找相应的单位 ,传入page和pageSize两个参数,用来换页和每页的数量
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectWorkPlace/{page}/{pageSize}")
    @ResponseBody
    public String selectWorkPlace(@PathVariable Integer page , @PathVariable int pageSize) throws Exception{

        PageHelper.startPage(page, pageSize );
        List<WorkPlace> list = adminService.selectWorkPlace();
        PageInfo<WorkPlace> pageInfo = new PageInfo<WorkPlace>(list);

       // System.out.println(list.get(0).getWid());
         return JSON.toJSONString(list);
    }

    /**
     * 判断是否存在这个用户
     * @param uemail
     * @return
     * @throws Exception
     */
//    @RequestMapping(value = "/selectEmail")
//    @ResponseBody
//    public String selectEmail(String uemail) throws Exception {
//        String result = adminService.selectEmailIfExist(uemail);
//         if(result != null ) {
//             HashMap<String,Integer> map = new HashMap<>();
//             map.put(StatusUtils.statecode,StatusUtils.SUCCESS_FIND);
//             return JSON.toJSONString(map);
//         }else  {
//             HashMap<String,Integer> map = new HashMap<>();
//             map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
//             return JSON.toJSONString(map);
//         }
//
//    }

    /**
     *  查找所有的用户信息.传入page和pageSize两个参数,用来换页和每页的数量
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/findWorkers/{page}/{pageSize}")
    @ResponseBody
    public String findWorkers(@PathVariable Integer page , @PathVariable int pageSize) throws Exception{
        PageHelper.startPage(page, pageSize );

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
     * 根据id查找用户信息,传入用户的id,然后将用户的信息输出,如果有的话,
     */
    @RequiresRoles(value = "admin")
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
     * 权限和指纹是要删除的,用户信息为了方便查他以前的借阅记录,所以是要将标志位改掉,因为文件还存在,所以文件暂时不删除,
     * 需要查看他是不是还有没有归还的文件,如果有的话 不能删除,两种文件都要查看一下
     * IllegalStateException
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/delWorker")
    @ResponseBody
    public String delWorker(String uid) throws Exception{
        HashMap<String,Integer> map = new HashMap<>();
        String[] uidstring = uid.split(",");
        //先将传过来的信息进行数组化
        int[] uids = new int[uidstring.length];
        for (int i = 0; i < uidstring.length; i++) {
            uids[i] = Integer.parseInt(uidstring[i]);
            System.out.println(uids[i]);
        }
        if(adminService.deleteWorkerById(uids) != 0) {

            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_DEL);
            return JSON.toJSONString(map);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_DEL);
            return JSON.toJSONString(map);
        }
    }

    /**
     * 根据id进行修改, 需要将用户的全部的字段都传过来,然后根据修改了的值进行修改,rid暂时不需要,修改权限的
     * @param user
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/updateWorker")
    @ResponseBody
    public String updateWorker( User user,Integer rid) throws Exception{
        HashMap<String,Integer> map = new HashMap<>();

//        if(user.getUpwd() != null) {
//            PassWordHelper helper = new PassWordHelper();
//            user.setUpwd(helper.SHA256(user.getUpwd()));
//        }
        //如果对角色插入失败, 就返回

//            if(adminService.updateUser_Role(user.getUid(),rid) == 0) {
//                map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
//                return JSON.toJSONString(map);
//            }

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
     * 删除 单位信息
     * @param wid
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/delCompany")
    @ResponseBody
    public String delCompany ( int wid ) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        int result = adminService.delCompany( wid );
            if(result > 0) {
                map.put( StatusUtils.statecode , StatusUtils.SUCCESS_DEL );
            } else {
                map.put( StatusUtils.statecode , StatusUtils.FAILURE_DEL );
            }
        return JSON.toJSONString( map );
    }

    /**
     * 更新单位信息, 根据单位的id
     * @param workPlace
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/updateCompany")
    @ResponseBody
    public String updateCompany( WorkPlace workPlace ) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        if ( workPlace.getWname() == null ) {
            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        int result  = adminService.updateCompany( workPlace );
        if(result > 0) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
        }
        return JSON.toJSONString( map );
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
        classify.setCyfatherid(fatherid);
        HashMap<String,Integer> map = new HashMap<>();
        int id = adminService.insertClassify( classify );
        if(id != 0) {
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            map.put( "cyid" , id );
            return JSON.toJSONString(map);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }

    }

    /**
     * 删除分类信息和子分类
     * @param cyid
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/delClassify")
    @ResponseBody
    public String delClassify ( int cyid ) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        int result = adminService.delClassify( cyid );
        if(result > 0) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_DEL );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_DEL );
        }
        return JSON.toJSONString( map );
    }

    /**
     * 修改一个分类
     * @param classify
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/updateClassify")
    @ResponseBody
    public String updateClassify( Classify classify  ) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        if(classify.getCyaddress() != null || classify.getCyfatherid() != 0 || classify.getCyname() != null ) {
            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        int result = adminService.updateClassify( classify );
        if(result > 0) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
        }
        return JSON.toJSONString( map );
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
     * 删除密级
     * @param lid
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/delLevelInfo")
    @ResponseBody
    public String delLevelInfo( int lid ) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        if( adminService.delLevelInfo( lid ) > 0 ) {
            map.put(StatusUtils.statecode , StatusUtils.SUCCESS_DEL );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_DEL );
        }
        return JSON.toJSONString( map );
    }
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/updateLevelInfo")
    @ResponseBody
    public String updateLevelInfo( Level level ) throws  Exception {
        HashMap<String,Integer> map = new HashMap<>();
        if( level.getLname() == null ) {
            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        int result = adminService.updateLevelInfo( level );
        if(result > 0) {
            map.put( StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_INSERT );
        }
        return JSON.toJSONString( map );
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
     * 录入指纹信息 ,fid  指纹id
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/addFingerInfo")
    @ResponseBody
    public String addFingerInfo(int uid ,  MultipartFile finger ) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
//        System.out.println( finger.getOriginalFilename() );
        if( !finger.isEmpty() ) {
            //获取图片存放在服务器的路径
            String bmpFilePath = UploadUtils.upload( finger , 0);
            System.out.println( bmpFilePath );
            int result = adminService.addFingerInfo( uid ,  bmpFilePath );
            if(result > 0 ) {
                map.put(StatusUtils.statecode , StatusUtils.SUCCESS_INSERT );
                //删除文件
                RmFileUtils.rmFile();
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
    public String compareFP(MultipartFile finger) throws Exception {
        HashMap< String , Integer > map = new HashMap<>();
        if( finger.isEmpty() ) {
            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
            return JSON.toJSONString( map );
        }
        String filePath = UploadUtils.upload( finger , 0 );
        User  user = adminService.selectAllFingerInfoAndCompare(  filePath );
        if( user != null ) {
            return JSON.toJSONString( user );
        } else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
            return JSON.toJSONString( map );
        }
    }

}
