package com.woniuxy.order.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user")    //声明式访问服务名
public interface UserService {

    @GetMapping("/user/add")
    String add();

    @PostMapping("/user/integration")
    String updateIntegration(@RequestParam("chgVal") Integer chgVal,
                             @RequestParam("id") Integer id);
}
