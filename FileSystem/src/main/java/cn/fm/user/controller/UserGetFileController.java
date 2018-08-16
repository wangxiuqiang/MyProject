package cn.fm.user.controller;

import cn.fm.pojo.GetFile;
import cn.fm.user.service.UserGetFileService;
import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/worker",produces = "application/json;charset=utf-8" )
public class UserGetFileController {


    @Autowired
    UserGetFileService userGetFileService;

    /**
     * 录入收文信息
     */
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/infoSubGetFile")
    @ResponseBody
    public String infoSub(@Validated GetFile getFile, BindingResult bindingResult) throws Exception {
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return JSON.toJSONString(allErrors);
        }
       if(userGetFileService.insertGetFile(getFile) != 0) {
           return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
       }
       return JSON.toJSONString(StatusUtils.FAILURE_INSERT);
    }

    /**
     * 根据单项查收文信息  或多项
     */
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/findTypeGetFiles")
    @ResponseBody
    public String findTypeFiles( GetFile getFile) throws Exception {
        System.out.println(getFile);
        if(getFile.getGfnumber() != 0 || getFile.getGfdatetime() != null || getFile.getGfcompany() != null
                || getFile.getGfclassifyid() != 0 || getFile.getGfname() != null) {
            if(userGetFileService.findTypeFiles(getFile) != null) {
                return JSON.toJSONString(userGetFileService.findTypeFiles(getFile), SerializerFeature.DisableCircularReferenceDetect);
            }
            else {
                HashMap<String,Integer> map = new HashMap<>();
                map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
                return JSON.toJSONString(map);
            }

        }else{
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
            return JSON.toJSONString(map);
        }



    }

    /**
     * 查找全部的文件信息
     *
     */
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/findGetFiles")
    @ResponseBody
    public String findFiles() throws Exception {
        return  JSON.toJSONString(userGetFileService.selectAllGetFile(), SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 根据id更新  文件
     */
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/updateSubGetFile")
    @ResponseBody
    public String updateSubGetFile( GetFile getFile) throws Exception {
        if(getFile == null) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
            return JSON.toJSONString(map);
        }
        if(userGetFileService.updateGetFileById(getFile) != 0) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
            return JSON.toJSONString(map);

        }else {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
            return JSON.toJSONString(map);
        }
    }
    /**
     * 根据id删除单条记录
     */
//    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/delGetFile")
    @ResponseBody
    public String delGetFile( int gfid) throws Exception {
        if(gfid == 0) {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
            return JSON.toJSONString(map);
        }
        if(userGetFileService.deleteGetFileById(gfid) != 0) {
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
     * 根据id找文件
     * @param gfid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectGetFileById/{gfid}")
    @ResponseBody
    public String selectGetFileById(@PathVariable int gfid) throws Exception{
        if (userGetFileService.selectGetFileById(gfid) != null) {
            return JSON.toJSONString(userGetFileService.selectGetFileById(gfid), SerializerFeature.DisableCircularReferenceDetect);
        }else {
            HashMap<String,Integer> map = new HashMap<>();
            map.put(StatusUtils.statecode,StatusUtils.FAILURE_FIND);
            return JSON.toJSONString(map);

        }
    }
}
