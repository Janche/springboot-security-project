package com.example.janche.web.controller;

import com.example.janche.common.exception.CustomException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import springfox.documentation.annotations.ApiIgnore;

@Controller
@ApiIgnore
public class ExceptionController {

    /**
     * 根据状态码跳转对应错误页面
     * 在reources/reources/error/500.html 创建
     */
    @GetMapping("/test/exception")
    public void testError() {
        throw new RuntimeException("500");
    }

    /**
     * 自定义异常并返回错误消息到前端
     */
    @GetMapping("/test/customException")
    public void testCustomError() {
        try {
            int i = 1 / 0;
        } catch (Exception e) {
            e.printStackTrace();
            // 不返回exception msg
            //throw new CustomException("custom error test", StringUtils.EMPTY);
            // 返回exception msg
            throw new CustomException(102, e.getMessage());
        }
    }
}
