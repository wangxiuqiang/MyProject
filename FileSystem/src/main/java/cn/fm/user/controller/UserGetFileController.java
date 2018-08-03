package cn.fm.user.controller;

import cn.fm.pojo.GetFile;
import cn.fm.user.service.UserGetFileService;
import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
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
    @RequestMapping(value = "/infoSubGetFile")
    @ResponseBody
    public String infoSub( GetFile getFile) throws Exception {
       if(userGetFileService.insertGetFile(getFile) != 0) {
           return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
       }
       return JSON.toJSONString(StatusUtils.FAILURE_INSERT);
    }

    /**
     * 根据单项查收文信息  或多项
     */
    @RequestMapping(value = "/findTypeGetFiles")
    @ResponseBody
    public String findTypeFiles(GetFile getFile) throws Exception {
          System.out.println(getFile);

        return JSON.toJSONString(userGetFileService.findTypeFiles(getFile));
    }

    /**
     * 查找全部的文件信息
     *
     */
    @RequestMapping(value = "/findGetFiles")
    @ResponseBody
    public String findFiles() throws Exception {
        return  JSON.toJSONString(userGetFileService.selectAllGetFile());
    }

    /**
     * 根据id更新  文件
     */
    @RequestMapping(value = "/updateSubGetFile")
    @ResponseBody
    public String updateSubGetFile(GetFile getFile) throws Exception {
        if(userGetFileService.updateGetFileById(getFile) != 0) {
            return JSON.toJSONString(StatusUtils.SUCCESS_INSERT);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_INSERT);
        }
    }
    /**
     * 根据id删除单条记录
     */
    @RequestMapping(value = "/delGetFile")
    @ResponseBody
    public String delGetFile(int gfid) throws Exception {
        if(userGetFileService.deleteGetFileById(gfid) != 0) {
            return JSON.toJSONString(StatusUtils.SUCCESS_DEL);
        }else {
            return JSON.toJSONString(StatusUtils.FAILURE_DEL);
        }
    }
    /**
     *
     */

}
