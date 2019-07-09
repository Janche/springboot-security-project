package com.example.janche.security.hadler;

import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.common.config.IApplicationConfig;
import com.example.janche.security.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户未登录时的处理
 * @author daiyp
 * @date 2018-9-26
 */

@Component("securityAuthenticationEntryPoint")
@Slf4j
public class SecurityAuthenticationEntryPoint implements AuthenticationEntryPoint {
	@Autowired
	private IApplicationConfig applicationConfig;
	@Override
	public void commence(HttpServletRequest request , HttpServletResponse response , AuthenticationException authException ) throws IOException,
            ServletException {
		if(request.getMethod() != HttpMethod.OPTIONS.toString()) {
			log.info("尚未登录:" + authException.getMessage());
		}
		String originHeader = request.getHeader("Origin");
		ResponseUtils.addResponseHeader(response, applicationConfig.getOrigins(), originHeader);
		RestResult rv = new RestResult<>(ResultCode.UNLOGIN);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(rv.toJson());
		writer.flush();
	}
}
