package com.example.janche.web.controller.user;

import com.example.janche.common.restResult.PageParam;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultGenerator;
import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.domain.Role;
import com.example.janche.user.dto.TreeNodeDTO;
import com.example.janche.user.dto.role.RoleConditionDTO;
import com.example.janche.user.dto.role.RoleInputDTO;
import com.example.janche.user.dto.role.RoleOutpDTO;
import com.example.janche.user.service.RoleService;
import com.example.janche.web.aop.Log;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * @author lirong
 * @Description: 角色管理
 * @date 2019-7-18 17:47:06
 */
@Slf4j
@RestController
@RequestMapping("/role")
@Api(value = "/role", tags = "角色管理")
public class RoleController {
    @Resource
    private RoleService roleService;

    @Log(value = 1, description = "新增角色", type = 2)
    @PostMapping("/add")
    @ApiOperation(value = "新增角色", notes = "新增角色", produces = "application/json")
    public RestResult add(@ApiParam(name = "角色", required = true) RoleInputDTO inputDTO) {
        roleService.addRole(inputDTO);
        return ResultGenerator.genSuccessResult().setMessage("保存成功");
    }

    @Log(value = 2, description = "删除角色", type = 2)
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除角色", notes = "删除角色", produces = "application/json")
    public RestResult delete(@PathVariable("id") Long id) {
        roleService.deleteRole(id);
        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log(value = 2, description = "批量删除角色", type = 2)
    @DeleteMapping("/delete/batch/{ids}")
    @ApiOperation(value = "批量删除角色", notes = "批量删除角色", produces = "application/json")
    public RestResult delete(@ApiParam(name = "角色ID集合", required = true) @PathVariable("ids") String ids) {
        roleService.deleteRole(ids);
        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log(value = 3, description = "批量冻结角色", type = 2)
    @PostMapping("/froze/batch")
    @ApiOperation(value = "批量冻结角色", notes = "批量冻结角色", produces = "application/json")
    public RestResult frozeRole(@ApiParam(name = "角色ID集合", required = true) String ids,
                                @ApiParam(name = "冻结状态(0-冻结，1-解冻)", required = true) Integer status) {
        roleService.frozeRole(ids, status);
        return ResultGenerator.genSuccessResult().setMessage("冻结成功");
    }

    @Log(value = 3, description = "修改角色", type = 2)
    @PutMapping("/update")
    @ApiOperation(value = "修改角色", notes = "修改角色" , produces = "application/json")
    public RestResult update(@ApiParam(name = "角色信息", required = true) RoleInputDTO inputDTO) {
        roleService.updateRole(inputDTO);
        return ResultGenerator.genSuccessResult().setMessage("修改成功");
    }

    @Log
    @GetMapping("/detail")
    @ApiOperation(value = "获取角色信息", notes = "获取角色信息", produces = "application/json")
    public RestResult detail(@ApiParam(value = "主键ID") @RequestParam Long id) {
        RoleOutpDTO dto = roleService.findOne(id);
        return ResultGenerator.genSuccessResult(dto);
    }

    @Log
    @GetMapping("/menu/{ids}")
    @ApiOperation(value = "获取权限Ids", notes = "获取权限Ids", produces = "application/json")
    public RestResult getMenus(@ApiParam(name = "角色ID集合", required = true) @PathVariable("ids") String ids) {
        Set<Long> list = roleService.getMenuIdsByRoleIds(ids);
        return ResultGenerator.genSuccessResult(list);
    }

    @Log
    @GetMapping(value = "/list")
    @ApiOperation(value = "分页查询角色", notes = "分页查询", produces = "application/json")
    public RestResult list(@ApiParam(value = "分页信息") PageParam pageParam,
                           @ApiParam(value = "筛选条件") RoleConditionDTO dto) {
        List<Role> list = roleService.list(pageParam, dto, SecurityUtils.getCurrentUser());
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Log
    @GetMapping(value = "/all")
    @ApiOperation(value = "角色列表", notes = "下拉框角色列表", produces = "application/json")
    public RestResult listAll() {
        List<Role> list = roleService.listAll();
        return ResultGenerator.genSuccessResult(list);
    }

    @Log
    @GetMapping("/menu/tree")
    @ApiOperation(value = "获取权限树", notes = "获取权限树", produces = "application/json")
    public RestResult menuTree() {
        TreeNodeDTO nodeDTO = roleService.getMenuTree();
        return ResultGenerator.genSuccessResult(nodeDTO.getChildren());
    }

}
