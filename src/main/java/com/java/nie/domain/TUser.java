package com.java.nie.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author nie
 * @since 2021-12-26
 */
@Getter
@Setter
@TableName("t_user")
@ApiModel(value = "TUser对象", description = "用户表")
public class TUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    @ApiModelProperty("用户名")
    private String loginName;

    @ApiModelProperty("手机号")
    private Long phone;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("创建时间")
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime gmtCreate;

    @ApiModelProperty("创建人")
    private String gmtCreator;

    @ApiModelProperty("修改时间")
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime gmtUpdate;

    @ApiModelProperty("修改人")
    private String gmtUpdator;


}
