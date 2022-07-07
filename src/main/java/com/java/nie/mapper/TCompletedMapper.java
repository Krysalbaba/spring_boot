package com.java.nie.mapper;

import com.java.nie.common.entity.vo.CompletedExportVo;
import com.java.nie.domain.TCompleted;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 竣工信息表 Mapper 接口
 * </p>
 *
 * @author nie
 * @since 2022-07-07
 */
public interface TCompletedMapper extends BaseMapper<TCompleted> {

    List<CompletedExportVo> export(@Param("ids") List<String> ids);

}
