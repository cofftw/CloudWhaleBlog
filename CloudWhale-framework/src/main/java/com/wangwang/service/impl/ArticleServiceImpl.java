package com.wangwang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangwang.constants.SystemConstants;
import com.wangwang.domain.ResponseResult;
import com.wangwang.domain.entity.Article;
import com.wangwang.domain.entity.Category;
import com.wangwang.domain.vo.ArticleDetailVo;
import com.wangwang.domain.vo.ArticleListVo;
import com.wangwang.domain.vo.HotArticleVo;
import com.wangwang.domain.vo.PageVo;
import com.wangwang.mapper.ArticleMapper;
import com.wangwang.service.ArticleService;
import com.wangwang.service.CategoryService;
import com.wangwang.utils.BeanCopyUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {

    @Autowired
    private CategoryService categoryService;

    @Override
    public ResponseResult hotArticleList() {
        //查询热门文章 封装成ResponseResult返回
        //Todo:lambdaQueryWrapper是一个查询条件构造器
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //必须是正式文章
        //Todo:使用方法进行匹配，不直接使用名称进行匹配，这样检测不到可以报错
        //Todo:Article::getStatus为属性表达式
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //按照浏览量进行排序
        lambdaQueryWrapper.orderByDesc(Article::getViewCount);
        //最多只查询十条
        Page<Article> page = new Page(1, 10);
        //Todo:在MyBatis-Plus中,page()方法是用于实现分页查询的,page参数表示分页查询的具体信息
        page(page, lambdaQueryWrapper);

        List<Article> articles = page.getRecords();
        List<HotArticleVo> articleVos = new ArrayList<>();
        //Bean拷贝
//        for (Article article : articles) {
//            HotArticleVo vo = new HotArticleVo();
//            BeanUtils.copyProperties(article, vo);
//            articleVos.add(vo);
//        }
        //Todo:将Article类型的数据列表articles转换成HotArticleVo类型的数据列表vs
        List<HotArticleVo> vs = BeanCopyUtils.copyBeanList(articles, HotArticleVo.class);
        return ResponseResult.okResult(vs);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        //如果有categoryID,就要传入相同的
        lambdaQueryWrapper.eq(Objects.nonNull(categoryId) && categoryId > 0, Article::getCategoryId, categoryId);
        //状态为正式发布
        lambdaQueryWrapper.eq(Article::getStatus, SystemConstants.ARTICLE_STATUS_NORMAL);
        //对isTop进行降序
        lambdaQueryWrapper.orderByDesc(Article::getIsTop);

        //分页查询
        Page<Article> page = new Page<>(pageNum, pageSize);
        page(page, lambdaQueryWrapper);

        //查询categoryName
        List<Article> articles = page.getRecords();
        //articleID查询articleName进行设置
//        for (Article article : articles) {
//            Category category = categoryService.getById(article.getCategoryId());
//            article.setCategoryName(category.getName());
//        }
        //获取分类id,查询分类信息,获取分类名称
        articles.stream()
                .map(article -> article.setCategoryName(categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());

        //封装查询结果
        List<ArticleListVo> articleListVos = BeanCopyUtils.copyBeanList(page.getRecords(), ArticleListVo.class);

        PageVo pageVo = new PageVo(articleListVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult getArticleDetail(Long id) {
        //根据id查询文章
        Article article = getById(id);
        //转换成VO
        ArticleDetailVo articleDetailVo = BeanCopyUtils.copyBean(article, ArticleDetailVo.class);
        //根据分类id查询分类名
        Long categoryId = articleDetailVo.getCategoryId();
        Category category = categoryService.getById(categoryId);
        if(category != null){
            articleDetailVo.setCategoryName(category.getName());
        }
        return ResponseResult.okResult(articleDetailVo);
    }
}
