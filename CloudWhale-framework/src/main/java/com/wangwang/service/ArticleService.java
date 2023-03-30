package com.wangwang.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.wangwang.domain.ResponseResult;
import com.wangwang.domain.entity.Article;
import org.springframework.stereotype.Component;

@Component
public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getArticleDetail(Long id);
}
