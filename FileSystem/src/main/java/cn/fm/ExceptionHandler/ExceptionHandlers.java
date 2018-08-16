package cn.fm.ExceptionHandler;

import cn.fm.utils.StatusUtils;
import com.alibaba.fastjson.JSON;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 异常处理类
 */
@ControllerAdvice
public class ExceptionHandlers {

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


    /**
     * 登录失败
     */
    @ExceptionHandler({IncorrectCredentialsException.class})
    @ResponseBody
    public String pwdoremail_Failure() {
        return JSON.toJSONString(StatusUtils.FAILURE_LOGIN);
    }

    @ExceptionHandler({UnknownAccountException.class})
    @ResponseBody
    public String emailorpwd_Failure() {
        return JSON.toJSONString(StatusUtils.FAILURE_LOGIN);
    }

    @ExceptionHandler({LockedAccountException.class})
    @ResponseBody
    public String lock_account(){
        return JSON.toJSONString(StatusUtils.LOCK_INFO);
    }

    /**
     * 主键存在,表示已经录入了该数据 ,,录入数据失败
     * @return
     */
    @ExceptionHandler({MySQLIntegrityConstraintViolationException.class})
    @ResponseBody
    public String insert_before() {
        return JSON.toJSONString(StatusUtils.EXIST_CONTENT);
    }
}
