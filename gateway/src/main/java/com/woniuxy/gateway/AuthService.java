package com.woniuxy.gateway;

import com.woniuxy.common.utils.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("user")
public interface AuthService {
    @RequestMapping("/auth")
    ResponseResult<?> auth(@RequestParam String url, @RequestParam String jwt);

    @PostMapping("/user/login")
    ResponseResult<?> login(@RequestParam String account, @RequestParam String password);
}
