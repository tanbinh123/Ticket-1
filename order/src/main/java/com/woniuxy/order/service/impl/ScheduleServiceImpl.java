package com.woniuxy.order.service.impl;

import com.woniuxy.order.exception.StatusErrorException;
import com.woniuxy.order.service.ScheduleService;
import org.springframework.stereotype.Service;

@Service
public class ScheduleServiceImpl implements ScheduleService {
    @Override
    public String updateSeatStatus(String status, Integer scheduleId, Integer seatId) {
//        throw new StatusErrorException();
        return "fail";
    }

    @Override
    public String getById(Integer id) {
        throw new StatusErrorException();

    }
}
