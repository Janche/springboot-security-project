package com.example.janche.security.hadler;

import com.example.janche.common.config.IApplicationConfig;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.security.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 用户未登录时的处理
 * @author lirong
 * @date 2019-8-8 17:37:27
 */
@Component("securityAuthenticationEntryPoint")
@Slf4j
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Autowired
	private IApplicationConfig applicationConfig;
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
		if(request.getMethod() != HttpMethod.OPTIONS.toString()) {
			log.info("尚未登录:" + authException.getMessage());
		}
		ResponseUtils.renderJson(request, response, ResultCode.UNLOGIN, applicationConfig.getOrigins());
	}
}
