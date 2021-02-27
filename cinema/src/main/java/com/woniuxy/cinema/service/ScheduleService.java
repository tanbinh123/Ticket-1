package com.woniuxy.cinema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.cinema.entity.Schedule;
import com.woniuxy.cinema.entity.dto.ScheduleSeatStatusDto;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface ScheduleService extends IService<Schedule> {
    List<ScheduleSeatStatusDto> getSeatStatusById(Integer statusId);

    Boolean updateSeatStatus(String status, Integer scheduleId, Integer seatId);
}
