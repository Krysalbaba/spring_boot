package com.java.nie.domain;

import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 文件上传
 * </p>
 *
 * @author nie
 * @since 2022-07-05
 */
@Getter
@Setter
@TableName("file_info")
@ApiModel(value = "FileInfo对象", description = "文件上传")
public class FileInfo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("主键id")
    private String id;

    @ApiModelProperty("文件id")
    private String fid;

    @ApiModelProperty("文件名称")
    private String fileName;

    @ApiModelProperty("附件类型名称")
    private String name;

    @ApiModelProperty("文件地址")
    private String url;

    @ApiModelProperty("文件大小")
    private Long size;

    @ApiModelProperty("文件类型")
    private String type;

    @ApiModelProperty("类型:1头像；2用户基本信息--营业执照；3用户基本信息--委托书；4用户基本信息--资质证明")
    private String fileType;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("删除标识")
    private Integer logicDelete;

    @ApiModelProperty("业务主键")
    private String businessId;

    @ApiModelProperty("创建人所属部门")
    private String createDept;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;


}
