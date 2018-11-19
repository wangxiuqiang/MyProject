package cn.graduate.subject.controller;

import cn.graduate.subject.pojo.Progress;
import cn.graduate.subject.service.UserService;
import cn.graduate.subject.utils.MailUtils;
import cn.graduate.subject.utils.StatusUtils;
import cn.graduate.subject.utils.UploadUtils;
import com.alibaba.fastjson.JSON;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;

@Controller
@RequestMapping(value = "/user", produces = "application/json;charset=utf-8")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/upwdFirst")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String upwdFirst(String content, String uaccount) throws Exception {
//       把密码初始化,查出邮箱来
        HashMap<String, Integer> map = new HashMap<>();
        String email = userService.selectEmailAndUpdateUpwd(uaccount);
        if (email != null) {
            //发送邮件
            MailUtils.sendMail(1, email, content);
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_SUCCESS);
            return JSON.toJSONString(map);
        }
        map.put(StatusUtils.STATUSCODE, StatusUtils.FIND_FAILURE);
        return JSON.toJSONString(map);

    }

    /**
     * 修改密码,根据学号,不是用户编号
     *
     * @param upwd
     * @param uaccount
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updatePwd")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String updatePwd(String upwd, String uaccount) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        int result = userService.updatePwd(upwd, uaccount);
        if (result > 0) {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_SUCCESS);
        } else {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_FAILURE);
        }
        return JSON.toJSONString(map);
    }

    @RequestMapping(value = "/addSubjectForSelf")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String addSubjectForSelf(int sid, int uid) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        int result = userService.addSubjectForSelf(sid, uid);
        if (result > 0) {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_SUCCESS);
        } else {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_FAILURE);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 删除自己的选题信息
     *
     * @param uid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/delSubjectForSelf")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String delSubjectForSelf(int uid) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        int result = userService.delSubjectForSelf(uid);
        if (result > 0) {
            map.put(StatusUtils.STATUSCODE, StatusUtils.DEL_SUCCESS);
        } else {
            map.put(StatusUtils.STATUSCODE, StatusUtils.DEL_FAILURE);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 发送一封邮件来确认 绑定邮箱
     *
     * @param uemail
     * @param content
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/sendEmailToCheck")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String sendEmailToCheck(String uemail, String content) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        MailUtils.sendMail(3, uemail, content);
        map.put(StatusUtils.STATUSCODE, StatusUtils.FIND_SUCCESS);
        return JSON.toJSONString(map);
    }

    /**
     * 更新邮箱.将邮箱写进数据库
     *
     * @param uemail
     * @param uaccount
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/updateEmail")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String updateEmail(String uemail, String uaccount) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        int result = userService.updateEmail(uemail, uaccount);
        if (result > 0) {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_SUCCESS);
        } else {
            map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_FAILURE);
        }
        return JSON.toJSONString(map);
    }

    /**
     * 提交进度,同时更新    用户的信息
     */
    @RequestMapping(value = "/uploadFile")
    @ResponseBody
    @RequiresRoles(value = "user")
    public String uploadFile(MultipartFile file, String uaccount, String uname, int i, int uid ,int pid) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();

        String result = UploadUtils.upload(file, 1, uaccount, i);
        if (result != null) {
            if (i == 1) {
                int flag = userService.addProgress(result, uid);
                if (flag > 0) {
                    map.put(StatusUtils.STATUSCODE, StatusUtils.UPLOAD_SUCCESS);
                } else {
                    map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_FAILURE);
                }
                return JSON.toJSONString(map);
            } else {
                Progress progress = new Progress();
                if( pid != 0 ) {
                    progress.setPid( pid );
                }
                switch (i) {
                    case 2:
                        progress.setPtwo(result);
                        break;
                    case 3:
                        progress.setPthree(result);
                        break;
                    case 4:
                        progress.setPfour(result);
                        break;
                    case 5:
                        progress.setPfive(result);
                        break;
                    default:
                        break;
                }
                int flag = userService.updateProgress(progress);
                if (flag > 0) {
                    map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_SUCCESS);
                } else {
                    map.put(StatusUtils.STATUSCODE, StatusUtils.UPDATE_FAILURE);
                }
                return JSON.toJSONString( map );
            }

        }
        map.put(StatusUtils.STATUSCODE, StatusUtils.UPLOAD_FAILURE);
        return JSON.toJSONString(map);
    }

//    /**
//     * 录入用户的进度信息
//     * @param pone
//     * @param uid
//     * @return
//     * @throws Exception
//     */
//    @RequestMapping(value = "/insertProgress")
//    @ResponseBody
//    @RequiresRoles(value = "user")
//    public String insertProgress( String pone, int uid ) throws Exception {
//        HashMap<String , Integer> map = new HashMap<>();
//        int result = ;
//
//        return JSON.toJSONString( map );
//    }

    /**
     * 更新用户的进度信息
     */
//    @RequestMapping(value = "/updateProgress")
//    @ResponseBody
//    @RequiresRoles(value = "user")
//    public String updateProgress(Progress progress) throws  Exception {
//        HashMap<String , Integer> map = new HashMap<>();
//        int result = u;
//        if( result > 0 ) {
//            map.put( StatusUtils.STATUSCODE , StatusUtils.UPDATE_SUCCESS ) ;
//        } else  {
//            map.put( StatusUtils.STATUSCODE , StatusUtils.UPDATE_FAILURE );
//        }
//        return JSON.toJSONString( map );
//    }

    /**
     * 查找进度信息,用来渲染
     * @param pid
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/selectProgress")
    @ResponseBody
    @RequiresRoles(value = {"user", "admin"}, logical = Logical.OR)
    public String selectProgress(int pid) throws Exception {
        HashMap<String, Integer> map = new HashMap<>();
        Progress progress = userService.selectProgressByPid(pid);
        if (progress != null) {
            return JSON.toJSONString(progress);
        }
        map.put(StatusUtils.STATUSCODE, StatusUtils.FIND_FAILURE);

        return JSON.toJSONString(map);
    }

}
