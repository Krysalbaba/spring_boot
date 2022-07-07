package com.java.nie.service.impl;

import com.java.nie.domain.FileInfo;
import com.java.nie.mapper.FileInfoMapper;
import com.java.nie.service.IFileInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 文件上传 服务实现类
 * </p>
 *
 * @author nie
 * @since 2022-07-05
 */
@Service
public class FileInfoServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements IFileInfoService {

}
