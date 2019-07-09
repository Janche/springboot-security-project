package com.example.janche.log.service;

import com.example.janche.log.domain.SysLog;
import com.example.janche.common.core.Service;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.log.dto.SysLogInputDTO;
import com.example.janche.log.dto.SysLogOutpDTO;
import org.springframework.http.ResponseEntity;

import java.util.List;

/**
* @author lirong
* @Description: // TODO 为类添加注释
* @date 2018-12-05 03:21:19
*/
public interface SysLogService extends Service<SysLog> {

    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param inputDTO  查询关键字
     * @return
     */
    List<SysLogOutpDTO> list(PageParam pageParam, SysLogInputDTO inputDTO);

    /**
     * 保存日志
     */
    void saveLog(SysLog sysLog);

    /**
     * 删除日志
     */
    void deleteById(String ids);

    /**
     * 分页导出日志
     * @param inputDTO
     * @return
     */
    ResponseEntity<byte[]> exportLogList(SysLogInputDTO inputDTO);
}
