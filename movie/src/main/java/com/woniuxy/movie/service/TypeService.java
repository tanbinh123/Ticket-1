package com.woniuxy.movie.service;

import com.woniuxy.movie.entity.Type;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface TypeService extends IService<Type> {
    Integer add(Type type);
}
