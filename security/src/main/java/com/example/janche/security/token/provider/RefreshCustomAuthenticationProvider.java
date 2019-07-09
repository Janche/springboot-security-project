package com.example.janche.security.token.provider;

import com.example.janche.security.token.authentication.JWTAuthenticationToken;
import com.example.janche.security.token.exception.TokenExpiredException;
import com.example.janche.security.token.exception.TokenMalformationException;
import com.example.janche.security.token.service.TokenService;
import com.example.janche.user.domain.Role;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.UserDTO;
import com.example.janche.user.service.UserService;
import io.jsonwebtoken.ExpiredJwtException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

/**
 * @author daiyp
 * @date 2018/9/27
 */
public class RefreshCustomAuthenticationProvider implements AuthenticationProvider {

	private  static  final Logger logger = LoggerFactory.getLogger(RefreshCustomAuthenticationProvider.class);

	@Autowired
    private UserService userService;

	@Autowired
    private TokenService tokenService;

    @Value("${janche.auth.refresh_token_expirationminutes}")
    private Long refresh_token_expirationminutes;

    @Value("${janche.auth.token_expirationminutes}")
    private Long token_expirationminutes;



    public RefreshCustomAuthenticationProvider() {
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
    	logger.debug("进入刷新令牌验证器");
    	String token = ((JWTAuthenticationToken)authentication).getRefresh_token();
        try{
            String name = tokenService.getValueFromToken(token, "sub").toString();
        	User user = userService.findByUsername(name);
             // 认证逻辑
             if (user!=null) {
             	logger.debug("用户:"+name+" 令牌验证通过 生成新令牌");
                 // 这里设置权限和角色
                 ArrayList<GrantedAuthority> authorities = new ArrayList<>();
                 // 获取用户的所有角色
                 UserDTO userDTO = userService.getRolesByUsername(name);
                 List<Role> roleDTOs = userDTO.getRoleList();
                 roleDTOs.stream().forEach(role ->  authorities.add( new SimpleGrantedAuthority(role.getRoleName())));

                 // for(Role role:user.getRoles()){
                 //     authorities.add( new SimpleGrantedAuthority(role.getRoleName()));
                 // }
                 // 生成令牌
                 Authentication auth = new JWTAuthenticationToken(tokenService.sign(name,  new HashMap<String,Object>(), token_expirationminutes),authorities);
                 return auth;
             }else {
             	logger.debug("用户:"+name+" 令牌错误 错误令牌:"+token);
                throw new BadCredentialsException("令牌错误~");
             }
        }catch(ExpiredJwtException e){
        	logger.debug("用户 refresh令牌过期 过期令牌:"+token);
        	throw new TokenExpiredException("令牌过期");
        }catch(Exception e){
        	logger.debug("用户 refresh令牌不合法:"+token);
        	throw new TokenMalformationException("令牌不合法");
        }
    }

    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(JWTAuthenticationToken.class);
    }
}
