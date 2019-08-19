package com.example.janche.security.manager;

import com.example.janche.common.model.Constant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Iterator;


@Component("urlAccessDecisionManager")
public class UrlAccessDecisionManager implements AccessDecisionManager {

    /**
     * 当前激活的配置文件
     */
    @Value("${spring.profiles.active}")
    private String env;

    @Override
    public void decide(Authentication auth, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, AuthenticationException {

        // 开发环境不启用权限验证
         /*if (StringUtils.equals(env, "dev,errorcode") || true) {
             return;
         }*/

        //当前用户所具有的权限
        Collection<? extends GrantedAuthority> authorities = auth.getAuthorities();
        Iterator<ConfigAttribute> iterator = collection.iterator();
        while (iterator.hasNext()) {
            ConfigAttribute ca = iterator.next();
            //当前请求需要的权限
            String needRole = ca.getAttribute();
            if ("ROLE_LOGIN".equals(needRole)) {
                if (auth instanceof AnonymousAuthenticationToken) {
                    throw new BadCredentialsException("用户未登录");
                } else {
                    return;
                }
            }
            //当前用户所具有的权限
            for (GrantedAuthority authority : authorities) {
                if (authority.getAuthority().equals(needRole) ||
                        Constant.ROLE_ADMIN.equals(authority.getAuthority())) {
                    return;
                }
            }
        }
        throw new AccessDeniedException("权限不足!");
    }

    @Override
    public boolean supports(ConfigAttribute configAttribute) {
        return true;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
