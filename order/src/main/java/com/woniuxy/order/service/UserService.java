package com.woniuxy.order.service;

import com.woniuxy.order.service.impl.UserServiceImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "user", fallback = UserServiceImpl.class)    //声明式访问服务名,熔断
public interface UserService {

    @GetMapping("/user/test2")
    String test2();

    @PostMapping("/user/integration")
    String updateIntegration(@RequestParam("chgVal") Integer chgVal,
                             @RequestParam("id") Integer id);
}
