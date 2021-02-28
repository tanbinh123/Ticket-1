package com.woniuxy.user.controller;


import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import com.woniuxy.user.service.UserService;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@Slf4j
@RequestMapping("/user")
public class UserController {
    @Resource
    UserService userService;

//    @Value("${server.port}")
//    private Integer port;
//
//    @RequestMapping("/add")
//    public String add() {
//        log.info(String.valueOf(port));
//        return "user-add";
//    }

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

