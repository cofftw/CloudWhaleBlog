package com.wangwang.filter;

import com.alibaba.fastjson.JSON;
import com.wangwang.domain.ResponseResult;
import com.wangwang.domain.entity.LoginUser;
import com.wangwang.enums.AppHttpCodeEnum;
import com.wangwang.utils.JwtUtil;
import com.wangwang.utils.RedisCache;
import com.wangwang.utils.WebUtils;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Objects;

@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private RedisCache redisCache;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        //获取请求头的token
        String token = request.getHeader("token");
        if (!StringUtils.hasText(token)){
            //说明该接口不需要登陆,直接放行
            //Todo:后面都是解析token,没有token交给后面的过滤器解析
            filterChain.doFilter(request, response);
            return;
        }
        //解析请求的userid
        Claims claims = null;
        try {
            claims = JwtUtil.parseJWT(token);
        } catch (Exception e){
            e.printStackTrace();
            //token超时,token非法,相应告诉前端需要重新登陆
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        String userId = claims.getSubject();
        //从redis中获取信息
        LoginUser loginUser = redisCache.getCacheObject("bloglogin:" + userId);//如果获取不到
        if(Objects.isNull(loginUser)){
            //说明登陆过期
            ResponseResult result = ResponseResult.errorResult(AppHttpCodeEnum.NEED_LOGIN);
            WebUtils.renderString(response, JSON.toJSONString(result));
            return;
        }
        //存入SecurityContextHolder
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUser, null, null);
        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        //放行,进入下一个过滤器
        filterChain.doFilter(request, response);
    }
}
