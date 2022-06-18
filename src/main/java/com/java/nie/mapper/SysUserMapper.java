package com.java.nie.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.java.nie.domain.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * 系统用户表 Mapper 接口
 * </p>
 *
 * @author nie
 * @since 2022-06-16
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser demo (@Param("id")String id) ;

    IPage<SysUser> getPage(Page<SysUser> data);

}
