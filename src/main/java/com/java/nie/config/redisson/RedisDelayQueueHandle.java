package com.java.nie.config.redisson;


public interface RedisDelayQueueHandle<T> {

    void execute(T t);
}
