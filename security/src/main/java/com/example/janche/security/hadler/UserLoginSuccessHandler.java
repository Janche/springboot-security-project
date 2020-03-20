package com.example.janche.security.hadler;


import com.alibaba.fastjson.JSON;
import com.example.janche.common.config.IApplicationConfig;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.util.IPUtils;
import com.example.janche.log.domain.SysLog;
import com.example.janche.log.service.SysLogService;
import com.example.janche.security.utils.ResponseUtils;
import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.dao.MenuRightMapper;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.dto.LoginOutpDTO;
import com.example.janche.user.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@Component("userLoginSuccessHandler")
@Slf4j
public class UserLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Resource
    private MenuRightMapper menuRightMapper;
    @Autowired
    private IApplicationConfig applicationConfig;
    @Resource
    private SysLogService sysLogService;


    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {

        //获得授权后可得到用户信息   可使用SecurityUserService进行数据库操作
        LoginUserDTO userDetails = (LoginUserDTO) authentication.getPrincipal();
        Long userId = userDetails.getId();
        List<MenuRight> menus = menuRightMapper.getUserMenus(userId);
        LoginOutpDTO dto = new LoginOutpDTO();
        BeanUtils.copyProperties(userDetails, dto);
        dto.setMenus(menus);

        //输出登录提示信息
        log.info("用户 " + userDetails.getUsername() + " 登录");
        log.info("用户 " + JSON.toJSONString(authentication.getAuthorities()) + "角色权限");
        log.info("IP :"+ IPUtils.getIpAddr(request));

        // 记录登录成功的日志
        this.saveLog(request, authentication);
        // JSON 格式的返回
        ResponseUtils.renderSuccessJson(request, response, new RestResult(200, "登录成功", dto), applicationConfig.getOrigins());
    }


    /**
     * 记录登录成功的日志
     * @param request
     */
    private void saveLog(HttpServletRequest request, Authentication authentication) {
        LoginUserDTO user = (LoginUserDTO) authentication.getPrincipal();
        SysLog sysLog = SecurityUtils.buildLog(request, authentication);
        sysLog.setOperation(5);
        sysLog.setLogDesc(user.getUsername() + " 登录了系统 ");
        sysLog.setLogType(2);
        sysLogService.saveLog(sysLog);
    }
}

