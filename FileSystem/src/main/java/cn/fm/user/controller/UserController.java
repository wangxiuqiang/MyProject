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
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
     * 录入 借阅信息
     *
     * @param uname
     * @param ucompany
     * @param cfid
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/insertBorrowcfInfo")
    @ResponseBody
    public String insertBorrowcfInfo( String uname,  String ucompany,
                                      Integer cfid) throws Exception {
       // System.out.println("------------------" + cfid +"--------------------------");

        if(cfid == null) {
            cfid = 0;
        }
        if(cfid == 0) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
        if(ucompany == null || uname == null   ) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
            return JSON.toJSONString(map);

        }
        if (userService.insertBorrowcfInfo(uname, ucompany, cfid) != 0) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        } else {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
    }
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/insertBorrowgfInfo")
    @ResponseBody
    public String insertBorrowgfInfo( String uname,  String ucompany,
                                      Integer gfid) throws Exception {
      //  System.out.println("------------------" + gfid +"--------------------------");
        HashMap<String,Integer> map = new HashMap<>();

        if(gfid == null) {
            gfid = 0;

        }
        if(gfid == 0) {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
        if(ucompany == null || uname == null) {
            map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
            return JSON.toJSONString(map);
        }
        if (userService.insertBorrowgfInfo(uname, ucompany, gfid) != 0) {
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);
        } else {
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
    public String updatecfBackTime(int[] cfid) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        int result = userService.updatecfBackTime(cfid);
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
    public String updategfBackTime(int[] gfid) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
//        Borrow borrow = new Borrow();
////        System.out.println(uid + "---------------------" + gfid);
//        borrow.setFileid(gfid);
//        borrow.setUid(uid);

        int result = userService.updategfBackTime(gfid);
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
     * 通过是否传参, 决定是否有id
     * 根据id或者没有id查询
     * flag 0 表示查全部
     * 1 查借阅中的,
     * 2 已经归还的
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/selectBorrowcfInfo/{flag}/{page}")
    @ResponseBody
    public String selectBorrowcfInfo(String uname,String ucompany,@PathVariable int flag,@PathVariable Integer page) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();


        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);

        List<BorrowCFExtends> bcfes = userService.selectBorrowcfInfo(uname,ucompany,flag);

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
    public String selectBorrowgfInfo(String uname,String ucompany,@PathVariable int flag,@PathVariable Integer page) throws Exception {
        //flag = 0 查询所有的, flag = 1 查询 在借阅中的,flag = 2 归还了的
        HashMap<String,Integer> map = new HashMap<>();


        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);

        List<BorrowGFExtends> bgfes = userService.selectBorrowgfInfo(uname,ucompany,flag);

        PageInfo<BorrowGFExtends> pageInfo = new PageInfo<BorrowGFExtends>(bgfes);
        if(bgfes != null  && bgfes.size() > 0) {
            return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
        }else {
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);
        }
    }

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

}