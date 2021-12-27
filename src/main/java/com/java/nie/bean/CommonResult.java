package com.java.nie.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;


@Data
@ApiModel(description = "接口统一返回类")
public class CommonResult<T> {

    @ApiModelProperty("服务端响应码")
    private String code;

    @ApiModelProperty("服务端响应消息")
    private String message;

    @ApiModelProperty("返回的数据")
    private T data;

    public CommonResult(){

    }

    public CommonResult(String code, String message) {
        this.code = code;
        this.message = message;
    }
}
