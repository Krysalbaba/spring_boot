package com.java.nie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.nie.bean.CommonResult;
import com.java.nie.bean.ResultGenerator;
import com.java.nie.bean.field.annotation.DemoAnnotation;
import com.java.nie.bean.field.annotation.DemoOneAnnotation;
import com.java.nie.domain.TUser;
import com.java.nie.service.ITUserService;
import com.java.nie.config.RedisService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.regex.Pattern;

/**
 * @author nie
 * @date 2023-02-17
 * @apiNote mybatis-plus 简单测试
 */
@RestController
@RequestMapping("/tUser")
public class TUserController {

    //redis token 头部
    private String token = "nie";

    @Autowired
    private ITUserService itUserService;

    @Autowired
    private RedisService redisService;


    @GetMapping("/checkLoginName/{loginName}")
    public CommonResult checkLoginName(@PathVariable("loginName") String loginName) {

        LambdaQueryWrapper<TUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(TUser::getLoginName, loginName);
        //0:未删除 1:已删除
        wrapper.eq(TUser::getIsDel, 0);
        List<TUser> list = itUserService.list(wrapper);
        if (!CollectionUtils.isEmpty(list)) {
            throw new RuntimeException("用户名重复");
        }
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/register")
    public CommonResult register(@Validated @RequestBody TUser user) {
        //将数据设置为 未删除
        itUserService.save(user);
        String idToken = token + user.getId();
        redisService.set(idToken, user, 3600);
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/aspect")
    @DemoAnnotation
    public void aspect(@RequestBody TUser user) {
        System.out.println("执行方法区----------------------------");
        if (ObjectUtils.isEmpty(user.getIsDel())){
            throw new RuntimeException();
        }
    }

    @PostMapping("/login")
    public CommonResult login(@RequestBody TUser user) {
        String loginName = user.getLoginName();
        String password = user.getPassword();
        if (StringUtils.hasLength(loginName) && StringUtils.hasLength(password)) {
            LambdaQueryWrapper<TUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TUser::getLoginName, loginName);
            wrapper.eq(TUser::getPassword, password);
            TUser tUser = itUserService.getOne(wrapper, false);
            if (!ObjectUtils.isEmpty(tUser)) {
                return ResultGenerator.genSuccessResult();
            }
        }
        return ResultGenerator.genFailResult("登录失败！");
    }


    @PostMapping("/deleteById/{id}")
    public CommonResult delete(@PathVariable(value = "id")String id){
        itUserService.removeById(id);
        return ResultGenerator.genSuccessResult();
    }

    @PostMapping("/selectList")
    public CommonResult selectList(@RequestBody TUser user){
        List<TUser> list = itUserService.list();
        return ResultGenerator.genSuccessResult(list);
    }


}

