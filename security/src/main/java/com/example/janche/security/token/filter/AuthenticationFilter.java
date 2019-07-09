package com.example.janche.security.token.filter;

import com.alibaba.fastjson.JSON;
import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.security.token.service.TokenService;
import com.example.janche.user.domain.Role;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.UserDTO;
import com.example.janche.user.service.UserService;
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
 *token过滤
 * @author daiyp
 * @date 2018/9/27
 */
@Slf4j
public class AuthenticationFilter extends BasicAuthenticationFilter {

	@Autowired
	private TokenService tokenService;

	@Autowired
    private UserService userService;

	public AuthenticationFilter(AuthenticationManager authenticationManager) {
		super(authenticationManager);
	}

	@SuppressWarnings("unchecked")
	@Override
    public void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, FilterChain filterChain) throws IOException, ServletException {
    	String jwtoken = httpServletRequest.getHeader("Authorization");
    	String refresh_token = httpServletRequest.getHeader("Authorization-Refresh");
    	Enumeration<String> headerNames = httpServletRequest.getHeaderNames();
        //获取获取的消息头名称，获取对应的值，并输出
        while(headerNames.hasMoreElements()){
            String nextElement = headerNames.nextElement();
            log.debug(nextElement+":"+httpServletRequest.getHeader(nextElement));
        }
    	log.debug("进入token验证过滤器");
    	log.debug("token:"+jwtoken);
    	log.debug("refresh-token:"+refresh_token);
        ArrayList<GrantedAuthority> authorities = new ArrayList<>();
        String sub = "";
        StringBuffer roles = new StringBuffer();
        httpServletResponse.setContentType("application/json;charset=utf-8");
        httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        String originHeader = httpServletRequest.getHeader("Origin");
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

            }else{
            	String url = httpServletRequest.getRequestURI();
            	boolean isPermit = false;
            	if(!isPermit){
            		//不带token时,抛出用户未登录的异常
                    log.debug("用户 未登录");
                    httpServletResponse.getOutputStream().write(new RestResult<>(ResultCode.UNLOGIN).toJson().getBytes());
                    return;
            	}
            }
        }catch(ExpiredJwtException e){
        	log.debug("用户令牌过期 过期令牌:"+jwtoken);
        	if(refresh_token!=null){
        		try{
	        		sub = tokenService.getValueFromToken(refresh_token, "sub").toString();
	        		User user = userService.findByUsername(sub);
	        		ArrayList<GrantedAuthority> authorities_spical = new ArrayList<>();
					UserDTO userDTO = userService.getRolesByUsername(user.getUsername());
					List<Role> roleDTOs = userDTO.getRoleList();
					roleDTOs.stream().forEach(role -> authorities_spical.add( new SimpleGrantedAuthority(role.getRoleName())));
					// for(Role userRole:userDTO.getRoles()){
	                // 	authorities_spical.add( new SimpleGrantedAuthority(userRole.getRoleName()));
	                // }
	                log.debug("refresh 重新查询权限 user:"+sub);
	    	        SecurityContextHolder.getContext()
	    	        	.setAuthentication(new UsernamePasswordAuthenticationToken(sub, null, authorities_spical));

        		}catch(ExpiredJwtException e_re){
	            	httpServletResponse.getOutputStream().write(new RestResult<>(ResultCode.REFRESH_TOKEN_EXPIRED).toJson().getBytes());
	                return;
	            }catch (Exception e_re) {
	            	log.debug("用户 refresh令牌不合法:"+refresh_token);
	            	log.debug("用户 refresh令牌不合法 exception:"+e.getMessage());
	            	httpServletResponse.getOutputStream().write(new RestResult<>(ResultCode.TOKEN_ILLEGAL).toJson().getBytes());
	            	return;
	            }
        	}else{
            	httpServletResponse.getOutputStream().write(new RestResult<>(ResultCode.TOKEN_EXPIRED).toJson().getBytes());
            	return;
        	}
        }catch (Exception e) {
        	log.debug("用户令牌不合法:"+jwtoken);
        	log.debug("用户令牌不合法 exception:"+e.getMessage());
        	httpServletResponse.getOutputStream().write(new RestResult<>(ResultCode.TOKEN_ILLEGAL).toJson().getBytes());
        	return;
        }
        filterChain.doFilter(httpServletRequest,httpServletResponse);
    }
}
