package com.woniuxy.cinema.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.woniuxy.cinema.entity.Schedule;
import com.woniuxy.cinema.entity.dto.ScheduleSeatStatusDto;
import com.woniuxy.cinema.mapper.ScheduleMapper;
import com.woniuxy.cinema.mapper.ScheduleSeatMapper;
import com.woniuxy.cinema.service.ScheduleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
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

    @Override
    public List<ScheduleSeatStatusDto> getSeatStatusById(Integer id) {

        return scheduleMapper.selectSeatStatusById(id);
    }

    @Override
    public Boolean updateSeatStatus(String status, Integer scheduleId, Integer seatId) {
        return scheduleSeatMapper.updateStatusByScheduleIdAndSeatId(status, scheduleId, seatId);
    }
}
