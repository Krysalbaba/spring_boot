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
 * 系统用户表
 * </p>
 *
 * @author nie
 * @since 2022-06-16
 */
@Getter
@Setter
@TableName("sys_user")
@ApiModel(value = "SysUser对象", description = "系统用户表")
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("用户编号")
    private String id;

    @ApiModelProperty("openid")
    private String openid;

    @ApiModelProperty("用户名称")
    private String userName;

    @ApiModelProperty("密码")
    private String password;

    @ApiModelProperty("昵称")
    private String nickName;

    @ApiModelProperty("登录IP")
    private String loginIp;

    @ApiModelProperty("邮箱")
    private String email;

    @ApiModelProperty("电话")
    private String phone;

    @ApiModelProperty("性别")
    private Integer sex;

    @ApiModelProperty("图标名称")
    private String iconImg;

    @ApiModelProperty("租户编号")
    private String tenantId;

    @ApiModelProperty("用户排序")
    private Integer sort;

    @ApiModelProperty("昵称")
    private String petName;

    @ApiModelProperty("用户类型;1-系统用户 2-门户用户")
    private Integer userType;

    @ApiModelProperty("状态;0-未激活 1-正常 2-锁定")
    private Integer state;

    @ApiModelProperty("审批状态;0-待审批 1-通过 2-未通过")
    private Integer approveState;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("逻辑删除")
    private Integer logicDelete;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建人所属部门;支持多部门")
    private String createDept;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改人")
    private String updateBy;


}
