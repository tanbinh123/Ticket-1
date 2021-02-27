package com.woniuxy.cinema.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.cinema.entity.Hall;
import com.woniuxy.cinema.exception.HallExistException;
import com.woniuxy.cinema.service.HallService;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.common.utils.ResponseResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Api(tags = "放映厅接口")
@RestController
@RequestMapping("/hall")
public class HallController {
    @Resource
    HallService hallService;

    /**
     * 添加放映厅
     *
     * @param hall 放映厅对象
     * @return 添加结果
     */
    @ApiOperation("添加放映厅")
    @PostMapping("/add")
    private ResponseResult<?> add(@RequestBody Hall hall) {
        // 检查是否有重名放映厅
        QueryWrapper<Hall> wrapper = new QueryWrapper<>();
        wrapper.eq("cinema_id", hall.getCinemaId());
        wrapper.eq("name", hall.getName());
        if (hallService.count(wrapper) > 0) throw new HallExistException();

        return hallService.save(hall) ?
                new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }
}

