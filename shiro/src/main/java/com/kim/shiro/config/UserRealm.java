package com.kim.shiro.config;

import com.kim.shiro.domain.User;
import com.kim.shiro.service.UserService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;

/**
 * @author scq
 * @date 2020-08-04 10:38:00
 */
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserService userService;

    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        System.out.println("授权逻辑");
        /**
         *  给资源进行授权
         */
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        /**
         * 添加资源授权字符串
         */
        authorizationInfo.addStringPermissions(Collections.singletonList("user:add"));

        /**
         * 获取当前登录用户
         * 到数据库查询当前登录用户授权的字符串
         */
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        User dbUser = userService.findById(user.getId());
        authorizationInfo.addStringPermission(dbUser.getPerms());
        return authorizationInfo;
    }

    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        /**
         * 判断用户名
         */
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        User user = userService.findByName(usernamePasswordToken.getUsername());

        if (user == null) {
            /**
             * 用户名不存在,UnknownAccountException
             */
            return null;
        }
        // 判断密码
        System.out.println("认证逻辑");
        return new SimpleAuthenticationInfo(user, user.getPassword(), "") ;
    }
}
