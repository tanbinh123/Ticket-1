package com.woniuxy.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.user.entity.User;
import com.woniuxy.user.mapper.UserMapper;
import com.woniuxy.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

    @Override
    public Boolean updateIntegrationById(Integer integration, Integer id) {
        return userMapper.updateIntegrationById(integration, id);
    }
}
