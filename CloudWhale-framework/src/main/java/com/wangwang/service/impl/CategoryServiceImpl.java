package com.wangwang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangwang.constants.SystemConstants;
import com.wangwang.domain.ResponseResult;
import com.wangwang.domain.entity.Article;
import com.wangwang.domain.entity.Category;
import com.wangwang.domain.vo.CategoryVo;
import com.wangwang.mapper.CategoryMapper;
import com.wangwang.service.ArticleService;
import com.wangwang.service.CategoryService;
import com.wangwang.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private ArticleService articleService;

    @Override
    public ResponseResult getCatagoryList() {
        //查询文章表,状态为已发布的文章
        LambdaQueryWrapper<Article> articleWrapper = new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //Todo:直接使用list方法查到的是category表,使用articleService查询的是文章表
        List<Article> articleList = articleService.list(articleWrapper);
        //获取文章的分类ID,并且去重
        Set<Long> categoryIds = articleList.stream()
                .map(article -> article.getCategoryId())
                .collect(Collectors.toSet());
        //查询分类表
        List<Category> categories = listByIds(categoryIds);
        categories.stream()
                .filter(category -> SystemConstants.STRING_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装VO
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(categories, CategoryVo.class);
        return ResponseResult.okResult(categoryVos);
    }
}

