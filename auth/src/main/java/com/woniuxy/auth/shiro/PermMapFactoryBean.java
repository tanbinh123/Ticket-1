package com.woniuxy.auth.shiro;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.auth.entity.Perm;
import com.woniuxy.auth.mapper.PermMapper;
import org.springframework.beans.factory.FactoryBean;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * TODO
 */
public class PermMapFactoryBean implements FactoryBean<Map<String, String>> {
    @Resource
    private PermMapper permMapper;

    @Override
    public Map<String, String> getObject() {
        Map<String, String> map = new LinkedHashMap<>();
        // 添加静态过滤器
        map.put("/user/login", "anon");
        map.put("/user/menu", "jwt");
        // 添加从数据库获取的 权限过滤器
        QueryWrapper<Perm> wrapper = new QueryWrapper<>();
        wrapper.eq("type", "a");
        permMapper.selectList(wrapper).forEach(perm -> {
            map.put(perm.getLink(), "jwt,perms[" + perm.getCode() + "]");
        });

        return map;
    }

    /**
     * 重写 FactoryBean 返回的 Bean 的类型
     * @return Map.class
     */
    @Override
    public Class<?> getObjectType() {
        return Map.class;
    }
}
