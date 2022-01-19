package com.java.nie.bean;

import org.springframework.util.StringUtils;

public class ResultGenerator {

    private static final String DEFAULT_SUCCESS_MESSAGE = "success";
    private static final String DEFAULT_FAIL_MESSAGE = "fail";
    private static final String RESULT_CODE_SUCCESS = "200";
    private static final String RESULT_CODE_SERVER_ERROR = "500";


    public static CommonResult genSuccessResult() {
        CommonResult result = new CommonResult();
        result.setCode(RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        return result;
    }

    public static CommonResult genSuccessResult(String message) {
        CommonResult result = new CommonResult();
        result.setCode(RESULT_CODE_SUCCESS);
        result.setMessage(message);

        return result;
    }

    public static <T> CommonResult<T> genSuccessResult(T data) {
        CommonResult<T> result = new CommonResult<T>();
        result.setCode(RESULT_CODE_SUCCESS);
        result.setMessage(DEFAULT_SUCCESS_MESSAGE);
        result.setData(data);
        return result;
    }


    public static CommonResult genFailResult(String message) {
        CommonResult result = new CommonResult();
        result.setCode(RESULT_CODE_SERVER_ERROR);
        if (!StringUtils.hasLength(message)) {
            result.setMessage(DEFAULT_FAIL_MESSAGE);
        } else {
            result.setMessage(message);
        }
        return result;
    }


    public static CommonResult genErrorResult(String code, String message) {
        CommonResult result = new CommonResult();
        result.setCode(code);
        result.setMessage(message);
        result.setData(null);
        return result;
    }


}
