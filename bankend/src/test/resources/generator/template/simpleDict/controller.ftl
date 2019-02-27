package ${modulePackage}.web.controller.${moduleName};

import ${modulePackage}.${moduleName}.domain.${modelNameUpperCamel};
import ${modulePackage}.${moduleName}.service.${modelNameUpperCamel}Service;
import ${basePackage}.common.utils.restResult.RestResult;
import ${basePackage}.common.utils.restResult.ResultGenerator;
import ${basePackage}.web.aop.Log;
import ${basePackage}.common.utils.restResult.PageParam;

import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
/**
*
* @author ${author}
* @Description: // TODO 1. 为类添加注释
* @date ${date}
*/
@Slf4j
@RestController
@RequestMapping("${baseRequestMapping}")//TODO 2. 适当修改url
@Api(basePath = "${baseRequestMapping}", tags = "XX模块管理") //TODO 3. 修改tags内容
public class ${modelNameUpperCamel}Controller {
    @Resource
    private ${modelNameUpperCamel}Service ${modelNameLowerCamel}Service;

    //TODO 4. 为实体类的时间类型字段添加注解，指定时间的格式
    //@JsonFormat(pattern="yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")

    @Log
    @PostMapping
    @ApiOperation(value = "新增XX", notes = "单个新增", produces = "application/json") // TODO 5. API方法的说明
    public RestResult add(@ApiParam(name = "XX信息", required = true) ${modelNameUpperCamel} ${modelNameLowerCamel}) { // TODO 6. API方法参数的说明
        ${modelNameLowerCamel}.setCreateTime(new Date());
        ${modelNameLowerCamel}Service.save(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult().setMessage("保存成功");
    }

    @Log
    @DeleteMapping
    @ApiOperation(value = "删除XX", notes = "单个删除", produces = "application/json") // TODO
    public RestResult delete(@ApiParam(name = "XX信息", required = true) Long id) { // TODO
        ${modelNameLowerCamel}Service.deleteById(id);
        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log
    @PutMapping
    @ApiOperation(value = "修改XX", notes = "单个修改" , code = 200, produces = "application/json") // TODO
    public RestResult update(@ApiParam(name = "XX信息", required = true) ${modelNameUpperCamel} ${modelNameLowerCamel}) { // TODO
        ${modelNameLowerCamel}.setModifyTime(new Date());
        ${modelNameLowerCamel}Service.update(${modelNameLowerCamel});
        return ResultGenerator.genSuccessResult().setMessage("修改成功");
    }

    @Log
    @GetMapping
    @ApiOperation(value = "获取XX信息", notes = "单个获取", code = 200, produces = "application/json") // TODO
    public RestResult detail(@ApiParam(value = "主键ID") @RequestParam Long id) {
        ${modelNameUpperCamel} ${modelNameLowerCamel} = ${modelNameLowerCamel}Service.findById(id);
        return ResultGenerator.genSuccessResult(${modelNameLowerCamel});
    }

    /**
     * 用于分页查询,默认可以不用传分页信息
     * 默认值：page=1,size=10,sortField="id",sortOrder="ASC"
     */
    @Log
    @GetMapping(value = "/list")
    @ApiOperation(value = "XX列表分页查询", notes = "分页列表", code = 200, produces = "application/json") // TODO
    public RestResult list(@ApiParam(value = "分页信息") PageParam pageParam,  // TODO
                           @ApiParam(value = "查询条件") @RequestParam(required = false, defaultValue = "") String query) { // TODO
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.list(pageParam, query);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    /**
     * 下拉框查询所有
     */
    @Log
    @ApiOperation(value = "XX列表查询所有", notes = "下拉框列表", code = 200, produces = "application/json") // TODO
    @GetMapping(value = "/all")
    public RestResult listAll() {
        List<${modelNameUpperCamel}> list = ${modelNameLowerCamel}Service.findAll();
        return ResultGenerator.genSuccessResult(list);
    }

}
