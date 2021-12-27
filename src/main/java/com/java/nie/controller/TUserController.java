package com.java.nie.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.java.nie.bean.CommonResult;
import com.java.nie.bean.ResultGenerator;
import com.java.nie.domain.TUser;
import com.java.nie.service.ITUserService;
import com.java.nie.config.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author nie
 * @since 2021-12-26
 */
@RestController
@RequestMapping("/tUser")
public class TUserController {

    @Autowired
    private ITUserService itUserService;

    @Autowired
    private RedisService redisService ;

    @PostMapping("/login")
    public CommonResult login(@RequestBody TUser user) {
        String loginName = user.getLoginName();
        String password = user.getPassword();
        if (StringUtils.hasLength(loginName) && StringUtils.hasLength(password)) {
            LambdaQueryWrapper<TUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(TUser::getLoginName, loginName);
            wrapper.eq(TUser::getPassword, password);
            TUser tUser = itUserService.getOne(wrapper, false);
            if (ObjectUtils.isEmpty(tUser)) {
                return ResultGenerator.genSuccessResult();
            }
        }
        return ResultGenerator.genFailResult("登录失败！");
    }

    @PostMapping("/register")
    @Cacheable(value = "uid",key = "#user")
    public CommonResult register(@RequestBody TUser user) {
        String uid="AAA12345678";
        String loginName = user.getLoginName();
        String password = user.getPassword();
        if (StringUtils.hasLength(loginName) && StringUtils.hasLength(password)) {
            itUserService.save(user);
            return ResultGenerator.genSuccessResult();
        }
        return ResultGenerator.genFailResult("注册失败");
    }

    @GetMapping("/redisDemo")
    public void  redisDemo(@RequestParam("id") String id){
        TUser user = itUserService.getById(id);
        redisService.set(id,user,1800L);
    }

    @GetMapping("/demo")
    @Cacheable(value = "user",key = "#uid")
    public TUser demo(@RequestParam("uid") String uid){
        TUser user=new TUser();
        user.setId("123456");
        user.setLoginName("zhangsan");
        user.setPassword("123456");
        return user;
    }
}

