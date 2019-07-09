package com.example.janche.security.token.provider;

import com.example.janche.user.domain.Role;
import com.example.janche.user.domain.User;
import com.example.janche.user.dto.UserDTO;
import com.example.janche.user.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * CustomAuthenticationProvider
 *
 * @author daiyp
 * @date 2018/9/27
 */
public class CustomAuthenticationProvider implements AuthenticationProvider {

	private  static  final Logger logger = LoggerFactory.getLogger(CustomAuthenticationProvider.class);

	@Autowired
    private UserService userService;

    public CustomAuthenticationProvider() {
    }
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("进入登录验证器");
    	// 获取认证的用户名 & 密码authenticationManager
        String name = authentication.getName();
        Object password = authentication.getCredentials();
        User user = userService.findByUsername(name);
        // 认证逻辑
        if (password!=null && user!=null && name.equals(user.getUsername()) && password.toString().equals(user.getPassword())) {
        	logger.debug("用户:"+name+" 账号密码验证成功");
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
            Authentication auth = new UsernamePasswordAuthenticationToken(name, password, authorities);
            return auth;
        }else {
        	logger.debug("用户:"+name+" 账号密码验证错误");
            throw new BadCredentialsException("密码错误~");
        }
    }

    // 是否可以提供输入类型的认证服务
    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
