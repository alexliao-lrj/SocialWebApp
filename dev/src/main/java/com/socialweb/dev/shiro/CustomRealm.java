package com.socialweb.dev.shiro;

import com.socialweb.dev.entity.User;
import com.socialweb.dev.repository.UserRepository;
import com.socialweb.dev.service.UserService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class CustomRealm extends AuthorizingRealm {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService){
        this.userService = userService;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String userId = (String)token.getPrincipal();
        User user = userService.getUserByUserId(userId);
        if(user == null){
            return null;
        }
        SimpleAuthenticationInfo sai = new SimpleAuthenticationInfo(user, user.getPassword(), getName());
        return sai;
    }

}
