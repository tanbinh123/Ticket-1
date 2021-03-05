package com.woniuxy.cinema.controller;


import com.woniuxy.cinema.entity.Seat;
import com.woniuxy.cinema.entity.dto.BatchAddSeatDto;
import com.woniuxy.cinema.service.SeatService;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Api(tags = "座位接口")
@RestController
@RequestMapping("/seat")
public class SeatController {
    @Resource
    SeatService seatService;

    /**
     * 批量添加座位
     *
     * @param batchAddSeatDto 座位对象,批量添加信息 DTO
     * @return 添加结果
     */
    @ApiOperation("批量添加座位")
    @PostMapping("/batch-add")
    private ResponseResult<?> batchAdd(@RequestBody BatchAddSeatDto batchAddSeatDto) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 1; i < batchAddSeatDto.getRowSize() + 1; i++) {
            for (int j = 1; j < batchAddSeatDto.getColSize() + 1; j++) {
                seats.add(new Seat().
                        setHallId(batchAddSeatDto.getSeat().getHallId()).
                        setRowNo(i).setColNo(j));
            }
        }

        return seatService.saveBatch(seats) ?
                new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }

    @ApiOperation("通过ID获取座位")
    @GetMapping("/get/{id}")
    private ResponseResult<?> getById(@PathVariable Integer id) {
        Seat seat = seatService.getById(id);
        if (seat != null) return new ResponseResult<>(seat);
        return new ResponseResult<>(StateEnum.FAIL);
    }
}

