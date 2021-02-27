package com.woniuxy.order.controller;


import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import com.woniuxy.order.entity.Order;
import com.woniuxy.order.entity.Seat;
import com.woniuxy.order.service.OrderService;
import com.woniuxy.order.service.ScheduleService;
import com.woniuxy.order.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import java.time.LocalDateTime;

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
@Api(tags = "订单接口")
@RequestMapping("/order")
public class OrderController {
    @Resource
    OrderService orderService;
    @Resource
    ScheduleService scheduleService;

    @Resource
    RestTemplate restTemplate;
    @Resource
    UserService userService;

//    @GetMapping("/add")
//    public String add() {
////        String userServiceAdd = "http://USER/user/add";
//
////        ResponseEntity<String> entity = restTemplate.getForEntity(userServiceAdd, String.class);
//
////        String body = entity.getBody();
////        System.out.println(body);
//
//        String add = userService.add();
//
//        log.info(add);
//
//        return "order-add";
//    }

    @ApiOperation("添加订单")
    @PostMapping("/add")
    private ResponseResult<?> add(Integer userId, Integer scheduleId, Integer seatId) {
        System.out.println(scheduleService.updateSeatStatus("0", scheduleId, seatId));
        System.out.println(userService.updateIntegration(2, userId));

        ResponseResult<?> result = JSONObject.parseObject(scheduleService.getById(seatId), ResponseResult.class);
        Seat seat = JSONObject.parseObject(result.getData().toString(), Seat.class);

        String orderNo = "100010";
        LocalDateTime orderTime = LocalDateTime.now();
        LocalDateTime payTime = LocalDateTime.now();

        boolean save = orderService.save(new Order().setOrderNo(orderNo).setOrderTime(orderTime).
                setUserId(userId).setScheduleId(scheduleId).
                setPayTime(payTime).setSeat(seat.getRowNo() + " 排 " + seat.getColNo() + " 列"));

        return save ? new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }

    @ApiOperation("查询订单")
    @GetMapping("/get")
    private ResponseResult<?> getByNoOrTime(String orderNo,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                                    LocalDateTime startDate,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                                    LocalDateTime endDate) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        if (orderNo != null && !orderNo.trim().equals("")) {
            wrapper.eq("order_no", orderNo);
        }
        if (startDate != null && endDate != null) {
            wrapper.between("order_time", startDate, endDate);
        }
        Page<Order> page = new Page<>(1, 2);
        Page<Order> orderPage = orderService.page(page, wrapper);
        return new ResponseResult<>(orderPage);

    }

}

