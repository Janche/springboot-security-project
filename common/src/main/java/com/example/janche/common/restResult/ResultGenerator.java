package com.example.janche.common.restResult;

/**
 * lirong
 */
public class ResultGenerator {

    public static RestResult genSuccessResult() {
        return new RestResult(ResultCode.SUCCESS);
    }

    public static <T> RestResult<T> genSuccessResult(T data) {

        return new RestResult(200, "SUCCESS", data);
    }

    public static RestResult genFailResult(String message) {
        return new RestResult(ResultCode.FAIL.getCode(), message);
    }

    /**
     * 未验证error 构造器
     * @return
     */
    public static RestResult genUnauthResult() {
        return new RestResult(ResultCode.UNAUTHORIZED);
    }
}
