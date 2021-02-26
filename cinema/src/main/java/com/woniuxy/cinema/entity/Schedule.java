package com.woniuxy.cinema.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer filmId;

    private Integer hallId;

    private LocalDateTime starttime;

    private LocalDateTime endtime;

    private BigDecimal price;

    private String status;


}
