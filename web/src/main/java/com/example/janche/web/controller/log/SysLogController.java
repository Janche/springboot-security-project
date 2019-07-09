package com.example.janche.web.controller.log;

import com.example.janche.log.domain.SysLog;
import com.example.janche.log.dto.SysLogInputDTO;
import com.example.janche.log.dto.SysLogOutpDTO;
import com.example.janche.log.service.SysLogService;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultGenerator;
import com.example.janche.web.aop.Log;
import com.example.janche.common.restResult.PageParam;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
*
* @author lirong
* @Description: 日志管理模块
* @date 2018-12-05 03:21:19
*/
@Slf4j
@RestController
@RequestMapping("/log")
@Api(basePath = "/log", tags = "日志模块管理")
public class SysLogController {
    @Resource
    private SysLogService sysLogService;

    @Log(description = "删除日志", value = 1, type = 9)
    @PostMapping
    @ApiOperation(value = "新增日志", notes = "单个新增", produces = "application/json")
    public RestResult add(@ApiParam(name = "日志信息", required = true) SysLog sysLog) {
        sysLog.setCreateTime(new Date());
        sysLogService.save(sysLog);
        return ResultGenerator.genSuccessResult().setMessage("保存成功");
    }

    @Log(description = "删除日志", value = 2, type = 9)
    @DeleteMapping("/{ids}")
    @ApiOperation(value = "删除日志", notes = "单个删除", produces = "application/json")
    public RestResult delete(@ApiParam(name = "日志信息", required = true)@PathVariable("ids") String ids) {
        sysLogService.deleteById(ids);
        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log(description = "修改日志", value = 3, type = 9)
    @PutMapping
    @ApiOperation(value = "修改日志", notes = "单个修改" , code = 200, produces = "application/json")
    public RestResult update(@ApiParam(name = "日志信息", required = true) SysLog sysLog) {
        sysLogService.update(sysLog);
        return ResultGenerator.genSuccessResult().setMessage("修改成功");
    }

    @Log(description = "单个获取", value = 4, type = 9)
    @GetMapping
    @ApiOperation(value = "获取日志信息", notes = "单个获取", code = 200, produces = "application/json")
    public RestResult detail(@ApiParam(value = "主键ID") @RequestParam Long id) {
        SysLog sysLog = sysLogService.findById(id);
        return ResultGenerator.genSuccessResult(sysLog);
    }

    /**
     * 用于分页查询,默认可以不用传分页信息
     * 默认值：page=1,size=10,sortField="id",sortOrder="ASC"
     */
    @Log(description = "日志分页列表", value = 4, type = 9)
    @GetMapping(value = "/list")
    @ApiOperation(value = "日志列表分页查询", notes = "分页列表", code = 200, produces = "application/json")
    public RestResult list(@ApiParam(value = "分页信息") PageParam pageParam,
                           @ApiParam(value = "查询条件") SysLogInputDTO inputDTO) {
        List<SysLogOutpDTO> list = sysLogService.list(pageParam, inputDTO);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 下拉框查询所有
     */
    @Log(description = "日志列表查询所有", value = 4, type = 9)
    @ApiOperation(value = "日志列表查询所有", notes = "下拉框列表", code = 200, produces = "application/json") // TODO
    @GetMapping(value = "/all")
    public RestResult listAll() {
        List<SysLog> list = sysLogService.findAll();
        return ResultGenerator.genSuccessResult(list);
    }

    /**
     * 导出日志
     * @return
     */
    @Log(description = "日志分页列表", value = 8, type = 8)
    @ApiOperation(value = "导出日志列表", notes = "导出报表", code = 200, produces = "application/json")
    @GetMapping(value = "/export")
    public ResponseEntity<byte[]> exportSysLogList(@ApiParam(value = "查询条件") SysLogInputDTO inputDTO){
        return sysLogService.exportLogList(inputDTO);
    }
}
