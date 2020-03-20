package com.example.janche.security.hadler;

import com.example.janche.common.config.IApplicationConfig;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.log.domain.SysLog;
import com.example.janche.log.service.SysLogService;
import com.example.janche.security.utils.ResponseUtils;
import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.dto.LoginUserDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@Component("securityLogoutSuccessHandler")
@Slf4j
public class SecurityLogoutSuccessHandler implements LogoutSuccessHandler {
	@Autowired
	private IApplicationConfig applicationConfig;
	@Resource
	private SysLogService sysLogService;
	@Override
	public void onLogoutSuccess(HttpServletRequest request , HttpServletResponse response , Authentication authentication) {

		// 清除登录的session
		request.getSession().invalidate();

		// 记录登出日志
		if (null != authentication) {
			this.saveLog(request, authentication);
		}
		log.info("退出成功");
		// JSON 格式的返回
		ResponseUtils.renderSuccessJson(request, response, new RestResult(200, "退出成功"), applicationConfig.getOrigins());

	}

	/**
	 * 记录登出日志
	 * @param request
	 */
	private void saveLog(HttpServletRequest request, Authentication authentication) {
		LoginUserDTO user = (LoginUserDTO) authentication.getPrincipal();
		SysLog sysLog = SecurityUtils.buildLog(request, authentication);
		sysLog.setOperation(6);
		sysLog.setLogDesc(user.getUsername() + " 退出了系统 ");
		sysLog.setLogType(2);
		sysLogService.saveLog(sysLog);
	}

}
