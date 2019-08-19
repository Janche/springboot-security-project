package com.example.janche.web.controller.user;

import com.example.janche.common.restResult.PageParam;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultGenerator;
import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.MenuDTO;
import com.example.janche.user.dto.user.UserConditionDTO;
import com.example.janche.user.dto.user.UserInputDTO;
import com.example.janche.user.dto.user.UserOutpDTO;
import com.example.janche.user.dto.user.UserPwdDTO;
import com.example.janche.user.service.MenuRightService;
import com.example.janche.user.service.UserService;
import com.example.janche.web.aop.Log;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;


/**
 * @author lirong
 * @desc 用户管理
 * @date 2019-7-18 17:18:09
 */
@Slf4j
@Api(value = "/user", tags = "用户管理")
@RestController
@RequestMapping("/user")
public class UserController  {
    @Resource
    private UserService userService;

    @Resource
    private MenuRightService menuRightService;

    @Autowired
    private SessionRegistry sessionRegistry;

    @Log(value = 1, description = "添加用户", type = 2)
    @ApiOperation(value = "添加用户", notes = "时间传null就行" , produces = "application/json")
    @PostMapping ("/add")
    public RestResult add(@ApiParam(name = "用户信息", required = true) UserInputDTO inputDTO) {
        userService.addUser(inputDTO);
        return ResultGenerator.genSuccessResult().setMessage("添加成功");
    }

    @Log(value = 2, description = "删除用户", type = 2)
    @ApiOperation(value = "删除用户", notes = "删除用户", produces = "application/json")
    @DeleteMapping("/delete/{id}")
    public RestResult delete(@PathVariable("id") Long id) {
        userService.deleteUser(id);
        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log(value = 2, description = "批量删除用户", type = 2)
    @ApiOperation(value = "批量删除用户", notes = "批量删除用户", produces = "application/json")
    @DeleteMapping("/delete/batch/{ids}")
    public RestResult delete(@ApiParam(name = "用户ID集合", required = true) @PathVariable("ids") String ids) {
        userService.deleteUser(ids);
        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log(value = 3, description = "修改用户", type = 2)
    @ApiOperation(value = "修改用户", notes = "修改用户", produces = "application/json")
    @PutMapping("/update")
    public RestResult update(@ApiParam(name = "用户信息", required = true) @RequestBody UserInputDTO inputDTO) {
        userService.updateUser(inputDTO);
        return ResultGenerator.genSuccessResult().setMessage("修改成功");
    }

    @Log
    @ApiOperation(value = "通过id查询用户信息", notes = "通过id查询用户信息", produces = "application/json")
    @GetMapping("/detail")
    public RestResult detail(@ApiParam(name="id",value="用户id", required=true) Long id) {
        UserOutpDTO user = userService.findOne(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @Log
    @ApiOperation(value = "查询所有用户列表", notes = "查询所有用户列表", produces = "application/json")
    @GetMapping("/list")
    public RestResult list(@ApiParam(value = "分页信息") PageParam pageParam,
                           @ApiParam(value = "筛选条件") UserConditionDTO dto) {
        List<User> list = userService.findAll(pageParam, dto);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Log
    @ApiOperation(value = "下拉框用户列表", notes = "下拉框用户列表", produces = "application/json")
    @GetMapping("/all")
    public RestResult all() {
        List<User> list = userService.listAll();
        return ResultGenerator.genSuccessResult(list);
    }

    @Log(value = 3, description = "修改密码", type = 2)
    @ApiOperation(value = "修改密码", notes = "修改用户密码" , produces = "application/json")
    @PutMapping("/updatePwd")
    public RestResult updatePwd(@ApiParam(value = "筛选条件") UserPwdDTO dto) {
        dto.setId(SecurityUtils.getLoginUserId());
        userService.updateUserPwd(dto);
        return ResultGenerator.genSuccessResult().setMessage("密码修改成功");
    }

    @Log(value = 3, description = "重置密码", type = 2)
    @ApiOperation(value = "重置密码", notes = "重置用户密码" , produces = "application/json")
    @PutMapping("/resetPwd")
    public RestResult resetPwd(@ApiParam(value = "用户ID") Long userId) {
        userService.resetUserPwd(userId);
        return ResultGenerator.genSuccessResult().setMessage("密码重置成功");
    }

    @Log(value = 3, description = "冻结用户", type = 2)
    @ApiOperation(value = "冻结用户", notes = "冻结用户", produces = "application/json")
    @PostMapping("/froze/batch")
    public RestResult frozeUser(@ApiParam(name = "用户ID集合", required = true) String ids,
                                @ApiParam(name = "冻结状态", required = true) Integer status) {
        userService.frozeUser(ids, status);
        return ResultGenerator.genSuccessResult().setMessage("冻结成功");
    }

    @Log(description = "统计在线用户的数量", type = 2, value = 4)
    @ApiOperation(value = "获取在线用户的数量", notes = "获取在线用户的数量", produces = "application/json")
    @GetMapping(value = "/online/userNum")
    public RestResult getOnlineUserNum(){
        List<String> list = sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(Object::toString)
                .collect(Collectors.toList());
        return ResultGenerator.genSuccessResult(list.size());
    }


    /**
     * *******************开放给SSO客户端使用的接口 *******************
     */
    @Log
    @ApiOperation(value = "获取登录用户", notes = "SSO客户端使用接口", produces = "application/json")
    @PostMapping("/oauth/sso")
    public User getSsoUserId(Principal user) {
        OAuth2Authentication u = (OAuth2Authentication) user;
        String username = u.getPrincipal().toString();
        User currUser = userService.findBy("username", username);
        return currUser;
    }

    @Log
    @ApiOperation(value = "下拉框用户列表", notes = "SSO客户端使用接口", produces = "application/json")
    @PostMapping("/oauth/all")
    public List<User> getSsoUserList() {
        List<User> list = userService.listAll();
        return list;
    }

    @Log
    @ApiOperation(value = "用户所有的权限", notes = "SSO客户端使用接口", produces = "application/json")
    @PostMapping("/oauth/menu")
    public List<MenuDTO> getSsoUserMenus() {
        Long userId = SecurityUtils.getLoginUserId();
        return menuRightService.getUserMenus(userId);
    }
}
