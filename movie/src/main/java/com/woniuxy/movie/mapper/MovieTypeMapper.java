package com.woniuxy.movie.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.movie.entity.MovieType;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface MovieTypeMapper extends BaseMapper<MovieType> {
    /**
     * 批量添加电影-类型
     * @param movieTypes 电影-类型对象
     * @return 是否添加成功
     */
    Boolean insertAll(List<MovieType> movieTypes);
}
