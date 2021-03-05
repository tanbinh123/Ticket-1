package com.woniuxy.auth.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.woniuxy.auth.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * Mapper 接口
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface UserMapper extends BaseMapper<User> {

    /**
     * 修改用户积分
     *
     * @param chgVal 积分变化值
     * @param id     用户ID
     * @return 修改结果
     */
    Boolean updateIntegrationById(@Param("chgVal") Integer chgVal, @Param("id") Integer id);


}
