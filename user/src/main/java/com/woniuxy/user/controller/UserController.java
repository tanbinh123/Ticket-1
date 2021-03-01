package com.woniuxy.user.controller;


import cn.hutool.crypto.SecureUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import com.woniuxy.user.entity.User;
import com.woniuxy.user.service.UserService;
import com.woniuxy.user.validation.Login;
import com.woniuxy.user.validation.Register;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.constraints.Min;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Api(tags = "用户接口")
@RestController
@Slf4j
@Validated
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

    @Value("${server.port}")
    private Integer port;

//    @Value("${account}")
//    private String account;
//
//    @GetMapping("/test")
//    private String test() {
//        return account;
//    }

    @GetMapping("/test2")
    public String test2() {
        log.info(String.valueOf(port));
        try {
            Thread.sleep(6000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "user-add";
    }

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 注册结果
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    private ResponseResult<?> register(@RequestBody @Validated(Register.class) User user) {
        return userService.register(user) ?
                new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }

    /**
     * 用户登录
     *
     * @param user 用户对象
     * @return 登录结果
     */
    @ApiOperation("用户登录")
    @PostMapping("/login")
    private ResponseResult<?> login(@Validated(Login.class) User user) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("account", user.getAccount());
        wrapper.eq("password", SecureUtil.md5(user.getPassword()));
        return userService.count(wrapper) == 1 ?
                new ResponseResult<>(StateEnum.SUCCESS) :
                new ResponseResult<>(StateEnum.FAIL).setMessage("账号或密码错误");
    }

    /**
     * 修改用户积分
     *
     * @param chgVal 积分变化值
     * @param id     用户ID
     * @return 修改结果
     */
    @ApiOperation("修改用户积分")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "chgVal", value = "积分变化值", defaultValue = "2",
                    required = true, paramType = "query"),
            @ApiImplicitParam(name = "id", value = "用户ID", required = true, paramType = "query")})
    @PostMapping("/integration")
    private ResponseResult<?> updateIntegration(Integer chgVal,
                                                @Min(value = 1, message = "用户ID应大于{value}") Integer id) {
        return userService.updateIntegrationById(chgVal, id) ?
                new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }
}

