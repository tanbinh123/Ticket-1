package com.woniuxy.cinema.service;

import com.woniuxy.cinema.entity.Cinema;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface CinemaService extends IService<Cinema> {
    /**
     * 添加影院
     *
     * @param cinema 影院对象
     * @return 添加结果
     */
    Boolean add(Cinema cinema);
}
