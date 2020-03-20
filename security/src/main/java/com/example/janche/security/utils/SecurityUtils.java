package com.example.janche.security.utils;

import com.example.janche.common.util.IPUtils;
import com.example.janche.log.domain.SysLog;
import com.example.janche.security.authentication.SecurityUser;
import com.example.janche.user.dto.LoginUserDTO;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;


@Component
public class SecurityUtils {

    /**
     * 判断当前用户是否已经登陆
     * @return 登陆状态返回 true, 否则返回 false
     */
    public static boolean isLogin() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return !"anonymousUser".equals(username);
    }
    /**
     * 取得登陆用户的 ID, 如果没有登陆则返回 -1
     * @return 登陆用户的 ID
     */
    public static Long getLoginUserId() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (SecurityUtils.isLogin()) {
            SecurityUser userDetails = (SecurityUser) principle;
            return userDetails.getId();
        }
        return -1L;
    }
    /**
     *
     * 功能：返回当前用户<br/>
     * @return
     * @exception
     *
     */
    public static LoginUserDTO getCurrentUser() {
		if (SecurityContextHolder.getContext().getAuthentication() != null) {
			if ((SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof LoginUserDTO)) {
				return (LoginUserDTO) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			}
		}
		return null;
	}

    /**
     * 构建Security操作的日志
     * @param request
     */
    public static SysLog buildLog(HttpServletRequest request, Authentication authentication) {
        LoginUserDTO user = (LoginUserDTO) authentication.getPrincipal();
        SysLog sysLog = new SysLog();
        sysLog.setLogUser(user.getId());
        sysLog.setCreateTime(new Date());
        sysLog.setLogIp(IPUtils.getIpAddr(request));
        sysLog.setLogMethod(request.getMethod());
        return sysLog;
    }
}
