package com.woniuxy.order.service.impl;

import com.woniuxy.order.exception.StatusErrorException;
import com.woniuxy.order.service.UserService;
import org.springframework.cloud.openfeign.FallbackFactory;
import org.springframework.stereotype.Service;

@Service
public class UserServiceFallbackFactory implements FallbackFactory<UserService> {
    @Override
    public UserService create(Throwable cause) {
        throw new StatusErrorException();
    }
}
