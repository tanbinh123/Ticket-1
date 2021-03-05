package com.woniuxy.user.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.woniuxy.user.entity.Perm;
import com.woniuxy.user.entity.User;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
public interface UserService extends IService<User> {
    /**
     * 用户注册
     *
     * @param user 用户对象
     * @return 注册结果
     */
    Boolean register(User user);

    /**
     * 修改用户积分
     *
     * @param chgVal 积分变化值
     * @param id     用户ID
     * @return 修改结果
     */
    Boolean updateIntegrationById(Integer chgVal, Integer id);

    /**
     * 获取用户菜单
     *
     * @param id 用户ID
     * @return 菜单列表
     */
    List<Perm> getMenu(Integer id);
}
