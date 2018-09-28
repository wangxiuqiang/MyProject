package cn.fm.user.controller;
/**
 * 用户设置密码和 数据库备份
 */

import cn.fm.pojo.Borrow;
import cn.fm.pojo.Classify;
import cn.fm.pojo.GetFile;
import cn.fm.pojo.User;
import cn.fm.user.service.UserService;
import cn.fm.utils.DateToStringUtils;
import cn.fm.utils.MysqlBackupUtils;
import cn.fm.utils.MysqlRecoverUtils;
import cn.fm.utils.StatusUtils;
import cn.fm.vo.BorrowCFExtends;
import cn.fm.vo.BorrowGFExtends;
import cn.fm.vo.UserExtend;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/worker" , produces = "application/json;charset=utf8" ,method = RequestMethod.POST)
public class UserController {

    private static String address = "";
    @Autowired
    UserService userService;

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
     * 从数据库中查找这个时间,比较时间的变化是不是超了24小时
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/regbefore")
    @ResponseBody
    public String regbefore(String code) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();

        long now = System.currentTimeMillis();
//        从数据库找个取出时间的字符串类型数据
        String dateString = userService.selectUserupdatetime(code);
        Date date  = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(dateString == null) {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);
        }
       date =  sdf.parse(dateString);
       long before = date.getTime();

       if((((now - before)/1000)/3600) >= 24 ) {
           map.put(StatusUtils.statecode,StatusUtils.TIME_OUT);
           return JSON.toJSONString(map);
       }else {
           map.put(StatusUtils.statecode,StatusUtils.TIME_IN);
           return JSON.toJSONString(map);
       }
    }

    /**
     * 发送激活的code 和设置密码
     */

    @RequestMapping(value = "/reg")
    @ResponseBody
    public String reg( String upwd, String code) throws Exception {
        if (userService.updateUserpwd(upwd, code) != 0) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
        return JSON.toJSONString(map);
    }

    /**
     * 备份数据库
     *
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequestMapping(value = "/backupDatabase")
    @ResponseBody
    public String backupDatabase() throws Exception {

        if (MysqlBackupUtils.backup() == 0) {
            return JSON.toJSONString("备份成功");
        } else {
            return JSON.toJSONString("备份失败");

        }

    }

    /**
     * 恢复数据库
     */
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequestMapping(value = "/recoverDatabase")
    @ResponseBody
    public String recoverDatabase(MultipartFile file) throws Exception {

        if (MysqlRecoverUtils.recover(file) == 0) {
            return JSON.toJSONString("恢复成功");
        } else {
            return JSON.toJSONString("恢复失败");

        }

    }

    /**
     * 在借阅的时候提供 用户信息以供选择
     * @param uname
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin", "user"}, logical = Logical.OR)
    @RequestMapping(value = "/selectUserByName")
    @ResponseBody
    public String selectUserByName(String uname) throws Exception{
        List<User> users = userService.selectUserByName(uname);
        if(users != null &&users.size() >0) {
            return JSON.toJSONString(users);
        }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
        return JSON.toJSONString(map);

    }

    /**
     * 录入 借阅信息
     *
     * @param uid
     * @param cfid
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/insertBorrowcfInfo")
    @ResponseBody
    public String insertBorrowcfInfo(int uid,
                                     String cfid) throws Exception {
       // System.out.println("------------------" + cfid +"--------------------------");
        HashMap<String,Integer> map = new HashMap<>();
        String[] cfidStrings = cfid.split(",");
        int[] cfids = new int[cfidStrings.length];
        int i = 0;
        for( i = 0; i <cfids.length ; i++) {
            cfids[i] = Integer.parseInt(cfidStrings[i]);
        }
        int result = userService.insertBorrowcfInfo(uid, cfids);
        if (result == -5) {
            map.put(StatusUtils.statecode,StatusUtils.IS_BORROW);
            return JSON.toJSONString(map);

        }
        if (result != -4) {
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }

    }
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/insertBorrowgfInfo")
    @ResponseBody
    public String insertBorrowgfInfo( int uid,
                                      String gfid) throws Exception {
      //  System.out.println("------------------" + gfid +"--------------------------");
        HashMap<String,Integer> map = new HashMap<>();
        String[] gfidStrings = gfid.split(",");
        int[] gfids = new int[gfidStrings.length];
        int i = 0;
        for( i = 0; i <gfids.length ; i++) {
            gfids[i] = Integer.parseInt(gfidStrings[i]);
        }
        int result = userService.insertBorrowgfInfo(uid, gfids);
        if (result == -5) {
            map.put(StatusUtils.statecode,StatusUtils.IS_BORROW);
            return JSON.toJSONString(map);

        }
        if (result != -4) {
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
    }

    /**
     * 更新借阅归还时间
     */
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/updatecfBackTime")
    @ResponseBody
    public String updatecfBackTime(String cfid) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        String[] cfidStrings = cfid.split(",");
        int[] cfids = new int[cfidStrings.length];
        for(int i = 0; i < cfidStrings.length; i++) {
            cfids[i] = Integer.parseInt(cfidStrings[i]);
        }
        int result = userService.updatecfBackTime(cfids);
        if(result == -5){
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }

        if(result > 0) {
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
         }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
         }
    }
    @RequiresRoles(value = "admin")
    @RequestMapping(value = "/updategfBackTime")
    @ResponseBody
    public String updategfBackTime(String gfid) throws Exception {

        HashMap<String,Integer> map = new HashMap<>();
//        Borrow borrow = new Borrow();
////        System.out.println(uid + "---------------------" + gfid);
//        borrow.setFileid(gfid);
//        borrow.setUid(uid);
        String[] gfidStrings = gfid.split(",");
//        System.out.println(gfidStrings[0]);
        int[] gfids = new int[gfidStrings.length];
//        System.out.println(gfids[0]);
        for(int i = 0; i < gfidStrings.length; i++) {
            gfids[i] = Integer.parseInt(gfidStrings[i]);
        }
        int result = userService.updategfBackTime(gfids);
        if(result == -5){
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
        if(result > 0) {
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
    }


    /**
     * 根据id查询
     * flag 0 表示查全部
     * 1 查借阅中的,
     * 2 已经归还的
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/selectBorrowcfInfo/{flag}/{page}")
    @ResponseBody
    public String selectBorrowcfInfo(@PathVariable int flag,@PathVariable int page, int uid) throws Exception{
        HashMap<String,Integer> map = new HashMap<>();
        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);
        List<BorrowCFExtends> bcfes = userService.selectBorrowcfInfo(uid,flag);
        PageInfo<BorrowCFExtends> pageInfo = new PageInfo<BorrowCFExtends>(bcfes);
        if(bcfes != null && bcfes.size() > 0) {
            return JSON.toJSONString(pageInfo,SerializerFeature.DisableCircularReferenceDetect);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);
        }
    }
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/selectBorrowgfInfo/{flag}/{page}")
    @ResponseBody
    public String selectBorrowgfInfo(@PathVariable int flag,@PathVariable Integer page,int uid) throws Exception {
        //flag = 0 查询所有的, flag = 1 查询 在借阅中的,flag = 2 归还了的
        HashMap<String,Integer> map = new HashMap<>();


        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);

        List<BorrowGFExtends> bgfes = userService.selectBorrowgfInfo(uid,flag);

        PageInfo<BorrowGFExtends> pageInfo = new PageInfo<BorrowGFExtends>(bgfes);
        if(bgfes != null  && bgfes.size() > 0) {
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);
        }
    }

    /**
     * 根据文件id查这个文件的流向
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/selectBorrowcfInfoByFileid/{page}")
    @ResponseBody
    public String selectBorrowcfInfoByFileid(@PathVariable int page, int cfid) throws Exception{
        HashMap<String,Integer> map = new HashMap<>();
        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);
        List<BorrowCFExtends> bcfes = userService.selectBorrowcfInfoByFileid(cfid);
        PageInfo<BorrowCFExtends> pageInfo = new PageInfo<BorrowCFExtends>(bcfes);
        if(bcfes != null && bcfes.size() > 0) {
            return JSON.toJSONString(pageInfo,SerializerFeature.DisableCircularReferenceDetect);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);
        }
    }
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/selectBorrowgfInfoByFileid/{page}")
    @ResponseBody
    public String selectBorrowgfInfoByFileid(@PathVariable Integer page,int gfid) throws Exception {
        //flag = 0 查询所有的, flag = 1 查询 在借阅中的,flag = 2 归还了的
        HashMap<String,Integer> map = new HashMap<>();


        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);

        List<BorrowGFExtends> bgfes = userService.selectBorrowgfInfoByFileid(gfid);

        PageInfo<BorrowGFExtends> pageInfo = new PageInfo<BorrowGFExtends>(bgfes);
        if(bgfes != null  && bgfes.size() > 0) {
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);
        }
    }

//    public String selectBorrowcfInfo(String uname,String ucompany,@PathVariable int flag,@PathVariable Integer page) throws Exception {
//        HashMap<String,Integer> map = new HashMap<>();
//
//
//        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);
//
//        List<BorrowCFExtends> bcfes = userService.selectBorrowcfInfo(uname,ucompany,flag);
//
//        PageInfo<BorrowCFExtends> pageInfo = new PageInfo<BorrowCFExtends>(bcfes);
//
//        if(bcfes != null && bcfes.size() > 0) {
//            return JSON.toJSONString(pageInfo,SerializerFeature.DisableCircularReferenceDetect);
//        }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
//            return JSON.toJSONString(map);
//        }
//    }
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
//
//    @RequestMapping(value = "/selectBorrowgfInfo/{flag}/{page}")
//    @ResponseBody
//    public String selectBorrowgfInfo(String uname,String ucompany,@PathVariable int flag,@PathVariable Integer page) throws Exception {
//        //flag = 0 查询所有的, flag = 1 查询 在借阅中的,flag = 2 归还了的
//        HashMap<String,Integer> map = new HashMap<>();
//
//
//        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);
//
//        List<BorrowGFExtends> bgfes = userService.selectBorrowgfInfo(uname,ucompany,flag);
//
//        PageInfo<BorrowGFExtends> pageInfo = new PageInfo<BorrowGFExtends>(bgfes);
//        if(bgfes != null  && bgfes.size() > 0) {
//            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
//        }else {
//            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
//            return JSON.toJSONString(map);
//        }
//    }

    /**
     * 查找最顶层的 分类
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
    @RequestMapping(value = "/selectClassifyBiggest")
    @ResponseBody
    public String selectClassifyBiggst() throws Exception {
        List<Classify> classifies = userService.selectClassifyBiggest();
        if(classifies != null && classifies.size() > 0) {
            return JSON.toJSONString(classifies);
        }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
        return JSON.toJSONString(map);
    }
    /**
     * 查找下一级的分类, 并且 打印出这些分类的总的存放地址
     */
    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
    @RequestMapping(value = "/selectClassifyByFather/{fatherid}")
    @ResponseBody
    public String selectClassifyByFather(@PathVariable int fatherid) throws Exception{
        List<Classify> classifies = userService.selectClassifyByFatherId(fatherid);
//        如果这个 cyid = 0 则带出去的是选择了的分类的地址
        if(classifies != null && classifies.size() > 0) {
//            for (Classify c: classifies) {
//                if(c.getCyid() == 0) {
//                    address = address + "-" + c.getCyaddress();
//                    c.setCyaddress(address);
//                }
//            }
            return JSON.toJSONString(classifies);
        }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
        return JSON.toJSONString(map);
    }

    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
    @RequestMapping(value = "/selectMyself")
    @ResponseBody
    public String selectMyself() throws Exception{
        Subject subject = SecurityUtils.getSubject();
        String uemail = (String)subject.getPrincipal();
//        System.out.println(uemail);
       UserExtend ue = userService.selectMySelf(uemail);
       if(ue != null) {
           return  JSON.toJSONString(ue);
       }
        HashMap<String,Integer> map = new HashMap<>();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
        return JSON.toJSONString(map);
    }

    /**
     * 根据日期范围查询未归还的用户信息及其借阅的文件信息
     * @param starttime
     * @param endtime
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
    @RequestMapping(value = "/selectBorrowcfByborrowtime")
    @ResponseBody
    public String selectBorrowcfByborrowtime(String starttime , String endtime ) throws  Exception {
        HashMap<String , Integer > map = new HashMap<>();
        if( starttime == null || endtime == null ) {
           map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
           return JSON.toJSONString(map);
        }
        List<BorrowCFExtends> bcf = userService.selectBorrowcfByborrowtime(starttime, endtime );
        if( bcf != null ) {
            return JSON.toJSONString(bcf);
        }else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
            return JSON.toJSONString(map);
        }

    }

    /**
     * 根据日期范围查询未归还的用户信息及其借阅的文件信息
     * @param starttime
     * @param endtime
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin","user"} ,logical = Logical.OR)
    @RequestMapping(value = "/selectBorrowgfByborrowtime")
    @ResponseBody
    public String selectBorrowgfByborrowtime(String starttime , String endtime ) throws  Exception {
        HashMap<String , Integer > map = new HashMap<>();
        if( starttime == null || endtime == null ) {
            map.put( StatusUtils.statecode , StatusUtils.IS_NULL );
            return JSON.toJSONString(map);
        }
        List<BorrowGFExtends> bgf = userService.selectBorrowgfByborrowtime(starttime, endtime );
        if( bgf != null ) {
            return JSON.toJSONString(bgf);
        }else {
            map.put( StatusUtils.statecode , StatusUtils.FAILURE_FIND );
            return JSON.toJSONString(map);
        }

    }

}