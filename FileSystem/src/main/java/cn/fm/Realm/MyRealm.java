package cn.fm.Realm;

import cn.fm.admin.dao.AdminMapper;
import cn.fm.admin.service.AdminService;
import cn.fm.pojo.User;
import cn.fm.utils.PassWordHelper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

public class MyRealm extends AuthorizingRealm {

    private User user = null;
    @Autowired
    AdminMapper adminMapper;

    @Autowired
    AdminService adminService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String email = (String) principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();

        try {
            user = adminMapper.findUserByEmail(email);
            simpleAuthorizationInfo.setStringPermissions(adminService.findPermissions(user.getUid()));
            simpleAuthorizationInfo.setRoles(adminService.findRoles(user.getUid()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return simpleAuthorizationInfo;

    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
//        获取token中的信息
         String email  = (String)token.getPrincipal();
         System.out.println(email + "-----------------------------------------");
// 从数据库中获取相应的内容
        try {
          user  = adminMapper.findUserByEmail(email);
        } catch (Exception e) {
            e.printStackTrace();
        }
//        如果没找到 就抛出 找不到异常
        if( user == null) {
            throw new UnknownAccountException();
        }
        if(user.getState() == 0) {
            throw new LockedAccountException();
        }
        PassWordHelper pwh = new PassWordHelper();
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(user.getUemail(),
                user.getUpwd(),
                ByteSource.Util.bytes(pwh.getSalt()),
                getName());

        return authenticationInfo;
    }
}
