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
 * 竣工信息表
 * </p>
 *
 * @author nie
 * @since 2022-07-07
 */
@Getter
@Setter
@TableName("t_completed")
@ApiModel(value = "TCompleted对象", description = "竣工信息表")
public class TCompleted implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty("id")
    private String id;

    @ApiModelProperty("图审资料上报id;和t_reported_data主键关联")
    private String reportedId;

    @ApiModelProperty("开工时间")
    private LocalDateTime startTime;

    @ApiModelProperty("竣工时间")
    private LocalDateTime endTime;

    @ApiModelProperty("设备间")
    private Integer equipmentRoom;

    @ApiModelProperty("电信间")
    private Integer telecommunicationsRoom;

    @ApiModelProperty("配线间")
    private Integer distributionRoom;

    @ApiModelProperty("用户数量")
    private Integer userNumber;

    @ApiModelProperty("管道孔数")
    private Integer pipeHoleNumber;

    @ApiModelProperty("管道长度")
    private Integer pipeLength;

    @ApiModelProperty("物业公司")
    private String realtyManagementCompany;

    @ApiModelProperty("审核状态：1待审核；2资料审核通过；3资料审核不通过；4现场验收通过；5现场验收不通过；6综合审核通过；7综合审核不通过")
    private Integer status;

    @ApiModelProperty("修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty("创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty("逻辑删除")
    private Integer logicDelete;

    @ApiModelProperty("创建人所属部门;支持多部门")
    private String createDept;

    @ApiModelProperty("创建人")
    private String createBy;

    @ApiModelProperty("修改人")
    private String updateBy;

    @ApiModelProperty("申报时间")
    private LocalDateTime reportedTime;


}
