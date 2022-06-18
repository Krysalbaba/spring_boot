package com.java.nie.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.nie.bean.CommonResult;
import com.java.nie.bean.ResultGenerator;
import com.java.nie.domain.SysUser;
import com.java.nie.domain.TUser;
import com.java.nie.mapper.SysUserMapper;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 系统用户表 前端控制器
 * </p>
 *
 * @author nie
 * @since 2022-06-16
 */
@RestController
@RequestMapping("/sysUser")
public class SysUserController {

    @Resource
    SysUserMapper sysUserMapper ;

    @PostMapping("/getUserById")
    public CommonResult register(String id) {
        SysUser demo = sysUserMapper.demo("1");
        return ResultGenerator.genSuccessResult();
    }


    @PostMapping("/page")
    public CommonResult page() {
        Page<SysUser> page = new Page<>(2, 10);
        IPage<SysUser>  result= sysUserMapper.getPage(page);
        return ResultGenerator.genSuccessResult();
    }

}

