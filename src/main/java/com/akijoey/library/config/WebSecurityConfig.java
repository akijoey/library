package com.akijoey.library.config;

import com.akijoey.library.entity.Menu;
import com.akijoey.library.entity.Role;
import com.akijoey.library.response.ResponseBody;
import com.akijoey.library.service.MenuService;
import com.akijoey.library.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.config.annotation.ObjectPostProcessor;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.util.AntPathMatcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    MenuService menuService;

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers(
            "/css/**",
            "/js/**",
            "/login",
            "/img/**",
            "/fonts/**",
            "/favicon.ico",
            "/verifyCode"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
//        .withObjectPostProcessor(new ObjectPostProcessor<FilterSecurityInterceptor>() {
//            @Override
//            public <O extends FilterSecurityInterceptor> O postProcess(O o) {
//                o.setAccessDecisionManager(new AccessDecisionManager() {
//                    @Override
//                    public void decide(Authentication authentication, Object o, Collection<ConfigAttribute> collection) throws AccessDeniedException, InsufficientAuthenticationException {
//                        for (ConfigAttribute configAttribute : collection) {
//                            String needRole = configAttribute.getAttribute();
//                            if ("ROLE_LOGIN".equals(needRole)) {
//                                if (authentication instanceof AnonymousAuthenticationToken) {
//                                    throw new AccessDeniedException("Places Login");
//                                }else {
//                                    return;
//                                }
//                            }
//                            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//                            for (GrantedAuthority authority : authorities) {
//                                if (authority.getAuthority().equals(needRole)) {
//                                    return;
//                                }
//                            }
//                        }
//                        throw new AccessDeniedException("Permission Denied");
//                    }
//
//                    @Override
//                    public boolean supports(ConfigAttribute configAttribute) {
//                        return true;
//                    }
//
//                    @Override
//                    public boolean supports(Class<?> aClass) {
//                        return true;
//                    }
//                });
//                o.setSecurityMetadataSource(new FilterInvocationSecurityMetadataSource() {
//                    @Override
//                    public Collection<ConfigAttribute> getAttributes(Object o) throws IllegalArgumentException {
//                        String requestUrl = ((FilterInvocation) o).getRequestUrl();
//                        List<Menu> menus = menuService.findAll();
//                        AntPathMatcher antPathMatcher = new AntPathMatcher();
//                        for (Menu menu : menus) {
//                            if (antPathMatcher.match(menu.getUrl(), requestUrl)) {
//                                List<Role> roles = menu.getRoles();
//                                String[] str = new String[roles.size()];
//                                for (int i = 0; i < roles.size(); i++) {
//                                    str[i] = roles.get(i).getName();
//                                }
//                                return SecurityConfig.createList(str);
//                            }
//                        }
//                        return SecurityConfig.createList("ROLE_LOGIN");
//                    }
//
//                    @Override
//                    public Collection<ConfigAttribute> getAllConfigAttributes() {
//                        return null;
//                    }
//
//                    @Override
//                    public boolean supports(Class<?> aClass) {
//                        return true;
//                    }
//                });
//                return o;
//            }
//        })

        // 登录功能
        .and()
        .formLogin()
        .loginPage("/login").loginProcessingUrl("/api/login")
        .usernameParameter("username").passwordParameter("password")
        .successHandler(new AuthenticationSuccessHandler() {
            @Override
            public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                ResponseBody body = new ResponseBody(200, "Login Success", null);
                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            }
        })
        .failureHandler(new AuthenticationFailureHandler() {
            @Override
            public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(401);
                ResponseBody body = new ResponseBody(401, "Login Failure", null);
                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            }
        })
        .permitAll()

        // 注销功能
        .and()
        .logout()
        .logoutUrl("/api/logout")
        .logoutSuccessHandler(new LogoutSuccessHandler() {
            @Override
            public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                ResponseBody body = new ResponseBody(200, "Logout Success", null);
                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            }
        })
        .permitAll()

        // 未认证时防止重定向
        .and()
        .csrf().disable().exceptionHandling()
        .authenticationEntryPoint(new AuthenticationEntryPoint() {
            @Override
            public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(401);
                ResponseBody body = new ResponseBody(401, "Places Login", null);
                if (e instanceof InsufficientAuthenticationException) {
                    body.setMessage("request error");
                }
                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            }
        })

        // 权限不足
        .and()
        .csrf().disable().exceptionHandling()
        .accessDeniedHandler(new AccessDeniedHandler() {
            @Override
            public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
                response.setContentType("application/json;charset=utf-8");
                response.setStatus(403);
                ResponseBody body = new ResponseBody(403, "Permission Denied", null);
                response.getWriter().write(new ObjectMapper().writeValueAsString(body));
            }
        });

    }

}
