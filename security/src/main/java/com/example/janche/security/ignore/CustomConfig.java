package com.example.janche.security.ignore;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author lirong
 * @ClassName: CustomConfig
 * @Description: 自定义的配置
 * @date 2019-07-29 17:06
 */
@Configuration
@ConfigurationProperties(prefix = "custom.config")
@Data
public class CustomConfig {
    /**
     * 不需要拦截的地址
     */
    private IgnoreConfig ignores;
}
