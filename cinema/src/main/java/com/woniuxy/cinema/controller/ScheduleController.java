package com.woniuxy.cinema.controller;


import com.woniuxy.cinema.service.ScheduleService;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "排片接口")
@RestController
@RequestMapping("/schedule")
public class ScheduleController {
    @Resource
    ScheduleService scheduleService;

    /**
     * 通过ID获取排片座位状态
     *
     * @param id 排片ID
     * @return 排片座位状态
     */
    @ApiOperation("通过ID获取座位状态")
    @GetMapping("/seat-status/{id}")
    private ResponseResult<?> getSeatStatus(@PathVariable @Min(value = 1, message = "排片ID应大于{value}") Integer id) {

        return new ResponseResult<>(scheduleService.getSeatStatusById(id));
    }

    /**
     * 修改排片座位状态
     *
     * @param status     目标状态
     * @param scheduleId 排片ID
     * @param seatId     座位ID
     * @return 修改结果
     */
    @ApiOperation("修改排片座位状态")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "status", value = "目标状态", required = true, paramType = "query"),
            @ApiImplicitParam(name = "scheduleId", value = "排片ID", required = true, paramType = "query"),
            @ApiImplicitParam(name = "seatId", value = "座位ID", required = true, paramType = "query")
    })
    @PostMapping("/seat-status")
    private ResponseResult<?> updateSeatStatus(String status,
                                               @Min(value = 1, message = "排片ID应大于{value}") Integer scheduleId,
                                               @Min(value = 1, message = "座位ID应大于{value}") Integer seatId) {
        return scheduleService.updateScheduleSeatStatus(status, scheduleId, seatId) ?
                new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }

}

