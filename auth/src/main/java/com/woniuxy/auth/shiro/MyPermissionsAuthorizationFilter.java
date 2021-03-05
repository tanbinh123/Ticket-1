package com.woniuxy.auth.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 没有权限时,需要 '返回JSON' 而不是 '重定向到页面',
 * 所以重写 权限授权过滤器(PermissionsAuthorizationFilter) 中的 拒绝访问方法(onAccessDenied) ,
 * 让该方法返回JSON数据
 */
public class MyPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        ResponseResult<?> result = new ResponseResult<>(StateEnum.FAIL).setMessage("No Permission");
        ObjectMapper objectMapper = new ObjectMapper();
        response.getWriter().write(objectMapper.writeValueAsString(result));

        return false;
    }
}
