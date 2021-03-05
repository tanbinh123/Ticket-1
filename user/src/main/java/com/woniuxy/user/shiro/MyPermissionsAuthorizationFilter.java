package com.woniuxy.user.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.context.ApplicationContext;

import javax.annotation.Resource;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * 没有权限时,需要 '返回JSON' 而不是 '重定向到页面',
 * 所以重写 权限授权过滤器(PermissionsAuthorizationFilter) 中的 拒绝访问方法(onAccessDenied) ,
 * 让该方法返回JSON数据
 */
public class MyPermissionsAuthorizationFilter extends PermissionsAuthorizationFilter {
    @Resource
    private ApplicationContext applicationContext;
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
//        ResponseResult<?> result = new ResponseResult<>(StateEnum.FAIL).setMessage("No Permission");
//        ObjectMapper objectMapper = new ObjectMapper();
//        response.getWriter().write(objectMapper.writeValueAsString(result));
//
//        return false;
//    }

    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {

        Subject subject = getSubject(request, response);
        String[] perms = (String[]) mappedValue;

        boolean isPermitted = true;
        if (perms != null && perms.length > 0) {
            if (perms.length == 1) {
                if (!subject.isPermitted(perms[0])) {
                    isPermitted = false;
                }
            } else {
                if (!subject.isPermittedAll(perms)) {
                    isPermitted = false;
                }
            }
        }
        response.setContentType("application/json");
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            if (isPermitted) {
                return CheckLastFilter.checkLastFilter((ShiroHttpServletRequest) request,
                        response, objectMapper, applicationContext, this);
            } else {
                ResponseResult<?> result = new ResponseResult<>(StateEnum.FAIL).setMessage("No Permission");
                response.getWriter().write(objectMapper.writeValueAsString(result));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        return isAccessAllowed(request, response, mappedValue);
    }
}
