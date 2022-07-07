package com.java.nie.service;

import com.java.nie.common.entity.vo.CompletedExportVo;
import com.java.nie.domain.TCompleted;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 竣工信息表 服务类
 * </p>
 *
 * @author nie
 * @since 2022-07-07
 */
public interface ITCompletedService extends IService<TCompleted> {


    List<CompletedExportVo> export(List<String> ids);

}
