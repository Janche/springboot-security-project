package com.example.janche.log.dto;

import lombok.Data;

import java.io.Serializable;

/**
 * @author lirong
 * @ClassName: SysLogInputDTO
 * @Description: TODO
 * @date 2018-12-07 9:38
 */
@Data
public class SysLogInputDTO implements Serializable {

    /**
     * 日志详情
     */
    private String logDesc;

    /**
     * 操作人员
     */
    private String logType;

    /**
     * 操作类型
     */
    private String operation;

    /**
     * 开始时间
     */
    private String startTime;

    /**
     * 结束时间
     */
    private String endTime;
}
