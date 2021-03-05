package com.woniuxy.user.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtFilter extends AuthenticatingFilter {
    // 获取容器,以便在调用方法时获取到容器中的 Bean
    @Resource
    private ApplicationContext applicationContext;

    @Override
    protected AuthenticationToken createToken(ServletRequest servletRequest, ServletResponse servletResponse) {
        // 从请求头获取到 jwt
        String jwt = ((HttpServletRequest) servletRequest).getHeader("jwt");
        // 生成自定义的 JwtToken ,返回给 Shiro 用于之后的认证
        return new JwtToken(jwt);
    }

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object mappedValue) {
        if (this.isLoginRequest(servletRequest, servletResponse))
            return true;
        boolean allowed = false;
        try {
            allowed = executeLogin(servletRequest, servletResponse);
        } catch (IllegalStateException e) {
            log.error("未找到令牌");
        } catch (Exception e) {
            log.error("登陆发生异常", e);
        }

        servletResponse.setContentType("application/json");
        ObjectMapper objectMapper = new ObjectMapper();
        ResponseResult<?> result;
        try {
            if (allowed) {
                return CheckLastFilter.checkLastFilter((ShiroHttpServletRequest) servletRequest,
                        servletResponse, objectMapper, applicationContext, this);
            } else {
                result = new ResponseResult<>(StateEnum.FAIL).setMessage("JWT Expired");
                servletResponse.getWriter().write(objectMapper.writeValueAsString(result));
                return false;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        return isAccessAllowed(request, response, mappedValue);
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        ResponseResult<?> result = new ResponseResult<>(StateEnum.FAIL).setMessage("JWT Expired");
        ObjectMapper objectMapper = new ObjectMapper();
        servletResponse.getWriter().write(objectMapper.writeValueAsString(result));

        return false;
    }
}
