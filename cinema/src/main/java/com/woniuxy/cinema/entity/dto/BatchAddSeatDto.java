package com.woniuxy.cinema.entity.dto;

import com.woniuxy.cinema.entity.Seat;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class BatchAddSeatDto {
    private Seat seat;
    private Integer rowSize;
    private Integer colSize;
}
