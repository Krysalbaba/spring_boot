package com.java.nie.controller;

import com.java.nie.service.AsyncService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.concurrent.Future;

/**
 * @author nie
 * @date 2023-02-17
 * @apiNote  异步简单使用
 */
@RequestMapping("/async")
@RestController
public class AsyncController {

    @Resource
    private AsyncService asyncService ;


    @GetMapping("/test")
    public String asyncTest() throws InterruptedException {
        Future<String> one = asyncService.asyncOne();
        Future<String> two = asyncService.asyncTwo();
        String result = "asyncTest=============success";
        System.out.println(result);
        return  result ;
    }



}
