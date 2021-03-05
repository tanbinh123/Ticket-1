package com.woniuxy.user.config;

import com.woniuxy.user.shiro.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.servlet.Filter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@Configuration
public class ShiroConfig {
    @Bean
    public Realm dbRealm() {
        return new DbRealm();
    }

    @Bean
    public Realm jwtRealm() {
        return new JwtRealm();
    }

    @Bean
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();

        // 使用自定义的认证器,解决出现认证异常时吞异常的问题
        MyModularRealmAuthenticator authenticator = new MyModularRealmAuthenticator();
        // 由于使用了自定义认证器,需要由自定义认证器来设置 Realm ,否则会找不到 Realm
        authenticator.setRealms(Arrays.asList(dbRealm(), jwtRealm()));
//        securityManager.setRealms(Arrays.asList(dbRealm(), jwtRealm()));
        // 启用自定义认证器
        securityManager.setAuthenticator(authenticator);

        // 设置授权器,由于权限需要从数据库获取,所以统一交给 DbRealm 授权(可以更改)
        securityManager.setAuthorizer((DbRealm) dbRealm());

        return securityManager;
    }

    @Bean
    public MyPermissionsAuthorizationFilter myPermissionsAuthorizationFilter() {
        return new MyPermissionsAuthorizationFilter();
    }

    @Bean
    public JwtFilter jwtFilter() {
        return new JwtFilter();
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean factoryBean = new ShiroFilterFactoryBean();
        factoryBean.setSecurityManager(securityManager());
        Map<String, Filter> filterMap = new HashMap<>();

//        log.error("=====================>"+new JwtFilter().toString());
//        log.error("=====================>"+jwtFilter().toString());

        filterMap.put("jwt", jwtFilter());
        filterMap.put("perms", myPermissionsAuthorizationFilter());
        factoryBean.setFilters(filterMap);

//        Map<String, String> map = new LinkedHashMap<>();
//        map.put("/user/login", "anon");
//        map.put("/user/menu", "jwt");
//        map.put("/movie/delete", "jwt,perms[movie:delete]");

        // 设置过滤器链
        factoryBean.setFilterChainDefinitionMap(permMap().getObject());

        return factoryBean;
    }

    @Bean
    public PermMapFactoryBean permMap(){
        return new PermMapFactoryBean();
    }

}
