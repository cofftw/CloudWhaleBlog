package com.wangwang.controller;

import com.wangwang.domain.ResponseResult;
import com.wangwang.domain.entity.User;
import com.wangwang.enums.AppHttpCodeEnum;
import com.wangwang.handler.exception.SystemException;
import com.wangwang.service.BlogLoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BlogLoginController {

    @Autowired
    private BlogLoginService blogLoginService;

    @PostMapping("/login")
    public ResponseResult login(@RequestBody User user){
        if(!StringUtils.hasText(user.getUserName())){
            //提升必须要传入用户名
            throw new SystemException(AppHttpCodeEnum.REQUIRE_USERNAME);
        }
        return blogLoginService.login(user);
    }

    @GetMapping("/logout")
    public ResponseResult logout(){
        return blogLoginService.logout();
    }
}
