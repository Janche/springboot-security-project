package com.example.janche.log.dao;

import com.example.janche.common.core.Mapper;
import com.example.janche.log.domain.SysLog;
import com.example.janche.log.dto.SysLogExportDTO;
import com.example.janche.log.dto.SysLogInputDTO;
import com.example.janche.log.dto.SysLogOutpDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper extends Mapper<SysLog> {
    List<SysLogOutpDTO> selectLogByQuery(@Param("dto") SysLogInputDTO inputDTO);

    List<SysLogExportDTO> exportLogList(@Param("dto") SysLogInputDTO inputDTO);
}