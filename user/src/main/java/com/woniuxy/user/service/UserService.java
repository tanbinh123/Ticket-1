package com.woniuxy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.user.entity.User;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface UserService extends IService<User> {
    Boolean updateIntegrationById(Integer integration, Integer id);

}
