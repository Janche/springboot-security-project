package com.example.janche.common.exception;

import com.example.janche.common.restResult.ResultCode;
import lombok.Data;

/**
 * 自定义异常
 */
@Data
public class CustomException extends RuntimeException {

    private Integer code;

    private ResultCode resultCode;

    public CustomException(){
    }

    public CustomException(ResultCode resultCode){
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.resultCode = resultCode;
    }
    

    public CustomException(Integer code, String message) {
        super(message);
        this.code = code;
    }

    public CustomException(String message, Throwable cause) {
        super(message, cause);
    }
}
