package com.woniuxy.cinema.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.cinema.entity.Schedule;
import com.woniuxy.cinema.entity.ScheduleSeat;
import com.woniuxy.cinema.entity.dto.ScheduleSeatStatusDto;
import com.woniuxy.cinema.exception.SchedulingOverException;
import com.woniuxy.cinema.exception.SeatAvailableException;
import com.woniuxy.cinema.exception.SeatUnavailableException;
import com.woniuxy.cinema.exception.StatusErrorException;
import com.woniuxy.cinema.mapper.ScheduleMapper;
import com.woniuxy.cinema.mapper.ScheduleSeatMapper;
import com.woniuxy.cinema.service.ScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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
public class ScheduleServiceImpl extends ServiceImpl<ScheduleMapper, Schedule> implements ScheduleService {
    @Resource
    ScheduleMapper scheduleMapper;
    @Resource
    ScheduleSeatMapper scheduleSeatMapper;

    /**
     * 通过ID获取排片座位状态
     *
     * @param id 排片ID
     * @return 排片座位状态
     */
    @Override
    public List<ScheduleSeatStatusDto> getSeatStatusById(Integer id) {
        return scheduleMapper.selectSeatStatusById(id);
    }

    /**
     * 修改排片座位状态
     *
     * @param status     目标状态
     * @param scheduleId 排片ID
     * @param seatId     座位ID
     * @return 修改结果
     */
    @Override
    public Boolean updateScheduleSeatStatus(String status, Integer scheduleId, Integer seatId) {
        // 检查排片是否已结束
        if (!scheduleMapper.checkTime(scheduleId, LocalDateTime.now()))
            throw new SchedulingOverException();
        // 检查座位状态
        QueryWrapper<ScheduleSeat> wrapper = new QueryWrapper<>();
        wrapper.eq("schedule_id", scheduleId);
        wrapper.eq("seat_id", seatId);
        switch (status) {
            // 修改座位状态为 不可用
            case "0":
                wrapper.eq("status", "1");
                if (scheduleSeatMapper.update(new ScheduleSeat().setStatus("0"), wrapper) == 1) return true;
                throw new SeatUnavailableException();
                // 修改座位状态为 可用
            case "1":
                wrapper.eq("status", "0");
                if (scheduleSeatMapper.update(new ScheduleSeat().setStatus("1"), wrapper) == 1) return true;
                throw new SeatAvailableException();
            default:
                throw new StatusErrorException();
        }
    }
}
