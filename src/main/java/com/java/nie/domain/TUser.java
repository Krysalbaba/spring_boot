package com.java.nie.domain;

import com.baomidou.mybatisplus.annotation.*;

import java.io.Serializable;
import java.time.LocalDateTime;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import mybatisPlus.enums.SexEnum;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author nie
 * @since 2021-12-28
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
    @NotNull
    private String loginName;

    @ApiModelProperty("手机号")
    @NotNull
    @Pattern(regexp = "^[1][3456789]\\d{9}$")     //pattern 只能校验字符串类型
    private String phone;

    @ApiModelProperty("邮箱")
    @Email
    @NotNull
    private String email;

    @ApiModelProperty("密码")
    @NotNull
    private String password;

    @ApiModelProperty("性别")
    private SexEnum sex ;

    @ApiModelProperty("是否删除(0:否, 1:是)")
    @TableLogic
    private Integer isDel;

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
