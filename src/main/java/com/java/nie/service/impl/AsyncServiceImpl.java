package com.java.nie.service.impl;

import com.java.nie.service.AsyncService;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;


@Service
public class AsyncServiceImpl implements AsyncService {


    @Override
    @Async
    public Future<String> asyncOne() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println("asyncOne======================"+i);
            Thread.sleep(1000);
        }

        return new AsyncResult<>("asyncOne=============success");
    }

    @Override
    @Async
    public Future<String> asyncTwo() throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            System.out.println("asyncTwo======================"+i);
            Thread.sleep(1000);
        }

        return new AsyncResult<>("asyncTwo=============success");
    }
}
