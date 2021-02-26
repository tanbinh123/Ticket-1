package com.woniuxy.movie.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.common.utils.ResponseResult;
import com.woniuxy.movie.entity.Movie;
import com.woniuxy.common.enums.StateEnum;
import com.woniuxy.movie.exception.MovieNotExistException;
import com.woniuxy.movie.service.MovieService;
import com.woniuxy.movie.validation.MovieUpdate;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@RestController
@Api(tags = "电影接口")
@Validated
@Slf4j
@RequestMapping("/movie")
public class MovieController {
    @Resource
    MovieService movieService;

    /**
     * 添加电影
     *
     * @param movie   电影对象
     * @param typeIDs 类型ID
     * @return 电影ID
     */
    @ApiOperation("添加电影")
    @PostMapping("/add")
    public ResponseResult<?> addMovie(@RequestBody @Valid Movie movie,
                                      @RequestParam("typeIDs") List<Integer> typeIDs) {
        return new ResponseResult<>(movieService.addMovie(movie, typeIDs));
    }

    /**
     * 通过类型查找电影
     *
     * @param currentPage 当前页
     * @param pageSize    分页大小
     * @param typeId      类型ID
     * @return 电影分页结果
     */
    @ApiOperation("通过类型查找电影")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "currentPage", value = "当前页", defaultValue = "1", required = true,
                    paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "分页大小", defaultValue = "2", required = true,
                    paramType = "path", dataType = "int"),
            @ApiImplicitParam(name = "typeId", value = "类型ID", required = true,
                    paramType = "path", dataType = "int")
    })
    @GetMapping("/by-type/{currentPage}/{pageSize}/{typeId}")
    public ResponseResult<IPage<Movie>> getByType(
            @PathVariable @Min(value = 1, message = "当前页应大于{value}") Integer currentPage,
            @PathVariable @Min(value = 2, message = "分页大小应大于{value}") Integer pageSize,
            @PathVariable @Min(value = 1, message = "类型ID应大于{value}") Integer typeId) {
        Page<Movie> page = new Page<>(currentPage, pageSize);

        return new ResponseResult<>(movieService.getByType(page, typeId));
    }

    /**
     * 通过ID查找电影
     *
     * @param id 电影ID
     * @return 电影对象
     */
    @ApiOperation("通过ID查找电影")
    @ApiImplicitParams(@ApiImplicitParam(name = "id", value = "电影ID", required = true,
            paramType = "path", dataType = "int"))
    @GetMapping("/by-id/{id}")
    public ResponseResult<Movie> getById(@PathVariable @Min(value = 1, message = "电影id应大于{value}") Integer id) {
        return new ResponseResult<>(movieService.getById(id));
    }

    /**
     * 通过ID修改电影信息
     *
     * @param movie 电影对象
     * @return 修改结果
     */
    @ApiOperation("通过ID修改电影信息")
    @PostMapping("/update")
    public ResponseResult<?> updateById(@RequestBody @Validated(MovieUpdate.class) Movie movie) {
        // if不成功
        if (!movieService.updateById(movie)) throw new MovieNotExistException();
        return new ResponseResult<>(StateEnum.SUCCESS);
    }

}

