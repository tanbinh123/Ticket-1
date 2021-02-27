package com.woniuxy.cinema.controller;


import com.woniuxy.cinema.service.ScheduleService;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Api(tags = "排片接口")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Resource
    ScheduleService scheduleService;

    @ApiOperation("通过ID获取座位状态")
    @GetMapping("/seat-status/{id}")
    private ResponseResult<?> getSeatStatus(@PathVariable Integer id) {

        return new ResponseResult<>(scheduleService.getSeatStatusById(id));
    }

    @PostMapping("/seat-status")
    private ResponseResult<?> updateSeatStatus(String status, Integer scheduleId, Integer seatId) {
        return scheduleService.updateSeatStatus(status, scheduleId, seatId) ?
                new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL).setCode(501);
    }

}

