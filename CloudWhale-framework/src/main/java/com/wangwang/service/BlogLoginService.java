package com.wangwang.service;

import com.wangwang.domain.ResponseResult;
import com.wangwang.domain.entity.User;

public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}
