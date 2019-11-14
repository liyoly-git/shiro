package com.springdemo.shiro.shiro;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ly
 * @date 2019/11/14
 */
public class CustomRealm extends AuthorizingRealm {
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //1.获取用户输入的账号
        String username = (String) token.getPrincipal();
        //2.通过username从数据库中查找到user实体
        User user = getUserByUserName(username);
        if (user == null) {
            return null;
        }
        //3.通过SimpleAuthenticationInfo做身份处理
        SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        //4.用户账号状态验证等其他业务操作
        if (!user.getAvailable()) {
            throw new AuthenticationException("该账号已被禁用");
        }
        //5.返回身份处理对象
        return simpleAuthenticationInfo;
    }

    private User getUserByUserName(String username) {
        List<User> users = getUsers();
        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return user;
            }
        }
        return null;
    }

    private List<User> getUsers() {
        List<User> users = new ArrayList<>();
        users.add(new User("admin", "123456", true));
        users.add(new User("common", "123456", false));
        return users;
    }
}
