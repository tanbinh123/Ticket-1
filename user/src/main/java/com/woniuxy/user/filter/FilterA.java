package com.woniuxy.user.filter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

//@WebFilter("/*")
public class FilterA implements Filter {

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException {
        response.getWriter().write("ok");
        response.getWriter().flush();
    }
}
