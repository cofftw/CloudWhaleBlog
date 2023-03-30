package com.wangwang.mapper;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wangwang.domain.entity.User;
import org.springframework.stereotype.Repository;

/**
 * 用户表(User)表数据库访问层
 *
 * @author makejava
 * @since 2023-03-25 21:40:14
 */

@Repository
public interface UserMapper extends BaseMapper<User> {
}

