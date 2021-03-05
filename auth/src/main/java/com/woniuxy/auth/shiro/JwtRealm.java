package com.woniuxy.auth.shiro;

import com.woniuxy.auth.jwt.Audience;
import com.woniuxy.auth.jwt.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class JwtRealm extends AuthorizingRealm {
    @Resource
    private Audience audience;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        String jwt = jwtToken.getPrincipal().toString();
        if (!JwtUtil.parseJwt(jwt, audience.getBase64Secret()))
            throw new AuthenticationException("JWT校验异常");
        return new SimpleAuthenticationInfo(jwt, jwt, getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }
}
