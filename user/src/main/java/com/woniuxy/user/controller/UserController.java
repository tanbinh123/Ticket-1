package com.woniuxy.user.controller;


import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import com.woniuxy.user.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @Value("${server.port}")
    private Integer port;

    @RequestMapping("/add")
    public String add(){
        log.info(String.valueOf(port));
        return "user-add";
    }

    @ApiOperation("修改用户积分")
    @PostMapping("/integration")
    private ResponseResult<?> updateIntegration(Integer integration, Integer id){
        userService.updateIntegrationById(integration, id);
        return new ResponseResult<>(StateEnum.SUCCESS);
    }
}

