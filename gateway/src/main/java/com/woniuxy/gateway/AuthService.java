package com.woniuxy.gateway;

import com.woniuxy.common.utils.ResponseResult;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("auth")
public interface AuthService {
    @RequestMapping("/auth/check")
    ResponseResult<?> auth(@RequestParam String url, @RequestParam String jwt);

    @PostMapping("/auth/login")
    ResponseResult<?> login(@RequestParam String account, @RequestParam String password);
}
