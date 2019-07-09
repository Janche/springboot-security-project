package com.example.janche.security.filter;

import com.example.janche.common.restResult.RestResult;
import com.example.janche.common.restResult.ResultCode;
import com.example.janche.security.token.service.TokenService;
import com.example.janche.user.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
 * 登录处理
 * @author daiyp
 * @date 2018-9-13
 */
@Slf4j
public class JwtLoginFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public JwtLoginFilter(AuthenticationManager authenticationManager ){
        this.authenticationManager = authenticationManager;
    }
    @Autowired
    private  TokenService tokenService;
    @Override
    public Authentication attemptAuthentication (HttpServletRequest req,
                                                HttpServletResponse res) throws AuthenticationException {
           String username  = req.getParameter("username");
           String password  = req.getParameter("password");
           User user =  new User();
           user.setPassword(password);
           user.setUsername(username);
           log.info("用戶{}登录", username);
           ArrayList<GrantedAuthority> authorities = new ArrayList<>();
           Authentication auth = new UsernamePasswordAuthenticationToken(username, password, authorities);
           return auth;
    }

    // 用户成功登录后，这个方法会被调用，我们在这个方法里生成token
    @Override
    protected void successfulAuthentication(HttpServletRequest req,
                                            HttpServletResponse res,
                                            FilterChain chain,
                                            Authentication auth) throws IOException, ServletException {

        res.setContentType("application/json;charset=utf-8");
        res.setStatus(HttpServletResponse.SC_OK);

        Map<String,Object> param = new HashMap<String,Object>();
        List<String> role_list=new ArrayList<String>();
        for(GrantedAuthority authority: auth.getAuthorities()){
            role_list.add(authority.getAuthority());
        }
        param.put("roles",role_list);
        String token = tokenService.sign(auth.getName(), param ,2);
        RestResult<String> result= new RestResult<>(1, "", token);
        res.setHeader("Authentication-Token", token);
        res.getOutputStream().write(result.toJson().getBytes());
    }
    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getOutputStream().write(new RestResult<>(ResultCode.LOGIN_ERROR).toJson().getBytes());
    }

}
