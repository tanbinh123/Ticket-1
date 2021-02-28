package com.woniuxy.cinema.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.cinema.entity.Schedule;
import com.woniuxy.cinema.entity.dto.ScheduleSeatStatusDto;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface ScheduleMapper extends BaseMapper<Schedule> {
    /**
     * 通过ID获取排片座位状态
     *
     * @param id 排片ID
     * @return 排片座位状态
     */
    List<ScheduleSeatStatusDto> selectSeatStatusById(Integer id);

    /**
     * 检查排片时间是否可用
     *
     * @param id  排片ID
     * @param now 当前时间
     * @return 检查结果
     */
    Boolean checkTime(@Param("id") Integer id, @Param("now") LocalDateTime now);
}
