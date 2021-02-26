package com.woniuxy.movie.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.movie.entity.Type;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.movie.exception.TypeExistException;
import com.woniuxy.movie.exception.TypeNotExistException;
import com.woniuxy.movie.service.TypeService;
import com.woniuxy.common.utils.ResponseResult;
import com.woniuxy.movie.validation.TypeUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-22
 */
@RestController
@Api(tags = "电影类型接口")
@RequestMapping("/type")
public class TypeController {
    @Resource
    private TypeService typeService;

    /**
     * 添加类型
     *
     * @return 类型ID
     */
    @ApiOperation("添加类型的接口")
    @ApiImplicitParams(
            @ApiImplicitParam(name = "typeName", value = "类型名称", required = true, paramType = "query")
    )
    @PostMapping("/add")
    private ResponseResult<Integer> add(String typeName) {
        return new ResponseResult<>(typeService.add(new Type().setName(typeName)));
    }

    /**
     * 获取所有类型
     *
     * @return 类型
     */
    @ApiOperation("获取所有类型的接口")
    @GetMapping("/get-all")
    private ResponseResult<List<Type>> getAll() {
        return new ResponseResult<>(typeService.list());
    }

    /**
     * 通过ID更改类型
     *
     * @param type 类型对象
     * @return 更改结果
     */
    @ApiOperation("更改类型")
    @PostMapping("/update")
    private ResponseResult<?> update(@RequestBody @Validated(TypeUpdate.class) Type type) {
        // 是否已有同名类型
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("name", type.getName());
        if (typeService.count(wrapper) > 0) throw new TypeExistException();
        // if不成功
        if (!typeService.updateById(type)) throw new TypeNotExistException();
        return new ResponseResult<>(StateEnum.SUCCESS);
    }
}

