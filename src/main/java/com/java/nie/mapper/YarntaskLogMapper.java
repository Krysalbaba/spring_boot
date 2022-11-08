package com.java.nie.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.java.nie.domain.YarntaskLog;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * <p>
 * yarn任务日志 Mapper 接口
 * </p>
 *
 * @author kafka
 * @since 2022-06-06
 */
public interface YarntaskLogMapper extends BaseMapper<YarntaskLog> {

    @Select("show tables LIKE #{tableName}")
    List<String> checkTable(@Param("tableName")String tableName);


    void createTable (@Param("tableName")String tableName);

}
