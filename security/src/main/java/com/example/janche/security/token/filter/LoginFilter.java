package com.example.janche.security.token.filter;

import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.security.token.service.TokenService;
import com.example.janche.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 登录过滤器
 *
 * @author daiyp
 * @date 2018/9/27
 */
@Slf4j
public class LoginFilter extends UsernamePasswordAuthenticationFilter {

	@Autowired
	private TokenService tokenService;

	@Value("${janche.auth.refresh_token_expirationminutes}")
	private long refresh_token_expirationminutes;

    @Value("${janche.auth.refresh_token_expirationminutes}")
    private long token_expirationminutes;
    public LoginFilter(AuthenticationManager authManager) {
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException {
        log.debug("进入登录过滤器");
    	User creds =new User();
    	String username = httpServletRequest.getParameter("username");
    	String password = httpServletRequest.getParameter("password");
        creds.setUsername(username);
        creds.setPassword(password);
        log.debug("用户:"+username+" 进行登录");
        // 返回一个验证令牌
        return getAuthenticationManager().authenticate(
                new UsernamePasswordAuthenticationToken(
                        creds.getUsername(),
                        creds.getPassword()
                )
        );
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);

        Map<String,Object> param = new HashMap<String,Object>();
        List<String> role_list=new ArrayList<String>();
        for(GrantedAuthority auth: authResult.getAuthorities()){
            role_list.add(auth.getAuthority());
        }
        param.put("roles",role_list);

        String refresh_token = tokenService.sign(authResult.getName(), new HashMap<String,Object>() ,refresh_token_expirationminutes);
        String token = tokenService.sign(authResult.getName(), param ,token_expirationminutes);

        Map tokenMap = new HashMap();
        tokenMap.put("refresh_token", refresh_token);
        tokenMap.put("token", token);
        RestResult<Map> result= new RestResult<>(1, "", tokenMap);
        response.getOutputStream().write(result.toJson().getBytes());
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(new RestResult<>(ResultCode.LOGIN_ERROR).toJson().getBytes());
    }
}
