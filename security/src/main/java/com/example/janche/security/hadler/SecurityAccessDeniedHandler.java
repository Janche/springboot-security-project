package com.example.janche.security.hadler;

import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.common.config.IApplicationConfig;
import com.example.janche.security.utils.ResponseUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 用户访问没有权限资源的处理
 * @author daiyp
 * @date
 */

@Component("securityAccessDeniedHandler")
@Slf4j
public class SecurityAccessDeniedHandler implements AccessDeniedHandler {
	@Autowired
	private IApplicationConfig applicationConfig;
	@Override
	public void handle(HttpServletRequest request , HttpServletResponse response , AccessDeniedException accessDeniedException ) throws IOException,
            ServletException {
		log.info(request.getRequestURL()+"没有权限");
		String originHeader = request.getHeader("Origin");
		ResponseUtils.addResponseHeader(response, applicationConfig.getOrigins(), originHeader);
		RestResult rv = new RestResult(ResultCode.LIMITED_AUTHORITY);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		PrintWriter writer = response.getWriter();
		writer.print(rv.toJson());
		writer.flush();

	}

}
