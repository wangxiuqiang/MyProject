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

import java.util.HashMap;

/**
 * 异常处理类
 */
@ControllerAdvice
public class ExceptionHandlers {

    /**
     * 用来捕捉 是不是没有登录或者没有权限
     * @return
     */


    HashMap<String ,Integer> map = new HashMap<>();

    @ExceptionHandler({UnauthenticatedException.class})
    @ResponseBody
    public String no_sujectLogin() {
        map.clear();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_LOGIN);
        return JSON.toJSONString(map);
    }

    @ExceptionHandler({UnauthorizedException.class})
    @ResponseBody
    public String nohave_role_permission() {
        map.clear();
        map.put(StatusUtils.statecode,StatusUtils.NO_ROLE_PERMISSION);
        return JSON.toJSONString(map);
    }


    /**
     * 登录失败
     */
    @ExceptionHandler({IncorrectCredentialsException.class})
    @ResponseBody
    public String pwdoremail_Failure() {
        map.clear();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_LOGIN);
        return JSON.toJSONString(map);
    }

    @ExceptionHandler({UnknownAccountException.class})
    @ResponseBody
    public String emailorpwd_Failure() {
        map.clear();
        map.put(StatusUtils.statecode,StatusUtils.FAILURE_LOGIN);
        return JSON.toJSONString(map);
    }

    @ExceptionHandler({LockedAccountException.class})
    @ResponseBody
    public String lock_account(){
        map.clear();
        map.put(StatusUtils.statecode,StatusUtils.LOCK_INFO);
        return JSON.toJSONString(map);
    }

    /**
     * 主键存在,表示已经录入了该数据 ,,录入数据失败
     * @return
     */
    @ExceptionHandler({MySQLIntegrityConstraintViolationException.class})
    @ResponseBody
    public String insert_before() {
        map.clear();
        map.put(StatusUtils.statecode,StatusUtils.EXIST_CONTENT);
        return JSON.toJSONString(map);
    }
}
