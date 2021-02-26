package com.woniuxy.movie.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.movie.entity.Movie;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface MovieService extends IService<Movie> {
    /**
     * 添加电影
     *
     * @param movie   电影对象
     * @param typeIDs 类型ID(s)
     * @return 电影ID
     */
    Integer addMovie(Movie movie, List<Integer> typeIDs);

    /**
     * 通过类型分页查找电影
     *
     * @param page   分页对象
     * @param typeId 类型ID
     * @return 电影分页结果
     */
    IPage<Movie> getByType(Page<?> page, Integer typeId);
}
