package com.example.janche.log.service.impl;

import com.example.janche.common.core.AbstractService;
import com.example.janche.common.util.PoiUtils;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.common.config.ApplicationConfig;
import com.example.janche.log.dao.SysLogMapper;
import com.example.janche.log.domain.SysLog;
import com.example.janche.log.dto.SysLogExportDTO;
import com.example.janche.log.dto.SysLogInputDTO;
import com.example.janche.log.dto.SysLogOutpDTO;
import com.example.janche.log.service.SysLogService;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
* @author lirong
* @Description: 日志业务层
* @date 2018-12-05 03:21:19
*/
@Slf4j
@Service
@Transactional
public class SysLogServiceImpl extends AbstractService<SysLog> implements SysLogService {
    @Resource
    private SysLogMapper sysLogMapper;

    @Resource
    private ApplicationConfig applicationConfig;

    private static final int FIND = 4;
    /**
     * 根据分页、排序信息和检索条件查询 @size 条 字典表数据
     * @param pageParam 分页参数
     * @param inputDTO  查询关键字
     * @return
     */
    @Override
    public List<SysLogOutpDTO> list(PageParam pageParam, SysLogInputDTO inputDTO) {
        PageHelper.startPage(pageParam.getPage(), pageParam.getSize(), pageParam.getOrderBy());
        return sysLogMapper.selectLogByQuery(inputDTO);
    }

    /**
     * 保存日志
     * @param sysLog
     */
    @Override
    public void saveLog(SysLog sysLog) {
        //todo 查看不做操作 Log注解上没写注释的也不保存
        Integer operation = sysLog.getOperation();
        if (operation != FIND && operation != 0){
            sysLogMapper.insert(sysLog);
        }
    }

    /**
     * 删除日志k
     * @param ids
     */
    @Override
    public void deleteById(String ids) {
        List<String> list = Arrays.stream(ids.split(",")).collect(Collectors.toList());
        Example example = new Example(SysLog.class);
        example.and().andIn("id",list);
        sysLogMapper.deleteByExample(example);
    }

    /**
     * 分页导出日志
     * @param inputDTO
     * @return
     */
    @Override
    public ResponseEntity<byte[]> exportLogList(SysLogInputDTO inputDTO) {
        List<SysLogOutpDTO> data = sysLogMapper.selectLogByQuery(inputDTO);
        Map<Integer, String> sysLogMap = applicationConfig.getLogOperation();
        List<SysLogExportDTO> list = new ArrayList<>();
        Long logId=1L;
        for (SysLogOutpDTO sysLog: data) {
            SysLogExportDTO exportDTO = new SysLogExportDTO();
            exportDTO.setId(logId);
            logId+=1L;
            exportDTO.setOperation(sysLogMap.get(sysLog.getOperation()));
            exportDTO.setUsername(sysLog.getUsername());
            exportDTO.setLogDesc(sysLog.getLogDesc());
            exportDTO.setCreateTime(sysLog.getCreateTime());
            list.add(exportDTO);
        }
        String[] headers = {"序号","操作类型","操作者","操作详情","操作时间"};
        PoiUtils poiUtils = new PoiUtils("日志列表", "日志导出模板.xls");
        poiUtils.setHeaders(headers, "日志列表");

        // 为excel表生成数据
        poiUtils.fillDataAndStyle(list,2);
        // 将内容返回响应
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            poiUtils.getWorkbook().write(bos);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        // 文件名
        String filename = "日志列表";
        try {
            filename = new String(filename.getBytes("gbk"), "iso8859-1");
        } catch (UnsupportedEncodingException e) {
            log.warn("不支持编码格式");
            e.printStackTrace();

        }
        // 设置http响应头
        HttpHeaders header = new HttpHeaders();
        header.add("Content-Disposition", "attachment;filename=" + filename + ".xls");

        return new ResponseEntity<byte[]>(bos.toByteArray(), header, HttpStatus.CREATED);
    }
}
