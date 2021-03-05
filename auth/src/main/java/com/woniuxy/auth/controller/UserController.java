package com.woniuxy.auth.controller;

import com.woniuxy.auth.entity.User;
import com.woniuxy.auth.jwt.Audience;
import com.woniuxy.auth.jwt.JwtUtil;
import com.woniuxy.auth.service.UserService;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private Audience audience;
    @Resource
    private UserService userService;

    @PostMapping("/login")
    private ResponseResult<?> login(String account, String password, HttpServletResponse response){
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

        response.setHeader("Authorization", jwt);

        return new ResponseResult<>(StateEnum.SUCCESS);
    }

    @ApiOperation("获取用户菜单")
    @GetMapping("/menu")
    public ResponseResult<?> getMenu(HttpServletRequest request) {
        Integer id = JwtUtil.getUserId(request.getHeader("jwt"), audience.getBase64Secret());

        return new ResponseResult<>(userService.getMenu(id));
    }

}
