package cn.fm.user.controller;
/**
 * 用户设置密码和 数据库备份
 */

import cn.fm.user.service.UserRegService;
import cn.fm.utils.MysqlBackupUtils;
import cn.fm.utils.MysqlRecoverUtils;
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
import org.springframework.web.multipart.MultipartFile;

@Controller
@RequestMapping(value = "/worker" , produces = "application/json;charset=utf8")
public class UserController {
    @Autowired
    UserRegService userRegService;

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
    public String reg(@RequestBody String upwd, @RequestBody String code) throws Exception {
        if(userRegService.updateUserpwd(upwd,code) != 0){
            return JSON.toJSONString(StatusUtils.SUCCESS_REG);
        }
        return JSON.toJSONString(StatusUtils.FAILURE_REG);
    }

    /**
     * 备份数据库
     * @return
     * @throws Exception
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/backupDatabase")
    @ResponseBody
    public String backupDatabase() throws Exception {

        if(MysqlBackupUtils.backup() == 0) {
            return JSON.toJSONString("备份成功");
        }else {
            return JSON.toJSONString("备份失败");

        }

    }
    /**
     * 恢复数据库
     */
    @RequiresRoles(value = {"admin","user"},logical = Logical.OR)
    @RequestMapping(value = "/recoverDatabase")
    @ResponseBody
    public String recoverDatabase(MultipartFile file) throws Exception {

        if(MysqlRecoverUtils.recover(file) == 0) {
            return JSON.toJSONString("恢复成功");
        }else {
            return JSON.toJSONString("恢复失败");

        }

    }

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
