package com.woniuxy.user.controller;


import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import com.woniuxy.user.entity.User;
import com.woniuxy.user.jwt.Audience;
import com.woniuxy.user.jwt.JwtUtil;
import com.woniuxy.user.service.UserService;
import com.woniuxy.user.validation.Register;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

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
//@Validated
@RequestMapping("/user")
public class UserController {
    @Resource
    private Audience audience;
    @Resource
    private UserService userService;

//    @Value("${server.port}")
//    private Integer port;

//    @Value("${account}")
//    private String account;
//
//    @GetMapping("/test")
//    private String test() {
//        return account;
//    }
//
//    @GetMapping("/test2")
//    public String test2() {
//        log.info(String.valueOf(port));
//        try {
//            Thread.sleep(6000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        return "user-add";
//    }

    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 注册结果
     */
    @ApiOperation("用户注册")
    @PostMapping("/register")
    public ResponseResult<?> register(@RequestBody @Validated(Register.class) User user) {
        return userService.register(user) ?
                new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }

    /**
     * 用户登录
     *
     * @param account  账号
     * @param password 密码
     * @return 登录结果
     */
    @ApiOperation("用户登录")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "account", value = "账号", required = true, paramType = "query"),
            @ApiImplicitParam(name = "password", value = "密码", required = true, paramType = "query")})
    @PostMapping("/login")
    public ResponseResult<?> login(@NotBlank(message = "账号不能为空") String account,
                                   @NotBlank(message = "密码不能为空") String password,
                                   HttpServletResponse response) {
        // 验证账号密码
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.eq("account", account);
//        wrapper.eq("password", SecureUtil.md5(password));
//        Integer id = userService.getOne(wrapper).getId();
//        if (id == null) return new ResponseResult<>(StateEnum.FAIL).setMessage("账号或密码错误");

        //
        UsernamePasswordToken token = new UsernamePasswordToken(account, password);
        Subject subject = SecurityUtils.getSubject();

        try {
            subject.login(token);
        } catch (UnknownAccountException e){
            return new ResponseResult<>(StateEnum.FAIL).setMessage("Account Incorrect");
        } catch (IncorrectCredentialsException e){
            return new ResponseResult<>(StateEnum.FAIL).setMessage("Password Incorrect");
        }

        User user = (User) subject.getPrincipal();

        String jwt = JwtUtil.createJWT(user.getId(), account, audience);


        // 生成JWT
//        String jwt = JwtUtil.createJWT(id, account, audience);
        // 设置响应头
        response.setHeader("Authorization", jwt);

        return new ResponseResult<>(StateEnum.SUCCESS);
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
    public ResponseResult<?> updateIntegration(Integer chgVal,
                                               @Min(value = 1, message = "用户ID应大于{value}") Integer id) {
        return userService.updateIntegrationById(chgVal, id) ?
                new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }

    @ApiOperation("获取用户菜单")
    @GetMapping("/menu")
    public ResponseResult<?> getMenu(HttpServletRequest request) {
        Integer id = JwtUtil.getUserId(request.getHeader("jwt"), audience.getBase64Secret());

        return new ResponseResult<>(userService.getMenu(id));
    }
}

