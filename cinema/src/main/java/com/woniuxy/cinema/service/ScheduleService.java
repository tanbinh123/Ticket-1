package com.woniuxy.cinema.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.cinema.entity.Schedule;
import com.woniuxy.cinema.entity.dto.ScheduleSeatStatusDto;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface ScheduleService extends IService<Schedule> {
    /**
     * 通过ID获取排片座位状态
     *
     * @param id 排片ID
     * @return 排片座位状态
     */
    List<ScheduleSeatStatusDto> getSeatStatusById(Integer id);

    /**
     * 修改排片座位状态
     *
     * @param status     目标状态
     * @param scheduleId 排片ID
     * @param seatId     座位ID
     * @return 修改结果
     */
    Boolean updateScheduleSeatStatus(String status, Integer scheduleId, Integer seatId);
}
