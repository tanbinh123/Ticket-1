package com.woniuxy.cinema.service.impl;

import com.woniuxy.cinema.entity.Cinema;
import com.woniuxy.cinema.exception.CinemaInfoExistException;
import com.woniuxy.cinema.mapper.CinemaMapper;
import com.woniuxy.cinema.service.CinemaService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Service
public class CinemaServiceImpl extends ServiceImpl<CinemaMapper, Cinema> implements CinemaService {
    @Resource
    CinemaMapper cinemaMapper;

    /**
     * 添加影院
     *
     * @param cinema 影院对象
     * @return 添加结果
     */
    @Override
    public Boolean add(Cinema cinema) {
        if (cinemaMapper.selectByNameOrTelOrAddress(cinema).size() > 0) throw new CinemaInfoExistException();

        return cinemaMapper.insert(cinema) == 1;
    }
}
