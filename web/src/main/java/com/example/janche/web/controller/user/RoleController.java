package com.example.janche.web.controller.user;

import com.example.janche.common.exception.CustomException;
import com.example.janche.common.restResult.PageParam;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.common.restResult.ResultGenerator;
import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.domain.Role;
import com.example.janche.user.domain.RoleAndMenu;
import com.example.janche.user.dto.LoginUserDTO;
import com.example.janche.user.dto.RoleDTO;
import com.example.janche.user.dto.menuRightWeb.FristMenuDTO;
import com.example.janche.user.dto.menuRightWeb.MeunRightWebDTO;
import com.example.janche.user.dto.menuRightWeb.MeunWebDTO;
import com.example.janche.user.dto.menuRightWeb.SecondMenuDTO;
import com.example.janche.user.service.MenuRightService;
import com.example.janche.user.service.RoleAndMenuService;
import com.example.janche.user.service.RoleService;
import com.example.janche.user.service.UserService;
import com.example.janche.web.aop.Log;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
*
* @author CodeGenerator
* @Description: 角色模块管理控制器
* @date 2018-11-08 09:37:24
*/
@Slf4j
@RestController
@RequestMapping("/role")
@Api(basePath = "/role", tags = "角色模块管理")
@Transactional
public class RoleController {
    @Resource
    private RoleService roleService; //角色业务

    @Resource
    private RoleAndMenuService roleAndMenuService;  //角色与权限中间表业务

    @Resource
    private MenuRightService menuRightService; //权限

    @Resource
    private UserService userService;  //用户



    @Log
    @PostMapping("/add")
    @ApiOperation(value = "新增单个角色", notes = "新增单个角色", produces = "application/json")
    public RestResult add(@ApiParam(name = "角色", required = true) Role role) {
        role.setCreateTime(new Date());
        roleService.addRole(role);
        return ResultGenerator.genSuccessResult().setMessage("保存成功");
    }

