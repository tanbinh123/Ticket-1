package com.woniuxy.cinema.mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.cinema.entity.Schedule;
import com.woniuxy.cinema.entity.dto.ScheduleSeatStatusDto;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface ScheduleMapper extends BaseMapper<Schedule> {
    List<ScheduleSeatStatusDto> selectSeatStatusById(Integer id);

}
