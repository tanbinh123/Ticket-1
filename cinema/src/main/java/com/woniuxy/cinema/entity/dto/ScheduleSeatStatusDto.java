package com.woniuxy.cinema.entity.dto;

import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class ScheduleSeatStatusDto {
    private Integer colNo;
    private Integer rowNo;
    private String status;
}
