package com.woniuxy.user.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.user.entity.User;
import com.woniuxy.user.exception.IntegrationLackException;
import com.woniuxy.user.mapper.UserMapper;
import com.woniuxy.user.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {
    @Resource
    UserMapper userMapper;

    /**
     * 修改用户积分
     *
     * @param chgVal 积分变化值
     * @param id     用户ID
     * @return 修改结果
     */
    @Override
    public Boolean updateIntegrationById(Integer chgVal, Integer id) {
        // 减积分判断积分是否足够
        if (!userMapper.updateIntegrationById(chgVal, id))
            throw new IntegrationLackException();
        return true;
    }
}
