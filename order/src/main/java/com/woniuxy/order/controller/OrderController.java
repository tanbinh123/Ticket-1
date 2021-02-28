package com.woniuxy.order.controller;


import cn.hutool.core.util.RandomUtil;
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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.validation.constraints.Min;
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

    /**
     * 添加订单
     *
     * @param userId     用户ID
     * @param scheduleId 排片ID
     * @param seatId     座位ID
     * @return 添加结果
     */
    @ApiOperation("添加订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userId", value = "用户ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "scheduleId", value = "排片ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "seatId", value = "座位ID", required = true, paramType = "query")
    })
    @PostMapping("/add")
    private ResponseResult<?> add(@Min(value = 1, message = "用户ID应大于{value}") Integer userId,
                                  @Min(value = 1, message = "排片ID应大于{value}") Integer scheduleId,
                                  @Min(value = 1, message = "座位ID应大于{value}") Integer seatId) {
        scheduleService.updateSeatStatus("0", scheduleId, seatId);
        userService.updateIntegration(2, userId);

        ResponseResult<?> result = JSONObject.parseObject(scheduleService.getById(seatId), ResponseResult.class);
        Seat seat = JSONObject.parseObject(result.getData().toString(), Seat.class);

        LocalDateTime orderTime = LocalDateTime.now();
        LocalDateTime payTime = LocalDateTime.now();

        String orderNo = orderTime.toString().substring(10).replaceAll("-", "") +
                RandomUtil.randomNumbers(10);

        boolean save = orderService.save(new Order().setOrderNo(orderNo).setOrderTime(orderTime).
                setUserId(userId).setScheduleId(scheduleId).
                setPayTime(payTime).setSeat(seat.getRowNo() + " 排 " + seat.getColNo() + " 列"));

        return save ? new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }

    /**
     * 多条件查询订单
     *
     * @param orderNo   订单编号
     * @param startDate 开始时间
     * @param endDate   结束时间
     * @return 订单分页结果
     */
    @ApiOperation("多条件查询订单")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "orderNo", value = "订单编号", paramType = "query"),
            @ApiImplicitParam(name = "startDate", value = "开始时间", paramType = "query"),
            @ApiImplicitParam(name = "endDate", value = "结束时间", paramType = "query")
    })
    @GetMapping("/get")
    private ResponseResult<?> getByNoOrTime(String orderNo,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                                    LocalDateTime startDate,
                                            @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                            @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
                                                    LocalDateTime endDate) {
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        // TODO 获取用户ID加入到wrapper条件

        // 条件存在 订单编号
        if (orderNo != null && !orderNo.trim().equals(""))
            wrapper.eq("order_no", orderNo);
        // 条件存在 起止时间
        if (startDate != null && endDate != null)
            wrapper.between("order_time", startDate, endDate);

        Page<Order> page = new Page<>(1, 2);
        Page<Order> orderPage = orderService.page(page, wrapper);

        return new ResponseResult<>(orderPage);
    }

}

