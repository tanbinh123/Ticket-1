package com.woniuxy.auth.controller;

import com.woniuxy.auth.entity.User;
import com.woniuxy.auth.jwt.Audience;
import com.woniuxy.auth.jwt.JwtUtil;
import com.woniuxy.auth.service.UserService;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.utils.URIBuilder;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import java.net.URI;
import java.net.URISyntaxException;

@Slf4j
//@CrossOrigin(exposedHeaders = "Authorization")
@RestController
@RequestMapping("/auth")
public class AuthController {
    @Value("${server.port}")
    private int port;
    @Resource
    private Audience audience;
    @Resource
    private UserService userService;

    @PostMapping("/login")
    public ResponseResult<?> login(@NotBlank(message = "账号不能为空") String account,
                                   @NotBlank(message = "密码不能为空") String password,
                                   HttpServletRequest request,
                                   HttpServletResponse response) {
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
        // 生成JWT
        String jwt = JwtUtil.createJWT(user.getId(), account, audience);

        // 支持所有自定义头
        String headers = request.getHeader("Access-Control-Request-Headers");
        if (!org.springframework.util.StringUtils.isEmpty(headers)) {
            response.addHeader("Access-Control-Allow-Headers", headers);
        }
        response.addHeader("Access-Control-Max-Age", "3600");
        response.addHeader("Access-Control-Allow-Credentials", "false");

        String exposeHeaders = "access-control-expose-headers";
        // if (!res.containsHeader(exposeHeaders))
        response.setHeader(exposeHeaders, "*");

        // 设置响应头
        response.setHeader("Authorization", jwt);

        return new ResponseResult<>(StateEnum.SUCCESS);
    }

    /**
     * 获取用户菜单
     *
     * @return 用户菜单
     */
    @ApiOperation("获取用户菜单")
    @GetMapping("/menu")
    public ResponseResult<?> getMenu(HttpServletRequest request) {
        Integer id = JwtUtil.getUserId(request.getHeader("jwt"), audience.getBase64Secret());

        return new ResponseResult<>(userService.getMenu(id));
    }

    /**
     * 检查授权
     *
     * @param url 请求URL
     * @param jwt JWT密钥
     * @return 检查结果
     * @throws URISyntaxException URI语法异常
     */
    @RequestMapping("/check")
    public ResponseResult<?> auth(String url, String jwt) throws URISyntaxException {
        //发送HTTP请求到本项目，会经过Shiro过滤器
        URI uri = new URIBuilder().setScheme("http").setHost("localhost").setPort(port).setPath(url).build();
        // build http headers
        HttpHeaders headers = new HttpHeaders();
        headers.add("jwt", jwt);
        RestTemplate restTemplate = new RestTemplate();
        // send request and get response
        ResponseResult<?> result = restTemplate.postForObject(uri, new HttpEntity<String>(headers), ResponseResult.class);
        log.info(String.valueOf(result));
        return result;
    }

}
