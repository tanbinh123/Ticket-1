package com.woniuxy.order.controller;


import com.woniuxy.order.service.UserService;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

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
@Api(tags = "订单相关接口")
@RequestMapping("/order")
public class OrderController {
    @Resource
    RestTemplate restTemplate;
    @Resource
    UserService userService;

    @GetMapping("/add")
    public String add() {
//        String userServiceAdd = "http://USER/user/add";

//        ResponseEntity<String> entity = restTemplate.getForEntity(userServiceAdd, String.class);

//        String body = entity.getBody();
//        System.out.println(body);

        String add = userService.add();

        log.info(add);

        return "order-add";
    }
}

