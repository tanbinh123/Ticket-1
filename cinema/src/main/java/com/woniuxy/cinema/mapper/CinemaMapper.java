package com.woniuxy.cinema.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.cinema.entity.Cinema;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface CinemaMapper extends BaseMapper<Cinema> {
    /**
     * 通过 名称/电话/地址 获取影院
     *
     * @param cinema 影院对象
     * @return 影院对象
     */
    List<Cinema> selectByNameOrTelOrAddress(Cinema cinema);

}
