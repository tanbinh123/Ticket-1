package com.woniuxy.auth.shiro;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.springframework.context.ApplicationContext;

import javax.servlet.Filter;
import javax.servlet.ServletResponse;
import java.io.IOException;
import java.util.Map;

public class CheckLastFilter {
    public static boolean checkLastFilter(ShiroHttpServletRequest servletRequest,
                                          ServletResponse servletResponse,
                                          ObjectMapper objectMapper,
                                          ApplicationContext applicationContext,
                                          Filter filter) throws IOException {
        ResponseResult<?> result;
        // 获取Url
        String uri = servletRequest.getRequestURI();
        // 从容器获取 ShiroFilterFactoryBean
        ShiroFilterFactoryBean shiroFilterFactoryBean = applicationContext.getBean(ShiroFilterFactoryBean.class);
        // 获取过滤器映射关系
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();
        // 获取过滤器
        Map<String, Filter> filters = shiroFilterFactoryBean.getFilters();

        // 获取Uri对应的过滤器
        String uriFilter = filterChainDefinitionMap.get(uri);
        // 获取该Uri最后的过滤器
        if (!uriFilter.contains(",")){
            result = new ResponseResult<>(StateEnum.SUCCESS);
            servletResponse.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }
        String[] split = uriFilter.split(",");
        int i = split[split.length - 1].indexOf("[");
        String lastFilterName;
        if (i != -1) {
            lastFilterName = split[split.length - 1].substring(0, i);
        } else {
            lastFilterName = split[split.length - 1];
        }
        // 判断最后的过滤器是否为当前过滤器
        if (filters.get(lastFilterName).getClass() == filter.getClass()) {
            // 是，返回false
            result = new ResponseResult<>(StateEnum.SUCCESS);
            servletResponse.getWriter().write(objectMapper.writeValueAsString(result));
            return false;
        }
        // 否，继续过滤器链
        return true;
    }
}
