package com.example.janche.security.hadler;

import com.example.janche.common.config.IApplicationConfig;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.security.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 用户访问没有权限资源的处理
 * @author lirong
 * @date
 */

@Component("securityAccessDeniedHandler")
@Slf4j
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
	@Autowired
	private IApplicationConfig applicationConfig;
	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) {
		log.info(request.getRequestURL()+"没有权限");
		ResponseUtils.renderJson(request, response, ResultCode.LIMITED_AUTHORITY, applicationConfig.getOrigins());
	}
}
