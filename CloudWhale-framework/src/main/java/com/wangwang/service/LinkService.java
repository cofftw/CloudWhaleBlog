package com.wangwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangwang.domain.ResponseResult;
import com.wangwang.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-03-25 17:58:04
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}

