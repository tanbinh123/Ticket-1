package com.woniuxy.order.service.impl;

import com.woniuxy.order.exception.StatusErrorException;
import com.woniuxy.order.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    @Override
    public String test2() {
//        throw new StatusErrorException();
        return "fail";
    }

    @Override
    public String updateIntegration(Integer chgVal, Integer id) {
//        return "当前服务不可用";
        throw new StatusErrorException();
    }
}
