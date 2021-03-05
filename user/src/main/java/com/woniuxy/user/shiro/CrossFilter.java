package com.woniuxy.user.shiro;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CrossFilter implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest) request;
//        if (req.getMethod().equalsIgnoreCase("options")) {
//            return;
//        }
        HttpServletResponse res = (HttpServletResponse) response;
        String origin = req.getHeader("Origin");
        if (!org.springframework.util.StringUtils.isEmpty(origin)) {
            // 带cookie的时候，origin必须是全匹配，不能使用*
            res.addHeader("Access-Control-Allow-Origin", origin);
        }
        res.addHeader("Access-Control-Allow-Methods", "*");
        String headers = req.getHeader("Access-Control-Request-Headers");
        // 支持所有自定义头
        if (!org.springframework.util.StringUtils.isEmpty(headers)) {
            res.addHeader("Access-Control-Allow-Headers", headers);
        }
        res.addHeader("Access-Control-Max-Age", "3600");
        res.addHeader("Access-Control-Allow-Credentials", "false");

        String exposeHeaders = "access-control-expose-headers";
        // if (!res.containsHeader(exposeHeaders))
        res.setHeader(exposeHeaders, "*");
        // 处理options请求
        if (req.getMethod().toUpperCase().equals("OPTIONS")) {
            return;
        }
        chain.doFilter(request, response);
    }
}
