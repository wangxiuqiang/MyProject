package cn.fm.user.controller;

import cn.fm.pojo.CompanyFile;
import cn.fm.pojo.GetFile;
import cn.fm.user.service.UserGetFileService;
import cn.fm.user.service.UserService;
import cn.fm.utils.StatusUtils;
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

import java.util.HashMap;
import java.util.List;

@Controller
@RequestMapping(value = "/worker",produces = "application/json;charset=utf-8" ,method = RequestMethod.POST)
public class UserGetFileController {


    @Autowired
    UserGetFileService userGetFileService;

    @Autowired
    UserService userService;

    /**
     * 录入收文信息
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/infoSubGetFile")
    @ResponseBody
    public String infoSub(@Validated GetFile getFile, BindingResult bindingResult) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        if(bindingResult.hasErrors()) {
            List<ObjectError> allErrors = bindingResult.getAllErrors();
            return JSON.toJSONString(allErrors);
        }
       if(userGetFileService.insertGetFile(getFile) != 0) {
           map.put(StatusUtils.statecode,StatusUtils.SUCCESS_INSERT);
           return JSON.toJSONString(map);

       }
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_INSERT);
       return JSON.toJSONString(map);
    }

    /**
     * 根据单项查收文信息  或多项
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/findTypeGetFiles/{page}")
    @ResponseBody
    public String findTypeFiles(@PathVariable Integer page , GetFile getFile) throws Exception {
        System.out.println(getFile);
        if(getFile.getGfnumber() != 0 || getFile.getGfdatetime() != null || getFile.getGfcompany() != null
                || getFile.getGfclassifyid() != 0 || getFile.getGfname() != null) {
            PageHelper.startPage(page,StatusUtils.PAGE_SIZE);

            List<GetFile> getFiles = userGetFileService.findTypeFiles(getFile);

            PageInfo<GetFile> pageInfo = new PageInfo<GetFile>(getFiles);

            if(getFiles != null && getFiles.size() > 0 ) {

                return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
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
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/findGetFiles/{page}")
    @ResponseBody
    public String findFiles(@PathVariable Integer page) throws Exception {
        PageHelper.startPage(page,StatusUtils.PAGE_SIZE);

        List<GetFile> getFiles = userGetFileService.selectAllGetFile();

        PageInfo<GetFile> pageInfo = new PageInfo<GetFile>(getFiles);
        return  JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
    }

    /**
     * 根据id更新  文件
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
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
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/delGetFile")
    @ResponseBody
    public String delGetFile(String gfid) throws Exception {
        HashMap<String,Integer> map = new HashMap<>();
        String[] gfidstring =gfid.split(",");
        int[] gfids = new int[gfidstring.length];
        for (int i = 0; i < gfidstring.length; i++) {
            gfids[i] = Integer.parseInt(gfidstring[i]);
            System.out.println(gfids[i]);
        }
        if(gfids.length <= 0) {
            map.put(StatusUtils.statecode,StatusUtils.IS_NULL);
            return JSON.toJSONString(map);
        }
        //判断是不是有 还没有归还的
        int gfisborrow[] =userService.selectcfisBorrow(gfids);
        for(int i = 0; i < gfisborrow.length;i++) {
            if(gfisborrow[i] == 2) {
                map.put(StatusUtils.statecode,StatusUtils.IS_BORROW);
                return JSON.toJSONString(map);
            }
        }

        if(userGetFileService.deleteGetFileById(gfids) != 0) {
                map.put(StatusUtils.statecode,StatusUtils.SUCCESS_DEL);
                return JSON.toJSONString(map);
        }
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_DEL);
        return JSON.toJSONString(map);

    }
    /**
     * 根据id找文件
     * @param gfid
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
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
