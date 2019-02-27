package com.example.core.log.dao;

import com.example.core.common.core.Mapper;
import com.example.core.log.domain.SysLog;
import com.example.core.log.dto.SysLogExportDTO;
import com.example.core.log.dto.SysLogInputDTO;
import com.example.core.log.dto.SysLogOutpDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper extends Mapper<SysLog> {
    List<SysLogOutpDTO> selectLogByQuery(@Param("dto") SysLogInputDTO inputDTO);

    List<SysLogExportDTO> exportLogList(@Param("dto") SysLogInputDTO inputDTO);
}