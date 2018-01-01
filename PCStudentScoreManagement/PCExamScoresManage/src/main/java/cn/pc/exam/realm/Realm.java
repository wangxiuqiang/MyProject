package cn.pc.exam.realm;

import cn.pc.exam.pojo.Admin;
import cn.pc.exam.service.Impl.LoginSelectServiceImpl;
import cn.pc.exam.service.LoginSelectService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

public class Realm extends AuthorizingRealm {

    @Autowired
    LoginSelectServiceImpl loginSelectService;

//    授予权限和角色
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    //检查用户是否登录
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
/*
*
* 首先获取传过来的token中的name  ,也就是用户名,通过用户名查找密码
* */
        String name = (String )token.getPrincipal();

        Admin admin = null ;
        try{
            admin = loginSelectService.queryAdminIDAndPassWd(name);
            if(admin != null) {
                /**

                 **/
                AuthenticationInfo authcInfo = new SimpleAuthenticationInfo(admin.getAname(), admin.getApassword(), "xx");
                return authcInfo;
            }else {
                return null;
            }
        }catch ( Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
