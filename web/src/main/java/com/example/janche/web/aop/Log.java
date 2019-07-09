package com.example.janche.web.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * log annotation
 * <p>
 * use:在方法上打上 @Log("log的描述")
 * 在控制台看到对应数据,后面改为入库操作
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Log {
    /**
     * 增1 删2 改3 查4 登陆5 登出6 导入7 导出8， 默认0
     */
    int value() default 0;

    /**
     * 操作模块类型
     *     1: "首页"
     *     2: "办公系统"
     *     3: "办案系统"
     *     4: "视频录像"
     *     5: "基础管理"
     *     6: "报警管理"
     *     7: "系统管理"
     *     8: "运维管理"
     */
    int type() default 0;

    /**
     * 接口的描述
     */
    String description() default "XXX方法";
}
