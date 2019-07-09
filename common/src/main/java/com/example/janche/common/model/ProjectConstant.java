package com.example.janche.common.model;

import lombok.extern.slf4j.Slf4j;

/**
 * 项目常量
 */
@Slf4j
public final class ProjectConstant {

    /**
     * 项目基础包名称
     */
    public static final String BASE_PACKAGE = "com.example.janche";
    /**
     * Model所在包
     */
    public static final String MODEL_PACKAGE = BASE_PACKAGE + ".*.*.domain";
    /**
     * DAO所在包
     */
    public static final String DAO_PACKAGE = BASE_PACKAGE + ".*.dao";
    public static final String MAPPER_XML_PACKAGE = "classpath:mapper/*/*.xml";
    public static final String SERVICE_PACKAGE = BASE_PACKAGE + ".*.*.service";
    public static final String SERVICE_IMPL_PACKAGE = SERVICE_PACKAGE + ".impl";
    public static final String CONTROLLER_PACKAGE = BASE_PACKAGE + ".*.*.controller";
    /**
     * Mapper插件基础接口的完全限定名
     */
    public static final String MAPPER_INTERFACE_REFERENCE = BASE_PACKAGE + ".common.core.Mapper";
}
