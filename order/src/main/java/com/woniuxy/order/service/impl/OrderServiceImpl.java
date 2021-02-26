package com.woniuxy.order.service.impl;

import com.woniuxy.order.entity.Order;
import com.woniuxy.order.mapper.OrderMapper;
import com.woniuxy.order.service.OrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Service
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements OrderService {

}
