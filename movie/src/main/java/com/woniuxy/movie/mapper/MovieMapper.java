package com.woniuxy.movie.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.woniuxy.movie.entity.Movie;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface MovieMapper extends BaseMapper<Movie> {

    /**
     * 通过类型分页查找电影
     *
     * @param page   分页对象
     * @param typeId 类型ID
     * @return 电影分页结果
     */
    IPage<Movie> selectByType(Page<?> page, Integer typeId);

}
