package com.woniuxy.movie.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.woniuxy.movie.validation.MovieUpdate;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.math.BigDecimal;

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
@TableName("ticket_movie")
public class Movie implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "id", type = IdType.AUTO)
    @Min(value = 1, message = "电影ID应大于{value}", groups = MovieUpdate.class)
    private Integer id;

    @Size(min = 2, max = 30, message = "电影名应在{min}-{max}个字符之间")
    private String name;

    @Size(min = 2, max = 20, message = "导演名应在{min}-{max}个字符之间")
    private String director;

    @Size(min = 2, max = 20, message = "演员名应在{min}-{max}个字符之间")
    private String actor;

    @NotBlank(message = "描述不能为空")
    private String description;

    private String stills;

    private String trailer;

    private BigDecimal score;

}
