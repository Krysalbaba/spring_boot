package com.java.nie.service.impl;

import com.java.nie.domain.TUser;
import com.java.nie.mapper.TUserMapper;
import com.java.nie.service.ITUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author nie
 * @since 2021-12-26
 */
@Service
public class TUserServiceImpl extends ServiceImpl<TUserMapper, TUser> implements ITUserService {

}
