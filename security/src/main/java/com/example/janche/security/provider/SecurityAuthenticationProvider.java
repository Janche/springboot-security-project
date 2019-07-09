package com.example.janche.security.provider;

import com.example.janche.common.utils.MD5Util;
import com.example.janche.security.authentication.SecurityUser;
import com.example.janche.security.utils.SecurityUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;


@Component("securityAuthenticationProvider")
@Slf4j
public class SecurityAuthenticationProvider implements AuthenticationProvider {


	@Autowired
	@Qualifier("securityUserService")
    UserDetailsService securityUserService;

	@Resource(name = "securityUtils")
	private SecurityUtils securityUtils;

	// @Resource(name = "passwordEncoder")
    // private PasswordEncoder passwordEncoder;

	@Override
	public Authentication authenticate(Authentication authentication ) throws AuthenticationException {
        ServletRequestAttributes sra = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = sra.getRequest();

        // 判断当前IP是否允许登录
        // Boolean flag = this.checkLoginIp(request);
        // if (!flag){
        //     throw new LockedException("IP或IP段已被禁用");
        // }

		System.out.println("*********************");
		// [1] token 中的用户名和密码都是用户输入的，不是数据库里的
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        //获取 username 和 password
		String userName = (String) authentication.getPrincipal();
        String password = (String) authentication.getCredentials();

        // [2] 使用用户名从数据库读取用户信息
        SecurityUser userDetails = (SecurityUser) securityUserService.loadUserByUsername(userName);

        // 判断账号是否被禁用
        if (null != userDetails && userDetails.getState() == 0){
            userDetails.setEnabled(false);
        }

     	// [3] 检查用户信息
        if(userDetails == null) {
            throw new UsernameNotFoundException(userName + " 用户不存在");
        } else if (!userDetails.isEnabled()){
            throw new DisabledException(userName + " 用户已被禁用，请联系管理员");
        } else if (!userDetails.isAccountNonExpired()) {
            throw new AccountExpiredException(userName + " 账号已过期");
        } else if (!userDetails.isAccountNonLocked()) {
            throw new LockedException(userName + " 账号已被锁定");
        } else if (!userDetails.isCredentialsNonExpired()) {
            throw new LockedException(userName + " 凭证已过期");
        }
        // [4] 根据不同的情况比对密码
        if (isOAuthUser(userDetails)) {
            // 通过 OAuth 登陆过来的，例如密码是调用 SecurityHelper.login() 前判断是 OAuth 合法登陆的，
            // 然后就查询数据库得到本地用户的密码，这里可以只需要和数据库里的使用等于比较就可以了，具体的仍然需要根据业务逻辑调整
        	log.info(userDetails.getUsername()+"通过oauth2登录");

        } else {
            // 数据库用户的密码，一般都是加密过的
            String encryptedPassword = userDetails.getPassword();
            // 用户输入的密码
            String inputPassword = (String) token.getCredentials();
            // 根据加密算法加密用户输入的密码，然后和数据库中保存的密码进行比较
           // if(!passwordEncoder.matches(inputPassword, encryptedPassword)) {
           //     throw new BadCredentialsException("用户名/密码无效");
           // }
            if(!password.equals(encryptedPassword) && !encryptedPassword.equals(MD5Util.encode(inputPassword))) {
                throw new BadCredentialsException(userName + " 输入账号或密码不正确");
            }
            // if(!password.equals(encryptedPassword)){
            // 	throw new BadCredentialsException("密码错误");
            // }

        }
        // [5] 成功登陆，把用户信息提交给 Spring Security
        // 把 userDetails 作为 principal 的好处是可以放自定义的 UserDetails，这样可以存储更多有用的信息，而不只是 username，
        // 默认只有 username，这里的密码使用数据库中保存的密码，而不是用户输入的明文密码，否则就暴露了密码的明文
        return new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());


	}

	@Override
	public boolean supports( Class<?> authentication ) {
		return authentication.equals(UsernamePasswordAuthenticationToken.class);
	}

    private boolean isOAuthUser(UserDetails userDetails) {
    	return false;
    }

    /**
     * 检测登录IP是否合法
     * @param request
     */
    // private Boolean checkLoginIp(HttpServletRequest request) {
    //     String loginIp = IPUtils.getIpAddr(request);
    //     log.debug("登录的IP：", loginIp);
    //
    //     List<SystemInfo> prohibitIps = this.getSystemIp("IP");
    //     for (SystemInfo info : prohibitIps) {
    //         if (loginIp.equals(info.getValue())){
    //             log.debug("被禁用的IP：", info.getValue());
    //             return false;
    //         }
    //     }
    //
    //     List<SystemInfo> ipSections = this.getSystemIp("IPSection");
    //     for (SystemInfo info : ipSections){
    //         if(IPUtils.ipIsValid(info.getValue(), loginIp)){
    //             log.debug("被禁用的IP段：", info.getValue());
    //             return false;
    //         }
    //     }
    //     return true;
    // }
    //
    // private List<SystemInfo> getSystemIp(String ip) {
    //     Example example = new Example(SystemInfo.class);
    //     example.and()
    //             .andEqualTo("type", ip)
    //             .andEqualTo("status", 1);
    //     return systemInfoMapper.selectByExample(example);
    // }
}
