package com.woniuxy.movie.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

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
@TableName("ticket_movie_type")
@Accessors(chain = true)
public class MovieType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer movieId;

    private Integer typeId;


}
