package com.example.janche.security.metadatasource;

import com.example.janche.security.utils.SecurityUtils;
import com.example.janche.user.dao.RoleMapper;
import com.example.janche.user.domain.MenuRight;
import com.example.janche.user.dto.LoginUserDTO;
import com.example.janche.user.service.MenuRightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.util.Collection;
import java.util.List;

@Component("urlFilterInvocationSecurityMetadataSource")
@Slf4j
public class UrlFilterInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
    @Autowired
    private MenuRightService menuService;

    @Resource
    private RoleMapper roleMapper;

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
        String requesMethod =  ((FilterInvocation) o).getHttpRequest().getMethod().toUpperCase();
        if("OPTIONS".equals(requesMethod)) {
            return null;
        }
        LoginUserDTO loginUser = SecurityUtils.getCurrentUser();
        if(ObjectUtils.isEmpty(loginUser)){
            return SecurityConfig.createList("ROLE_ANONYMOUS");
        }
        List<MenuRight> allMenu = loginUser.getMenus();
        if(requestUrl.indexOf("?") > 0) {
            requestUrl = requestUrl.substring(0, requestUrl.indexOf("?"));
        }
        // List<MenuRight> allMenu = menuService.findAllMenuRightByUrl(requestUrl,);
         for (MenuRight menu : allMenu) {
              String httpMethod = menu.getMethod();
              if (antPathMatcher.match(menu.getUrl(), requestUrl) &&
                      (httpMethod == null || requesMethod.equals(httpMethod.toUpperCase()))) {
                  //根据menu的url查询对应角色
                  return null;
              }
         }
       //  //获取请求地址
       //  String requestUrl = ((FilterInvocation) o).getRequestUrl();
       //  String requesMethod =  ((FilterInvocation) o).getHttpRequest().getMethod().toUpperCase();
       //  //根据url查询menuright节点
       //  List<MenuRight> allMenu = menuService.findAllMenuRight();
       // // List<MenuRight> allMenu = menuService.findAllMenuRightByUrl(requestUrl,);
       //  for (MenuRight menu : allMenu) {
       //       String httpMethod = menu.getMethod().toUpperCase();
       //       if (antPathMatcher.match(menu.getUrl(), requestUrl) &&
       //               (httpMethod == null || requesMethod.equals(menu.getMethod().toUpperCase()))) {
       //           //根据menu的url查询对应角色
       //           Set<Role> roleSet = roleMapper.getRolesByUrl(menu.getUrl());
       //           String[] roles = new String[roleSet.size() + 1];
       //           int i = 0;
       //           for (Role role: roleSet) {
       //               roles[i] = role.getName();
       //               i++;
       //           }
       //           roles[roles.length - 1] = "ROLE_超级管理员";
       //           log.debug("资源:" + menu.getUrl() + ",请求方式:" + menu.getMethod() + ",需要角色权限:" + JSON.toJSONString(roles));
       //           return SecurityConfig.createList(roles);
       //       }
       //  }
        //没有匹配上的资源，都是ADMIN访问
        return SecurityConfig.createList("ROLE_超级管理员");
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
