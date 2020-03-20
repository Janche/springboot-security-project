package com.example.janche.security.hadler;

import com.example.janche.common.config.IApplicationConfig;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.common.util.IPUtils;
import com.example.janche.log.domain.SysLog;
import com.example.janche.log.service.SysLogService;
import com.example.janche.security.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

@Component("securityAuthenticationFailureHandler")
@Slf4j
public class SecurityAuthenticationFailureHandler implements AuthenticationFailureHandler {
	@Autowired
	private IApplicationConfig applicationConfig;
	@Resource
	private SysLogService sysLogService;
	@Override
	public void onAuthenticationFailure(HttpServletRequest request , HttpServletResponse response , AuthenticationException exception) {
		// 记录登录失败的日志
		this.saveLog(request, exception);
		log.info("登录失败: "+ exception.getMessage());
		// JSON 格式的返回
		ResultCode.LOGIN_ERROR.message = exception.getMessage();
		// 1. 返回json让前端自己处理
		// ResponseUtils.renderJson(request, response, ResultCode.LOGIN_ERROR, applicationConfig.getOrigins());

		// 2. 由后端直接跳转到登录页面
		try {
			response.sendRedirect(request.getContextPath() + "/static/login.html");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 记录登录失败的日志
	 * @param request
	 */
	private void saveLog(HttpServletRequest request, AuthenticationException exception) {
		SysLog sysLog = new SysLog();
		sysLog.setOperation(5);
		sysLog.setLogUser(null);
		sysLog.setCreateTime(new Date());
		sysLog.setLogIp(IPUtils.getIpAddr(request));
		sysLog.setLogDesc(exception.getMessage() +" 登录系统失败 ");
		sysLog.setLogMethod(request.getMethod());
		sysLog.setLogType(1);
		sysLogService.saveLog(sysLog);
	}
}
