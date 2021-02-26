package com.woniuxy.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient("user")    //声明式访问服务名
public interface UserService {

    @GetMapping("/user/add")
    String add();
}
