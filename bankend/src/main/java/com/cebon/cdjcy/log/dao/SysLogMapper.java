package com.cebon.cdjcy.log.dao;

import com.cebon.cdjcy.common.core.Mapper;
import com.cebon.cdjcy.log.domain.SysLog;
import com.cebon.cdjcy.log.dto.SysLogExportDTO;
import com.cebon.cdjcy.log.dto.SysLogInputDTO;
import com.cebon.cdjcy.log.dto.SysLogOutpDTO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysLogMapper extends Mapper<SysLog> {
    List<SysLogOutpDTO> selectLogByQuery(@Param("dto") SysLogInputDTO inputDTO);

    List<SysLogExportDTO> exportLogList(@Param("dto") SysLogInputDTO inputDTO);
}