package com.java.nie.common.entity.vo;


import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.java.nie.common.excelconvert.CompletedStatusConvert;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@ApiModel("竣工文件导出返回参数")
public class CompletedExportVo {

    @ApiModelProperty(value = "工程名称")
    @ExcelProperty(value = "工程名称",index = 0)
    private String projectName;

    @ApiModelProperty(value = "建设单位")
    @ExcelProperty(value = "建设单位",index = 1)
    private String constructionUnit;

    @ApiModelProperty(value = "设计单位")
    @ExcelProperty(value = "设计单位",index = 2)
    private String designUnit;

    @ApiModelProperty(value = "施工单位")
    @ExcelProperty(value = "施工单位",index = 3)
    private String processUnit;

    @ApiModelProperty(value = "监理单位")
    @ExcelProperty(value = "监理单位",index = 4)
    private String supervisingUnit;

    @ApiModelProperty(value = "审核状态")
    @ExcelProperty(value = "审核状态",index = 5 ,converter = CompletedStatusConvert.class)
    private Integer status;

    @ApiModelProperty(value = "申报时间")
    @ExcelProperty(value = "申报时间",index = 6)
    @DateTimeFormat("yyyy-MM-dd HH:mm:ss")
    private LocalDateTime reportedTime;
}
