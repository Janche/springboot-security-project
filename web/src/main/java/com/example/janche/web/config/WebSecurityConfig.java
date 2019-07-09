package com.example.janche.web.config;

import com.example.janche.security.metadatasource.UrlFilterInvocationSecurityMetadataSource;
import com.example.janche.user.dao.RoleMapper;
import com.example.janche.user.service.MenuRightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDecisionVoter;
import org.springframework.security.access.vote.AbstractAccessDecisionManager;
import org.springframework.security.access.vote.AffirmativeBased;
import org.springframework.security.access.vote.AuthenticatedVoter;
import org.springframework.security.access.vote.RoleVoter;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.security.core.session.SessionRegistryImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.expression.WebExpressionVoter;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.session.HttpSessionEventPublisher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
@Configuration
@EnableWebSecurity
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    @Qualifier("securityAuthenticationProvider")
    private AuthenticationProvider securityAuthenticationProvider;

    @Autowired
    @Qualifier("userLoginSuccessHandler")
    private AuthenticationSuccessHandler userLoginSuccessHandler;

    @Autowired
    @Qualifier("securityAuthenticationFailureHandler")
    private AuthenticationFailureHandler securityAuthenticationFailureHandler;

    @Autowired
    @Qualifier("securityLogoutSuccessHandler")
    private LogoutSuccessHandler securityLogoutSuccessHandler;

    @Autowired
    @Qualifier("securityAccessDeniedHandler")
    private AccessDeniedHandler securityAccessDeniedHandler;

    @Autowired
    @Qualifier("securityAuthenticationEntryPoint")
    private AuthenticationEntryPoint securityAuthenticationEntryPoint;

    @Autowired
    private MenuRightService menuRightService;

    @Resource
    private RoleMapper roleMapper;

    @Autowired
    @Qualifier("urlFilterInvocationSecurityMetadataSource")
    UrlFilterInvocationSecurityMetadataSource urlFilterInvocationSecurityMetadataSource;

    @Autowired
    @Qualifier("urlAccessDecisionManager")
    AccessDecisionManager urlAccessDecisionManager;




    /**
     * 访问静态资源
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
                "/css/**",
                "/js/**",
                "/images/**",
                "/fonts/**",
                "/favicon.ico",
                "/janche/**",
                "/resources/**","/error","/status/*", "/swagger-ui.html", "/v2/**", "/webjars/**", "/swagger-resources/**");
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.authenticationProvider(securityAuthenticationProvider);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .withObjectPostProcessor(urlObjectPostProcessor())
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .permitAll()
                .failureHandler(securityAuthenticationFailureHandler)
                .successHandler(userLoginSuccessHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(securityAuthenticationEntryPoint)
                .and()
                .logout()
                .deleteCookies("remove")
                .invalidateHttpSession(false)
                .logoutUrl("/logout")
                .logoutSuccessHandler(securityLogoutSuccessHandler)
                .permitAll()
                .and()
                .csrf().requireCsrfProtectionMatcher(new AntPathRequestMatcher("/oauth/authorize"))
                .disable();

        http
                .sessionManagement()
                // 无效session跳转
                .invalidSessionUrl("/login")
                .maximumSessions(1)
                // session过期跳转
                .expiredUrl("/login")
                .sessionRegistry(sessionRegistry());
    }

    /**
     * 解决session失效后 sessionRegistry中session没有同步失效的问题
     * @return
     */
    @Bean
    public HttpSessionEventPublisher httpSessionEventPublisher() {
        return new HttpSessionEventPublisher();
    }

    @Bean
    public SessionRegistry sessionRegistry() {
        return new SessionRegistryImpl();
    }

    public ObjectPostProcessor urlObjectPostProcessor() {
        return new ObjectPostProcessor<FilterSecurityInterceptor>() {
            @Override
            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
                o.setSecurityMetadataSource(urlFilterInvocationSecurityMetadataSource);
                o.setAccessDecisionManager(urlAccessDecisionManager);
                return o;
            }
        };
    }
    /* *//**
     * 静态配置所有权限
     *//*
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                .authorizeRequests()
                 // .antMatchers("/**").permitAll();//测试开放所有权限
             .antMatchers("/error","/status/*", "/swagger-ui.html", "/v2/**", "/webjars/**", "/swagger-resources/**").permitAll();//开放权限，可以直接访问
               *//* .addFilter(new JwtLoginFilter(authenticationManager()))
                .addFilter(new AuthenticationFilter(authenticationManager()));*//*
        //权限控制需要按照顺序进行
        List<MenuRight> menuRights = menuRightService.findAllMenuRight();
        if(menuRights != null) {
            for (MenuRight rights : menuRights) {
                if (!StringUtils.isEmpty(rights.getUrl())) {
                    // 根据权限获取相应的角色
                    Set<Role> roleSet = roleMapper.getRoleByMenuId(rights.getId());
                    String[] roles = new String[roleSet.size() + 1];
                    int i = 0;
                    for (Role role: roleSet) {
                        roles[i] = role.getName();
                        i++;
                    }
                    roles[roles.length - 1] = "ROLE_超级管理员";
                    log.debug("资源:" + rights.getUrl() + "，请求方式:" + rights.getMethod() + ",需要角色权限:" + JSON.toJSONString(roles));
                    HttpMethod method = getMethod(rights.getMethod().toUpperCase());
                    if(method == null) {
                        http.authorizeRequests().mvcMatchers(rights.getUrl()).hasAnyAuthority(roles);
                    } else {
                        http.authorizeRequests().mvcMatchers(method, rights.getUrl()).hasAnyAuthority(roles);
                    }
                }
            }
        }
        // 其他权限必须要admin才能访问 暂时不使用
        http.authorizeRequests().antMatchers("/**").hasAnyAuthority("ROLE_超级管理员");
        // 添加filter，所有权限被拦截
        http.formLogin()
                .loginPage("/login")//指定登录页是”/login”
                .successHandler(userLoginSuccessHandler) //登录成功后可使用loginSuccessHandler()存储用户信息，可选。
                .failureHandler(securityAuthenticationFailureHandler)
                .and()
                .logout().deleteCookies("remove")
                .invalidateHttpSession(false)
                .logoutUrl("/logout")
                .logoutSuccessHandler(securityLogoutSuccessHandler)
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(securityAuthenticationEntryPoint)
                .accessDeniedHandler(securityAccessDeniedHandler);
        http.authorizeRequests()
                .accessDecisionManager(accessDecisionManager())
                .and()
                .headers()
                .frameOptions()
                .sameOrigin();
               // .and()
               // .rememberMe()//登录后记住用户，下次自动登录,数据库中必须存在名为persistent_logins的表
    }
*/
    /**
     * 自定义投票策略-投票器
     */
    private AbstractAccessDecisionManager accessDecisionManager() {
        List<AccessDecisionVoter<? extends Object>> decisionVoters = new ArrayList();
        decisionVoters.add(new AuthenticatedVoter());
        decisionVoters.add(new WebExpressionVoter());
        RoleVoter AuthVoter = new RoleVoter();
        //特殊权限投票器,修改前缀为ROLE_
        AuthVoter.setRolePrefix("ROLE_");
        decisionVoters.add(AuthVoter);
        AbstractAccessDecisionManager accessDecisionManager = new AffirmativeBased(decisionVoters);
        return accessDecisionManager;
    }


    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() {
        AuthenticationManager authenticationManager = null;
        try {
            authenticationManager = super.authenticationManagerBean();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return authenticationManager;
    }

    public static class MyFilterSecurityInterceptor extends FilterSecurityInterceptor {

        public MyFilterSecurityInterceptor(FilterInvocationSecurityMetadataSource securityMetadataSource, AccessDecisionManager accessDecisionManager, AuthenticationManager authenticationManager) {
            this.setSecurityMetadataSource(securityMetadataSource);
            this.setAccessDecisionManager(accessDecisionManager);
            this.setAuthenticationManager(authenticationManager);

        }
    }

    private HttpMethod getMethod(String method) {
        switch (method) {
            case "GET":
                return HttpMethod.GET;
            case "POST":
                return HttpMethod.POST;
            case "PUT":
                return HttpMethod.PUT;
            case "DELETE":
                return HttpMethod.DELETE;
            default:
                return null;
        }
    }
}
