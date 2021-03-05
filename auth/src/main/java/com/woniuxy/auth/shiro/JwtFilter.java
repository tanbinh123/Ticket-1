package com.woniuxy.auth.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

@Slf4j
public class JwtFilter extends AuthenticatingFilter {
    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        // 从请求头获取到 jwt
        String jwt = ((HttpServletRequest) servletRequest).getHeader("jwt");
        // 生成自定义的 JwtToken ,返回给 Shiro 用于之后的认证
        return new JwtToken(jwt);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        if (this.isLoginRequest(servletRequest, servletResponse)) return true;
        boolean allowed = false;
        try {
            allowed = executeLogin(servletRequest, servletResponse);
        } catch (IllegalStateException e) {
            log.error("未找到令牌");
        } catch (Exception e) {
            log.error("登陆发生异常", e);
        }
        return allowed || super.isPermissive(mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        ResponseResult<?> result = new ResponseResult<>(StateEnum.FAIL).setMessage("JWT Expired");
        ObjectMapper objectMapper = new ObjectMapper();
        servletResponse.getWriter().write(objectMapper.writeValueAsString(result));

        return false;
    }
}
