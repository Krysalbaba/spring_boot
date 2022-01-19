package com.java.nie.config;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    @Override
    //新增时默认填充新增和修改时间
    public void insertFill(MetaObject metaObject) {
        this.setFieldValByName("gmtCreate", LocalDateTime.now(), metaObject);
        this.setFieldValByName("gmtUpdate", LocalDateTime.now(), metaObject);
    }

    @Override
    //修改操作时默认填充修改时间
    public void updateFill(MetaObject metaObject) {
        this.setFieldValByName("gmtUpdate", LocalDateTime.now(), metaObject);
    }
}