    @Log
    @DeleteMapping("/delete/{id}")
    @ApiOperation(value = "删除单个角色", notes = "删除单个角色", produces = "application/json")
    public RestResult delete(@PathVariable("id")Long id) {
        roleService.deleteRole(id);
        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log
    @PutMapping("/update")
    @ApiOperation(value = "修改单个角色", notes = "修改单个角色" , code = 200, produces = "application/json")
    public RestResult update(@ApiParam(name = "角色信息", required = true) Role role) {
        role.setModifyTime(new Date());
        roleService.updateRole(role);
        return ResultGenerator.genSuccessResult().setMessage("修改成功");
    }


    @Log
    @GetMapping("/single")
    @ApiOperation(value = "获取单个角色信息", notes = "获取单个角色信息", code = 200, produces = "application/json")
    public RestResult detail(@ApiParam(value = "主键ID") @RequestParam Long id) {
        Role role = roleService.findSingleRole(id);
        return ResultGenerator.genSuccessResult(role);
    }


    @Log
    @ApiOperation(value = "查询所有角色和权限", notes = "获取角色和权限", code = 200, produces = "application/json")
    @GetMapping(value = "/all")
    public RestResult listAll() {
        List<RoleDTO> list = roleService.findAllRole();
        return ResultGenerator.genSuccessResult(list);
    }
    @Log
    @GetMapping(value = "/list")
    @ApiOperation(value = "分页查询角色", notes = "分页查询", code = 200, produces = "application/json")
    public RestResult list(@ApiParam(value = "分页信息") PageParam pageParam) {

        try {
            Role role = SecurityUtils.getCurrentUser().getRoles().get(0);
            String rolename = role.getRoleName();
            if (rolename.equals("超级管理员")) {
                List<Role> list = roleService.list(pageParam);
                PageInfo pageInfo = new PageInfo(list);
                return ResultGenerator.genSuccessResult(pageInfo);
            } else {
                List<Role> list = roleService.findByRoleIndex(role.getId().toString(),pageParam);
                PageInfo pageInfo = new PageInfo(list);
                return ResultGenerator.genSuccessResult(pageInfo);
            }
        }catch (Exception e){
            throw new CustomException(ResultCode.ROLE_LIST_FAIL);
        }

    }

    @Log
    @ApiOperation(value = "通过角色名查询是否有该角色", notes = "返回数量", code = 200, produces = "application/json")
    @GetMapping("/findCountByName")
    public  RestResult findCountByName(String roleName){
            Integer num = roleService.findCountByName(roleName);
            return ResultGenerator.genSuccessResult(num);
    }

    @Log
    @ApiOperation(value = "通过角色获取对应用户", notes = "通过角色id获取所有用户", code = 200, produces = "application/json")
    @GetMapping(value = "/UserList")
    public RestResult findlistByid(@ApiParam(value = "角色ID")  Long id) {
        try {
            RoleDTO role = roleService.findUsersByRoleId(id);
            return ResultGenerator.genSuccessResult(role);
        }catch (Exception e){
            throw new CustomException(ResultCode.ROLE_USERLIST_FAIL);
        }

    }


    @Log(description = "添加角色与对应权限", type = 7, value = 1)
    @ApiOperation(value = "添加角色与对应权限", notes = "menuIdStr用“-”连接menuid",  produces = "application/json")
    @PostMapping(value = "/addRoleAndMenu")
    public RestResult addRoleAndMenu(@RequestBody MeunWebDTO MeunWebDTO) {
        try {
            Role role = new Role();
            Role loginRole=SecurityUtils.getCurrentUser().getRoles().get(0);
            role.setParentId(loginRole.getId());   //添加登录用户角色id
            role.setRoleIndex(loginRole.getRoleIndex());
            role.setRoleName(MeunWebDTO.getName().trim());
            role.setDescription(MeunWebDTO.getDesc());

            //封装权限集合
            List<Long> menuList = this.getMenuList(MeunWebDTO);
            //添加角色
            roleService.addRole(role);
            Role role1 = roleService.findBy("roleName", role.getRoleName());
            //批量添加权限
            List<RoleAndMenu> roleAndMenuList = new ArrayList<>();
            if (menuList.size() != 0) {
                for (int i = 0; i < menuList.size(); i++) {
                    RoleAndMenu roleAndMenu = new RoleAndMenu(role1.getId(), menuList.get(i));
                    roleAndMenuList.add(roleAndMenu);
                }
            }
            roleAndMenuService.addList(roleAndMenuList);
            return ResultGenerator.genSuccessResult().setMessage("添加成功");
        }catch (Exception e){
            throw new CustomException(ResultCode.ROLE_ADD_FAIL);
        }
    }

    @Log
    @DeleteMapping (value = "/deleteRoleAndMenu/{id}")
    @ApiOperation(value = "删除角色与对应权限", notes = "删除角色与对应权限", code = 200, produces = "application/json")
    public RestResult deleteRoleAndMenu(@PathVariable("id")Long id) {

        try {
            //删除对应权限
            roleAndMenuService.deleteByRoleId(id);
            //删除角色
            roleService.deleteRole(id);
            return ResultGenerator.genSuccessResult().setMessage("删除成功");
        }catch (Exception e){
            throw new CustomException(ResultCode.ROLE_DELETE_FAIL);
        }

    }

    @Log(description = "批量删除角色与对应权限", type = 7, value = 2)
    @DeleteMapping (value = "/deleteList")
    @ApiOperation(value = "批量删除角色与对应权限", notes = "批量删除", code = 200, produces = "application/json")
    public RestResult deleteList(String idstr) {

        try {
            String[] idList=idstr.split("-");
            if(idList.length!=0){
                for (int i = 0; i < idList.length; i++) {
                    roleAndMenuService.deleteByRoleId(Long.parseLong(idList[i]));  //删除对应权限
                    roleService.deleteRole(Long.parseLong(idList[i]));   //删除角色
                }
                return ResultGenerator.genSuccessResult().setMessage("删除成功");
            }else{
                return ResultGenerator.genSuccessResult().setMessage("未选择要删除的系统!");
            }
        }catch (Exception e){
            throw new CustomException(ResultCode.ROLE_DELETE_FAIL);
        }
    }


    @Log(description = "修改角色与对应权限", type = 7, value = 3)
    @ApiOperation(value = "修改角色与对应权限", notes = "menuIdStr用“-”连接menuid", code = 200, produces = "application/json")
    @PutMapping(value = "/updateRoleAndMenu")
    public RestResult updateRoleAndMenu(@ApiParam(name = "角色信息")@RequestBody MeunWebDTO meunWebDTO) {

      try {
          //删除对应权限
          roleAndMenuService.deleteByRoleId(meunWebDTO.getId());
          //修改角色
          Role role =new Role();
          role.setParentId(SecurityUtils.getCurrentUser().getRoles().get(0).getId());   //添加登录用户角色id
          role.setId(meunWebDTO.getId());
          role.setRoleName(meunWebDTO.getName().trim());
          role.setDescription(meunWebDTO.getDesc());
          role.setRoleIndex(meunWebDTO.getRoleIndex());
          roleService.updateRole(role);

          //封装权限集合
          List<Long> menuList = this.getMenuList(meunWebDTO);
          //批量添加权限
          List<RoleAndMenu> roleAndMenuList = new ArrayList<>();
          if (menuList.size() != 0) {
              for (int i = 0; i < menuList.size(); i++) {
                  RoleAndMenu roleAndMenu = new RoleAndMenu(role.getId(), menuList.get(i));
                  roleAndMenuList.add(roleAndMenu);
              }
          }
          roleAndMenuService.addList(roleAndMenuList);
          return ResultGenerator.genSuccessResult().setMessage("修改成功");
      }catch (Exception e){
          throw new CustomException(ResultCode.ROLE_UPDATE_FAIL);
      }

    }


    @Log
    @GetMapping("/getRoles")
    @ApiOperation(value = "获取当前登录用户所能操作角色集合", notes = "获取当前用户所能操作角色集合", code = 200, produces = "application/json")
    public RestResult getRoles() {
       try {
           LoginUserDTO userDTO = SecurityUtils.getCurrentUser();
           String rolename = userDTO.getRoles().get(0).getRoleName();
           if (rolename.equals("超级管理员") || rolename.equals("系统管理员")) {
               List<Role> roles = new ArrayList<>();
               List<Role> rolesList = roleService.findAll();
               if (rolesList != null && rolesList.size() != 0) {
                   for (Role role: rolesList) {
                       if(role.getId()!=1){
                           roles.add(role);
                       }
                   }
               }
               return ResultGenerator.genSuccessResult(roles);
           } else {
               //搜索用户以及他下级的角色
               String roleid=userDTO.getRoles().get(0).getId().toString();
               PageParam pageParam=new PageParam();
               pageParam.setSize(2000);
               List<Role> rolesList = roleService.findByRoleIndex(roleid,pageParam); //以用户当前角色来搜索
              return ResultGenerator.genSuccessResult(rolesList);

           }
       }catch (Exception e){
           throw new CustomException(ResultCode.ROLE_USERROLES_FAIL);
       }

    }

    //封装权限集合
    public List<Long> getMenuList(MeunWebDTO MeunWebDTO){

        System.out.println(MeunWebDTO.getSecondMenuDTOS());
        List<Long> menuList=new ArrayList<>();

        MeunRightWebDTO meunRightWebDTO=menuRightService.FindAllMenuRightWebDtO();

        //二层
        Set<Long> parentids=new HashSet<>();
        for (SecondMenuDTO second: MeunWebDTO.getSecondMenuDTOS()) {
            parentids.add(second.getParentId());
            menuList.add(second.getId());
            if(second.getLookids()!=null&&second.getLookids().size()!=0){
                for (Long i : second.getLookids()) {
                    menuList.add(i);
                }
            }
            if(second.getType()==2&&second.getHandleIds()!=null&&second.getHandleIds().size()!=0){
                for (Long i : second.getHandleIds()) {
                    menuList.add(i);
                }
            }
        }
        for (long id:   parentids) {
             menuList.add(id);
        }

        //默认权限一层
        List<FristMenuDTO> list=meunRightWebDTO.getFristMenus();
        for (int i = 0; i <=2 ; i++) {
            menuList.add(list.get(i).getId());
            if(list.get(i).getLookids()!=null&&list.get(i).getLookids().size()!=0){
                for (Long j : list.get(i).getLookids()) {
                    menuList.add(j);
                }
            }
            if(list.get(i).getHandleIds()!=null&&list.get(i).getHandleIds().size()!=0){
                for (Long j : list.get(i).getHandleIds()) {
                    menuList.add(j);
                }
            }
        }


        return  menuList;
    }
}
