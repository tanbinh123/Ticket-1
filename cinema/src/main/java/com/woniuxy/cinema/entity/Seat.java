package com.woniuxy.cinema.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
public class Seat implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    private Integer hallId;

    private Integer rowNo;

    private Integer colNo;

    private String status;


}
