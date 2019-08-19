package com.example.janche.web.controller.user;

import com.example.janche.common.restResult.PageParam;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultGenerator;
import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.dto.LoginUserDTO;
import com.example.janche.user.service.MenuRightService;
import com.example.janche.web.aop.Log;
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
 * @author lirong
 * @date 2019-7-19 09:37:47
 * @desc 权限管理
 */
@Slf4j
@RestController
@RequestMapping("/menu")
@Api(value = "/menu", tags = "权限菜单表")
public class MenuRightController {

    @Resource
    private MenuRightService menuRightService;

    @Log
    @ApiOperation(value = "权限字典分页信息查看", notes = "权限字典，分页查看，需要传入分页信息", produces = "application/json")
    @GetMapping (value = "/list")
    public RestResult queryList( @ApiParam(value = "分页信息") PageParam pageParam,
                                 @ApiParam(value = "查询条件") @RequestParam(required = false, defaultValue = "") String query) {
        List<MenuRight> list = menuRightService.list(pageParam, query);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult().setData(pageInfo);
    }

    @Log(description = "添加权限", type = 2, value = 1)
    @ApiOperation(value = "添加权限", notes = "添加权限", produces = "application/json")
    @PostMapping ("/add")
    public RestResult add(@ApiParam(name = "权限信息", required = true) MenuRight menuRight) {
        Date date=new Date();
        menuRight.setCreateTime(date);
        menuRightService.addMenuRight(menuRight);
        return ResultGenerator.genSuccessResult();
    }


    @Log(description = "删除权限", type = 2, value = 2)
    @ApiOperation(value = "删除权限", notes = "权限", produces = "application/json")
    @DeleteMapping ("/delete")
    public RestResult delete(@ApiParam(name = "权限id", required = true) Long id) {
        menuRightService.deleteMenuRight(id);
        return ResultGenerator.genSuccessResult();
    }

    @Log(description = "修改权限", type = 2, value = 3)
    @ApiOperation(value = "修改权限", notes = "权限字典", produces = "application/json")
    @PutMapping ("/update")
    public RestResult update(@ApiParam(name = "权限信息", required = true) MenuRight menuRight) {
        menuRightService.updateMenuRight(menuRight);
        return ResultGenerator.genSuccessResult();
    }

    @Log
    @ApiOperation(value = "权限明细", notes = "权限明细", produces = "application/json")
    @GetMapping ("/detail")
    public RestResult detail(@ApiParam(name = "权限id", required = true) Long id) {
        MenuRight menuRight = menuRightService.findOne(id);
        return ResultGenerator.genSuccessResult(menuRight);
    }

    @Log
    @ApiOperation(value = "获取所有权限信息", notes = "获取所有权限信息", produces = "application/json")
    @GetMapping ("/all")
    public RestResult findAll() {
        List<MenuRight> list = menuRightService.findAll();
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }


    @Log
    @ApiOperation(value = "下拉框系统列表", notes = "下拉框系统列表", produces = "application/json")
    @GetMapping ("/system")
    public RestResult getSystemList() {
        List<MenuRight> list = menuRightService.getSystemList();
        return ResultGenerator.genSuccessResult(list);
    }

    @Log
    @ApiOperation(value = "检查用户是否有此按钮权限", notes = "检查用户是否有此按钮权限", produces = "application/json")
    @GetMapping ("/check/url")
    public RestResult checkUrl(String url) {
        Boolean hasButton = false;
        LoginUserDTO userDTO = SecurityUtils.getCurrentUser();
        // 判断登录用户是否为管理员
        if (userDTO.getId() == 1L) {
            hasButton = true;
        }else{
            List<MenuRight> menus = userDTO.getMenus();
            for (MenuRight menuRight: menus) {
                if(menuRight.getUrl().equals(url)){
                    hasButton = true;
                }
            }
        }
        return ResultGenerator.genSuccessResult(hasButton);
    }
}
