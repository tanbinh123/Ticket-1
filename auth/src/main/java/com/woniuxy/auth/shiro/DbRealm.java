package com.woniuxy.auth.shiro;

import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.auth.entity.User;
import com.woniuxy.auth.jwt.Audience;
import com.woniuxy.auth.jwt.JwtUtil;
import com.woniuxy.auth.mapper.PermMapper;
import com.woniuxy.auth.mapper.UserMapper;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

public class DbRealm extends AuthorizingRealm {
    @Resource
    private Audience audience;
    @Resource
    private UserMapper userMapper;
    @Resource
    private PermMapper permMapper;

    // 授权
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String jwt = principals.getPrimaryPrincipal().toString();
        Integer id = JwtUtil.getUserId(jwt, audience.getBase64Secret());

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
        permMapper.selectPermsByUserId(id).forEach(perm -> {
            info.addStringPermission(perm.getCode());
        });
        return info;
    }

    // 认证
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) token;
        upToken.setPassword(SecureUtil.md5(String.valueOf(upToken.getPassword())).toCharArray());
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", upToken.getUsername());
        User user = userMapper.selectOne(wrapper);
        if (user == null) throw new UnknownAccountException("Account: " + upToken.getUsername() + " Not Exist");

        return new SimpleAuthenticationInfo(user, user.getPassword(), getName());
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }
}
