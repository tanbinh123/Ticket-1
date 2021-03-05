package com.woniuxy.auth.entity;

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
 * @since 2021-03-03
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode()
public class RolePerm implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer roleId;

    private Integer permId;


}
