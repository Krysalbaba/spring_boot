package com.java.nie.service.impl;

import com.java.nie.common.entity.vo.CompletedExportVo;
import com.java.nie.domain.TCompleted;
import com.java.nie.mapper.TCompletedMapper;
import com.java.nie.service.ITCompletedService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 * 竣工信息表 服务实现类
 * </p>
 *
 * @author nie
 * @since 2022-07-07
 */
@Service
public class TCompletedServiceImpl extends ServiceImpl<TCompletedMapper, TCompleted> implements ITCompletedService {

    @Resource
    private TCompletedMapper completedMapper ;

    @Override
    public List<CompletedExportVo> export(List<String> ids) {
        return completedMapper.export(ids);
    }

}
