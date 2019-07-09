package com.example.janche.web.controller.user;

import com.example.janche.common.exception.CustomException;
import com.example.janche.common.utils.MD5Util;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.common.restResult.ResultGenerator;
import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.domain.User;
import com.example.janche.user.service.MenuRightService;
import com.example.janche.user.service.UserAndRoleService;
import com.example.janche.user.service.UserService;
import com.example.janche.web.aop.Log;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;


/**
* @author 张毅
* @date 2018-11-01 09:59:47
*/
@Slf4j
@Api(basePath = "user", value="用户类", tags = "用户相关操作")
@RestController
@RequestMapping("/user")
@Transactional
public class UserController  {
    @Resource
    private UserService userService;

    @Resource
    private UserAndRoleService userAndRoleService;//用户与角色关联业务

    @Resource
    private MenuRightService menuRightService;  //权限菜单业务

    @Autowired
    private SessionRegistry sessionRegistry;

    @Log
    @ApiOperation(value = "添加用户", notes = "时间传null就行" , code = 200, produces = "application/json")
    @PostMapping ("/add")
    public RestResult add(@ApiParam(name = "用户信息", required = true)  User user) {

        userService.addUser(user);
        return ResultGenerator.genSuccessResult().setMessage("添加成功");
    }

    @Log
    @ApiOperation(value = "删除指定用户信息", notes = "删除符合条件指定用户", code = 200, produces = "application/json")
    @DeleteMapping("/delete/{id}")
    public RestResult delete(@PathVariable("id")Long id) {
        userService.deleteUserById(id);
        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log
    @ApiOperation(value = "修改用户密码", notes = "时间传null就行" , code = 200, produces = "application/json")
    @PutMapping("/updatePwd")
    public RestResult update(@RequestBody  User user) {
        user.setPassword(MD5Util.encode(user.getPassword()));
        userService.updateUserPwd(user);
        return ResultGenerator.genSuccessResult().setMessage("修改成功");

    }



    @Log(description = "查询已登录用户密码是否正确", type = 7, value = 3)
    @ApiOperation(value = "查询已登录用户密码是否正确", notes = "查询用户密码是否正确", code = 200, produces = "application/json")
    @GetMapping("/PasswordCheck")
    public RestResult PasswordCheck(@ApiParam(name="password",value="用户密码",required=true) String password) {
        if(MD5Util.encode(password).equals(SecurityUtils.getCurrentUser().getPassword())){
            return ResultGenerator.genSuccessResult(true);
        }else{
            return ResultGenerator.genSuccessResult(false);
        }
    }

    @Log
    @ApiOperation(value = "通过id查询用户信息", notes = "通过id查询用户信息", code = 200, produces = "application/json")
    @GetMapping("/single")
    public RestResult detail(@ApiParam(name="id",value="用户id",required=true) Long id) {
        User user = userService.findById(id);
        return ResultGenerator.genSuccessResult(user);
    }

    @Log
    @ApiOperation(value = "通过账号密码查询用户信息", notes = "通过账号密码查询用户信息", code = 200, produces = "application/json")
    @GetMapping("/findByNameAndPwd")
    public  RestResult findUserByNameAndPassword(String username,String password){
        User user = userService.findByNameAndPwd(username,password);
        return ResultGenerator.genSuccessResult(user);
    }

    @Log
    @ApiOperation(value = "通过账号查询是否有该用户", notes = "通过账号查询是否有该用户", code = 200, produces = "application/json")
    @GetMapping("/findCountByName")
    public  RestResult findCountByName(String username){
        Integer num = userService.findCountByName(username);
        return ResultGenerator.genSuccessResult(num);
    }

    @Log
    @ApiOperation(value = "通过编号查询是否有该用户编号", notes = "返回数量", code = 200, produces = "application/json")
    @GetMapping("/findCountByNum")
    public  RestResult findCountByNum(String userNum){
        Integer num = userService.findCountByUserNum(userNum);
        return ResultGenerator.genSuccessResult(num);
    }

    @Log
    @ApiOperation(value = "查询所有用户列表", notes = "查询所有用户列表", code = 200, produces = "application/json")
    @GetMapping("/all")
    public RestResult list(@ApiParam(name = "页数") @RequestParam Integer page,
                           @ApiParam(name = "每页个数") @RequestParam Integer size) {
        PageHelper.startPage(page, size);
        List<User> list = userService.findAll(page,size);
        PageInfo pageInfo = new PageInfo(list);
        return ResultGenerator.genSuccessResult(pageInfo);
    }

    @Log
    @ApiOperation(value = "删除用户及详细信息", notes = "删除符合条件指定用户", code = 200, produces = "application/json")
    @DeleteMapping("/deleteAll/{id}")
    public RestResult deleteAllMessages(@PathVariable("id")Long id) {

        try {
            //删除角色关联
            userAndRoleService.deleteByUserID(id);
            //删除用户
            userService.deleteUserById(id);
            return ResultGenerator.genSuccessResult().setMessage("删除成功");
        }catch(Exception e){
            throw new CustomException(ResultCode.USER_DELETE_FAIL);
        }

    }

    //批量删除用户及信息
    @Log(description = "批量删除用户及信息", type = 7, value = 2)
    @DeleteMapping("/deleteList")
    @ApiOperation(value = "批量删除用户及信息", notes = "批量删除用户及信息", produces = "application/json")
    public RestResult deleteUserMessages(String idstr) {

        System.out.println(idstr);
        String[] idList=idstr.split("-");
            for (int i = 0; i < idList.length; i++) {

                //删除角色关联
                userAndRoleService.deleteByUserID(Long.parseLong(idList[i]));
                //删除用户
                userService.deleteUserById(Long.parseLong(idList[i]));

            }

        return ResultGenerator.genSuccessResult().setMessage("删除成功");
    }

    @Log(description = "统计在线用户的数量", type = 7, value = 7)
    @ApiOperation(value = "获取在线用户的数量", notes = "导入报表", code = 200, produces = "application/json")
    @PostMapping(value = "/online/userNum")
    public RestResult getOnlineUserNum(){
        List<String> list = sessionRegistry.getAllPrincipals().stream()
                .filter(u -> !sessionRegistry.getAllSessions(u, false).isEmpty())
                .map(Object::toString)
                .collect(Collectors.toList());
        list.forEach(e -> System.out.println(e));
        return ResultGenerator.genSuccessResult(list.size());
    }



}
