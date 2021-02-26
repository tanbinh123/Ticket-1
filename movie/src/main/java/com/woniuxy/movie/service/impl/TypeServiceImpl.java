package com.woniuxy.movie.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.woniuxy.movie.entity.Type;
import com.woniuxy.movie.exception.TypeExistException;
import com.woniuxy.movie.mapper.TypeMapper;
import com.woniuxy.movie.service.TypeService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author ll_5216
 * @since 2021-02-23
 */
@Service
public class TypeServiceImpl extends ServiceImpl<TypeMapper, Type> implements TypeService {
    @Resource
    private TypeMapper typeMapper;

    /**
     * 添加类型
     *
     * @param type 类型对象
     * @return 新增类型的ID
     */
    @Override
    @Transactional
    public Integer add(Type type) {
        // 检查类型是否已存在
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("name", type.getName());
        if (typeMapper.selectOne(wrapper) == null) typeMapper.insert(type);
        else throw new TypeExistException();

        return type.getId();
    }
}
