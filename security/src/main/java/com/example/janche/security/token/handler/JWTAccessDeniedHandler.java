package com.example.janche.security.token.handler;

import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JWTAccessDeniedHandler implements AccessDeniedHandler {

	private  static  final Logger logger = LoggerFactory.getLogger(JWTAccessDeniedHandler.class);

	@Override
	public void handle(HttpServletRequest request , HttpServletResponse response , AccessDeniedException accessDeniedException ) throws IOException,
            ServletException {
		logger.debug("权限不足:"+accessDeniedException.getMessage());
		response.setContentType("application/json");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(new RestResult<>(ResultCode.LIMITED_AUTHORITY).toJson().getBytes());
	}

}
