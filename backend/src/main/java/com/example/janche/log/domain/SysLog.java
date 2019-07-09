package com.example.janche.log.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;
@Data
@Table(name = "sys_log")
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SysLog implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 日志类型  根据系统模块来定义日志类型
     */
    @Column(name = "log_type")
    private Integer logType;

    /**
     * 操作类型： 添加-1 删除-2 更新-3 查看-4
     */
    private Integer operation;

    /**
     * 操作人员ID
     */
    @Column(name = "log_user")
    private Long logUser;

    /**
     * 访问IP
     */
    @Column(name = "log_ip")
    private String logIp;

    /**
     * 请求方法
     */
    @Column(name = "log_method")
    private String logMethod;

    /**
     * 请求参数
     */
    @Column(name = "log_params")
    private String logParams;

    /**
     * 日志描述
     */
    @Column(name = "log_desc")
    private String logDesc;

    /**
     * 响应时间
     */
    @Column(name = "log_time")
    private Long logTime;

    /**
     * 异常码
     */
    @Column(name = "exception_code")
    private String exceptionCode;

    /**
     * 异常描述
     */
    @Column(name = "exception_detail")
    private String exceptionDetail;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 用户名
     */
    @Transient
    private String username;

    /**
     * 操作类型
     */
    @Transient
    private String logOperation;

}