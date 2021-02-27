package com.woniuxy.cinema.controller;


import com.woniuxy.cinema.entity.Cinema;
import com.woniuxy.cinema.service.CinemaService;
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
@Api(tags = "影院接口")
@RestController
@RequestMapping("/cinema")
public class CinemaController {
    @Resource
    CinemaService cinemaService;

    /**
     * 添加影院
     *
     * @param cinema 影院对象
     * @return 添加结果
     */
    @ApiOperation("添加影院")
    @PostMapping("/add")
    private ResponseResult<?> add(@RequestBody Cinema cinema) {
        return cinemaService.add(cinema) ?
                new ResponseResult<>(StateEnum.SUCCESS) : new ResponseResult<>(StateEnum.FAIL);
    }
}

