package com.example.janche.security.token.filter;

import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.security.token.service.TokenService;
import com.example.janche.security.token.exception.TokenExpiredException;
import com.example.janche.security.token.authentication.JWTAuthenticationToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

/**
 * 刷新令牌过滤器
 * @author daiyp
 * @date 2018-9-27
 */
@Slf4j
public class RefreshLoginFilter extends AbstractAuthenticationProcessingFilter {

    @Value("${janche.auth.refresh_token_expirationminutes}")
    private Long refresh_token_expirationminutes;

    @Value("${janche.auth.token_expirationminutes}")
    private Long token_expirationminutes;

	@Autowired
	private TokenService tokenService;

    public RefreshLoginFilter(String url, AuthenticationManager authManager) {
        super(new AntPathRequestMatcher(url));
        setAuthenticationManager(authManager);
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
    	logger.debug("进入刷新令牌过滤器");
    	String refresh_token = httpServletRequest.getHeader("Authorization-Refresh");
        // 返回一个验证令牌
        return getAuthenticationManager().authenticate(
                new JWTAuthenticationToken(refresh_token)
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

        String refresh_token = ((JWTAuthenticationToken)authResult).getRefresh_token();
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
        if(failed instanceof TokenExpiredException){
        	response.getOutputStream().write(new RestResult<>(ResultCode.REFRESH_TOKEN_EXPIRED).toJson().getBytes());
        }else{
            response.getOutputStream().write(new RestResult<>(ResultCode.TOKEN_ILLEGAL).toJson().getBytes());
        }
    }
}
