package com.example.janche.security.filter;


import com.alibaba.fastjson.JSON;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.security.token.service.TokenService;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * AuthenticationFilter
 *
 * @author daiyp
 * @date 2018/9/13
 */
@Slf4j
public class AuthenticationFilter extends BasicAuthenticationFilter {

	@Autowired
	private TokenService tokenService;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		String jwtoken = request.getHeader("Authorization");
		Enumeration<String> headerNames = request.getHeaderNames();
		//获取获取的消息头名称，获取对应的值，并输出
		while(headerNames.hasMoreElements()){
			String nextElement = headerNames.nextElement();
			logger.debug(nextElement+":"+request.getHeader(nextElement));
		}
		log.debug("进入token验证过滤器");
		log.debug("token:"+jwtoken);
		String sub = "";
		StringBuffer roles = new StringBuffer();
		response.setContentType("application/json;charset=utf-8");
		response.setStatus(HttpServletResponse.SC_OK);
		ArrayList<GrantedAuthority> authorities = new ArrayList<>();
		try{
			if(jwtoken!=null){
				String rolestr = tokenService.getValueFromToken(jwtoken, "roles").toString();
				for(String role: (List<String>) JSON.parseObject(rolestr, List.class)){
					authorities.add( new SimpleGrantedAuthority(role) );
					roles.append(role).append(",");
				}
				sub = tokenService.getValueFromToken(jwtoken, "sub").toString();
				log.debug("token记录权限:"+roles.toString());
				SecurityContextHolder.getContext()
						.setAuthentication(new UsernamePasswordAuthenticationToken(sub, null, authorities));

			}
		}catch(ExpiredJwtException e){
				response.getOutputStream().write(new RestResult<>(ResultCode.TOKEN_EXPIRED).toJson().getBytes());
				return;
		}catch (Exception e) {
			log.debug("用户 令牌不合法:"+jwtoken);
			log.debug("用户 令牌不合法 exception:"+e.getMessage());
			response.getOutputStream().write(new RestResult<>(ResultCode.TOKEN_ILLEGAL).toJson().getBytes());
			return;
		}
		chain.doFilter(request, response);

	}
}
