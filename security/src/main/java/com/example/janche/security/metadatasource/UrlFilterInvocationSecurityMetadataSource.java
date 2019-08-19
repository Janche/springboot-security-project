package com.example.janche.security.metadatasource;

import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.dao.MenuRightMapper;
import com.example.janche.user.domain.Role;
import com.example.janche.user.dto.MenuDTO;
import com.example.janche.user.service.MenuRightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Component("urlFilterInvocationSecurityMetadataSource")
@Slf4j
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Resource
    private MenuRightMapper menuRightMapper;

    /**
     * 当前激活的配置文件
     */
    @Value("${spring.profiles.active}")
    private String env;

    AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Override
    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {

        // 开发环境不启用权限验证
        // if (env.contains("dev")) {
        //     return null;
        // }

        String requestUrl = ((FilterInvocation) o).getRequestUrl();
        if (requestUrl.contains("?")) {
            requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
        }
        String requesMethod =  ((FilterInvocation) o).getHttpRequest().getMethod().toUpperCase();
        if("OPTIONS".equals(requesMethod)) {
            return null;
        }

        // 获取用户ID
        Long userId = SecurityUtils.getLoginUserId();
        if (null != userId){
            // 获取系统所有权限
            List<MenuDTO> menuDTOS = menuRightMapper.getAllMenus();
            for (MenuDTO menu : menuDTOS) {
                if (antPathMatcher.match(menu.getUrl(), requestUrl)
                        && menu.getRoles().size()>0) {

                    List<Role> roles = menu.getRoles();
                    int size = roles.size();
                    String[] values = new String[size];
                    for (int i = 0; i < size; i++) {
                        values[i] = "ROLE_" + roles.get(i).getName();
                    }
                    return SecurityConfig.createList(values);
                }
            }
        }
        //没有匹配上的资源，都是登录访问
        return SecurityConfig.createList("ROLE_LOGIN");
    }

    @Override
    public Collection<ConfigAttribute> getAllConfigAttributes() {
        return null;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return FilterInvocation.class.isAssignableFrom(aClass);
    }
}
