package cn.fm.user.controller;

import cn.fm.pojo.GetFile;
import cn.fm.user.service.UserGetFileService;
import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(value = "/worker",produces = "application/json;charset=utf-8" )
public class UserGetFileController {


    @Autowired
    UserGetFileService userGetFileService;

    /**
     * 录入收文信息
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/infoSubGetFile")
    @ResponseBody
    public String infoSub(@RequestBody GetFile getFile) throws Exception {

       if(userGetFileService.insertGetFile(getFile) != 0) {
           return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
       }
       return JSON.toJSONString(StatusUtils.FAILURE_INSERT);
    }

    /**
     * 根据单项查收文信息  或多项
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/findTypeGetFiles")
    @ResponseBody
    public String findTypeFiles(@RequestBody GetFile getFile) throws Exception {
        System.out.println(getFile);
        if(getFile.getGfnumber() != 0 || getFile.getGfdatetime() != null || getFile.getGfcompany() != null
                || getFile.getGfclassifyid() != 0 || getFile.getGfname() != null) {
            return JSON.toJSONString(userGetFileService.findTypeFiles(getFile));
        }else{
            return JSON.toJSONString(StatusUtils.IS_NULL);
        }



    }

    /**
     * 查找全部的文件信息
     *
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/findGetFiles")
    @ResponseBody
    public String findFiles() throws Exception {
        return  JSON.toJSONString(userGetFileService.selectAllGetFile());
    }

    /**
     * 根据id更新  文件
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/updateSubGetFile")
    @ResponseBody
    public String updateSubGetFile(@RequestBody GetFile getFile) throws Exception {
        if(getFile == null) {
            return JSON.toJSONString(StatusUtils.IS_NULL);
        }
        if(userGetFileService.updateGetFileById(getFile) != 0) {
            return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_INSERT);
        }
    }
    /**
     * 根据id删除单条记录
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/delGetFile")
    @ResponseBody
    public String delGetFile(@RequestBody int gfid) throws Exception {
        if(gfid == 0) {
            return JSON.toJSONString(StatusUtils.IS_NULL);
        }
        if(userGetFileService.deleteGetFileById(gfid) != 0) {
            return JSON.toJSONString(StatusUtils.SUCCESS_DEL);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_DEL);
        }
    }
    /**
     *
     */
    /**
     * 用来捕捉 是不是没有登录或者没有权限
     * @return
     */

    @ExceptionHandler({UnauthenticatedException.class})
    @ResponseBody
    public String no_sujectLogin() {
        return JSON.toJSONString(StatusUtils.FAILURE_LOGIN);
    }
    @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public String nohave_role_permission() {
        return JSON.toJSONString(StatusUtils.NO_ROLE_PERMISSION);
    }


}
