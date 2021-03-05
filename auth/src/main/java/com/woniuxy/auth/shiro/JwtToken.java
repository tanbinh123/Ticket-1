package com.woniuxy.auth.shiro;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * 由于 Shiro 原生不支持 JWT Token ,
 * 所以使用自定义的 JwtToken ,继承自认证令牌(AuthenticationToken)
 */
public class JwtToken implements AuthenticationToken {
    private final String jwt;

    public JwtToken(String jwt) {
        this.jwt = jwt;
    }

    @Override
    public Object getPrincipal() {
        return jwt;
    }

    @Override
    public Object getCredentials() {
        return jwt;
    }
}
