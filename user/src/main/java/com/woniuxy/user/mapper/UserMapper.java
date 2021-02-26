package com.woniuxy.user.mapper;
import org.apache.ibatis.annotations.Param;

import com.woniuxy.user.entity.User;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface UserMapper extends BaseMapper<User> {

    Boolean updateIntegrationById(@Param("integration") Integer integration, @Param("id") Integer id);
}
