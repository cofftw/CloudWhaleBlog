package com.wangwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangwang.domain.ResponseResult;
import com.wangwang.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-03-21 15:40:59
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCatagoryList();
}

