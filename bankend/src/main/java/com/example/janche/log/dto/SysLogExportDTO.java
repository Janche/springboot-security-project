package com.example.janche.log.dto;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author lirong
 * @ClassName: ExportOutpDTO
 * @Description: 导出实体类
 * @date 2018-12-07 15:20
 */
@Data
public class SysLogExportDTO implements Serializable {
    private Long id;

    /**
     * 操作类型： 添加-1 删除-2 更新-3 查看-4
     */
    private String operation;

    /**
     * 操作人员名称
     */
    private String username;

    /**
     * 日志描述
     */
    private String logDesc;

    /**
     * 创建时间
     */
    private Date createTime;

}
