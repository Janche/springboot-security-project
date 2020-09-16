package com.example.janche.swagger;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableKnife4j
@Import(BeanValidatorPluginsConfiguration.class)
@EnableConfigurationProperties(SwaggerProperties.class)
public class SwaggerConfig {
    @Autowired
    private SwaggerProperties swaggerProperties;

    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 自行修改为自己的包路径
                .apis(RequestHandlerSelectors.basePackage(swaggerProperties.getBasepackage()))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        ApiInfoBuilder apiInfoBuilder = new ApiInfoBuilder();
        if (swaggerProperties.getTitle() != null) {
            apiInfoBuilder.title(swaggerProperties.getTitle());
        }
        if (swaggerProperties.getContractName() != null && swaggerProperties.getContractUrl() != null
                && swaggerProperties.getContractEmail() != null) {
            apiInfoBuilder.contact(new Contact(swaggerProperties.getContractName(), swaggerProperties.getContractUrl(),
                    swaggerProperties.getContractEmail()));
        }
        if (swaggerProperties.getVersion() != null) {
            apiInfoBuilder.version(swaggerProperties.getVersion());
        }
        return apiInfoBuilder.build();
    }
}
