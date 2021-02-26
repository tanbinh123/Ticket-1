package com.woniuxy.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.movie.entity.Movie;
import com.woniuxy.movie.entity.MovieType;
import com.woniuxy.movie.entity.Type;
import com.woniuxy.movie.exception.TypeNotExistException;
import com.woniuxy.movie.mapper.MovieMapper;
import com.woniuxy.movie.mapper.MovieTypeMapper;
import com.woniuxy.movie.mapper.TypeMapper;
import com.woniuxy.movie.service.MovieService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Service
@Slf4j
public class MovieServiceImpl extends ServiceImpl<MovieMapper, Movie> implements MovieService {
    @Resource
    MovieMapper movieMapper;
    @Resource
    MovieTypeMapper movieTypeMapper;
    @Resource
    TypeMapper typeMapper;

    /**
     * 添加电影
     *
     * @param movie   电影对象
     * @param typeIDs 类型ID(s)
     * @return 电影ID
     */
    @Override
    @Transactional
    public Integer addMovie(Movie movie, List<Integer> typeIDs) {
        // 检查类型是否存在
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.select("id");
        List<Object> ownTypeIDs = typeMapper.selectObjs(wrapper);
        for (Integer typeID : typeIDs) {
            if (!ownTypeIDs.contains(typeID)) throw new TypeNotExistException();
        }
        // 添加电影
        movieMapper.insert(movie);
        // 添加电影-类型
        List<MovieType> movieTypes = new ArrayList<>();
        for (Integer typeID : typeIDs) {
            movieTypes.add(new MovieType().setMovieId(movie.getId()).setTypeId(typeID));
        }
        movieTypeMapper.insertAll(movieTypes);

        return movie.getId();
    }

    /**
     * 通过类型分页查找电影
     *
     * @param page   分页对象
     * @param typeId 类型ID
     * @return 电影分页结果
     */
    @Override
    public IPage<Movie> getByType(Page<?> page, Integer typeId) {
        return movieMapper.selectByType(page, typeId);
    }
}
