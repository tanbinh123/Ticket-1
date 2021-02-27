package com.woniuxy.cinema.mapper;
import org.apache.ibatis.annotations.Param;

import com.woniuxy.cinema.entity.ScheduleSeat;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface ScheduleSeatMapper extends BaseMapper<ScheduleSeat> {
    Boolean updateStatusByScheduleIdAndSeatId(@Param("status") String status,
                                          @Param("scheduleId") Integer scheduleId,
                                          @Param("seatId") Integer seatId);
}
