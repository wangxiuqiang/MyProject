package cn.graduate.subject.Realm;

import cn.graduate.subject.dao.LoginMapper;
import cn.graduate.subject.pojo.User;
import cn.graduate.subject.service.LoginService;
import cn.graduate.subject.utils.PassWordHelper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class MyRealm extends AuthorizingRealm {

    @Autowired
    LoginService loginService;

    @Autowired
    User user;
    /**
     * 根据用户的principle 获取用户信息
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
        try {
            //获取用户的角色
            simpleAuthorizationInfo.setRoles( loginService.selectRole( user.getRid() ) );
        } catch ( Exception e ) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 通过token获取用户的信息
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String account = (String) token.getPrincipal();

        try {
            user = loginService.selectUser( account );
        } catch ( Exception e) {
            e.printStackTrace();
        }
        if( user.getUname() == null ) {
            throw new UnknownAccountException();
        }
        PassWordHelper pwh = new PassWordHelper();
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(user.getUaccount() , user.getUpwd() ,
                ByteSource.Util.bytes( pwh.getSalt() ) ,
                getName() );
        return info;
    }
}
