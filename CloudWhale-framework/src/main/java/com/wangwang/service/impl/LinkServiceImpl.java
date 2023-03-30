package com.wangwang.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wangwang.constants.SystemConstants;
import com.wangwang.domain.ResponseResult;
import com.wangwang.domain.entity.Link;
import com.wangwang.domain.vo.LinkVo;
import com.wangwang.mapper.LinkMapper;
import com.wangwang.service.LinkService;
import com.wangwang.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-03-25 17:58:04
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        //查询所有审核通过的
        LambdaQueryWrapper<Link> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Link::getStatus, SystemConstants.ARTICLE_STATUS_DRAFT);
        List<Link> links = list(lambdaQueryWrapper);
        //转换成VO
        List<LinkVo> linkVos = BeanCopyUtils.copyBeanList(links, LinkVo.class);
        return ResponseResult.okResult(linkVos);
    }
}

