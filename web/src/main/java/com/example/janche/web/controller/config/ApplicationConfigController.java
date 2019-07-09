package com.example.janche.web.controller.config;

import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultGenerator;
import com.example.janche.common.config.ApplicationConfig;
import com.example.janche.web.aop.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Auther: LiaoPeng
 * @Date: 2018/11/20
 */
@RestController
@RequestMapping("/config")
public class ApplicationConfigController {

    @Autowired
    ApplicationConfig applicationConfig;
    /**
     * 返回配置信息
     */
    @Log(description = "获取配置信息", value = 4, type = 1)
    @GetMapping(value = "/info")
    public RestResult info() {
        return ResultGenerator.genSuccessResult(applicationConfig);
    }

}
