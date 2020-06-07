package com.akijoey.library.config;

import com.akijoey.library.util.ResultUtil;
import com.akijoey.library.util.TokenUtil;
import com.akijoey.library.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.*;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    UserService userService;

    @Autowired
    TokenUtil tokenUtil;

    @Autowired
    ResultUtil resultUtil;

    @Bean
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Bean
    UsernamePasswordAuthenticationFilter authenticationFilter() throws Exception {
        UsernamePasswordAuthenticationFilter authenticationFilter = new UsernamePasswordAuthenticationFilter() {
            @Override
            public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
                if (request.getContentType().equals(MediaType.APPLICATION_JSON_VALUE) || request.getContentType().equals(MediaType.APPLICATION_JSON_UTF8_VALUE)) {
                    UsernamePasswordAuthenticationToken authRequest = null;
                    try {
                        Map<String, String> data = new ObjectMapper().readValue(request.getInputStream(), Map.class);
                        authRequest = new UsernamePasswordAuthenticationToken(data.get("username"), data.get("password"));
                    } catch (IOException e) {
                        e.printStackTrace();
                        authRequest = new UsernamePasswordAuthenticationToken("", "");
                    } finally {
                        setDetails(request, authRequest);
                        return this.getAuthenticationManager().authenticate(authRequest);
                    }
                } else {
                    return super.attemptAuthentication(request, response);
                }
            }
        };
        authenticationFilter.setAuthenticationSuccessHandler((request, response, authentication) -> {
            response.setContentType("application/json;charset=utf-8");
            String username = SecurityContextHolder.getContext().getAuthentication().getName();
            String token = tokenUtil.generateToken(username);
            Map<String, Object> result = resultUtil.successResult("Login Success", Map.of("token", token));
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        });
        authenticationFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            Map<String, Object> result = resultUtil.customResult(401, "Login Failure");
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        });
        authenticationFilter.setFilterProcessesUrl("/api/user/login");
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Bean
    AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            Map<String, Object> result = resultUtil.customResult(401, "Authentication Failure");
            if (exception instanceof CredentialsExpiredException) {
                result.put("status", 499);
                result.put("message", "Token Expired");
            } else if (exception instanceof AccountExpiredException) {
                result.put("status", 498);
                result.put("message", "Account Expired");
            } else if (exception instanceof BadCredentialsException) {
                result.put("status", 497);
                result.put("message", "Illegal Token");
            } else if (exception instanceof UsernameNotFoundException) {
                result.put("status", 496);
                result.put("message", "Username Not Found");
            } else if (exception instanceof AuthenticationCredentialsNotFoundException) {
                result.put("status", 495);
                result.put("message", "Token Not Found");
            }
            response.getWriter().write(new ObjectMapper().writeValueAsString(result));
        };
    }

    @Bean
    BasicAuthenticationFilter authorizationFilter() throws Exception {
        return new BasicAuthenticationFilter(authenticationManager()) {

            @Autowired
            AuthenticationEntryPoint authenticationEntryPoint;

            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
                if (authorization != null) {
                    try {
                        authenticationTokenHandler(request, authorization);
                        super.doFilterInternal(request, response, chain);
                    } catch (AuthenticationException exception) {
                        authenticationEntryPoint.commence(request, response, exception);
                    }
                } else {
                    chain.doFilter(request, response);
                }
            }

            private void authenticationTokenHandler(HttpServletRequest request, String authorization) throws AuthenticationException, IOException {
                String token = authorization.replace("Bearer ", "");
                if (token.length() > 0) {
                    Map<String, String> claims = tokenUtil.parseToken(token);
                    String username = tokenUtil.getSubject(claims);
                    long expiration = tokenUtil.getExpiration(claims);
                    if (username != null) {
                        UserDetails userDetails = userService.loadUserByUsername(username);
                        if (userDetails != null) {
                            if (!tokenUtil.isExpired(username, expiration)) {
                                if (tokenUtil.isValid(username, expiration)) {
                                    UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                                    authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                                    SecurityContextHolder.getContext().setAuthentication(authRequest);
                                } else {
                                    throw new AccountExpiredException("Account Expired");
                                }
                            } else {
                                throw new CredentialsExpiredException("Token Expired");
                            }
                        } else {
                            throw new UsernameNotFoundException("Username Not Found");
                        }
                    } else {
                        throw new BadCredentialsException("Bad Token");
                    }
                } else {
                    throw new AuthenticationCredentialsNotFoundException("Token Not Found");
                }
            }
        };
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userService).passwordEncoder(passwordEncoder());
    }

    @Override
    public void configure(WebSecurity web) {
        web.ignoring().antMatchers(
                "/css/**", "/js/**",
                "/img/**", "/fonts/**", "/favicon.ico",
                "/", "/api", "/login"
        );
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .antMatchers("/api/user/register").permitAll()
                .antMatchers("/api/**").authenticated()

                // authentication
                .and()
                .addFilterAt(authenticationFilter(), UsernamePasswordAuthenticationFilter.class)
                .addFilterAfter(authorizationFilter(), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                // logout
                .and()
                .logout()
                .logoutUrl("/api/user/logout")
                .logoutSuccessHandler((request, response, authentication) -> {
                    response.setContentType("application/json;charset=utf-8");
                    String authorization = request.getHeader(HttpHeaders.AUTHORIZATION);
                    if (authorization != null) {
                        String token = authorization.replace("Bearer ", "");
                        tokenUtil.removeToken(tokenUtil.getSubject(token));
                    }
                    Map<String, Object> result = resultUtil.successResult("Logout Success");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                })

                .and().exceptionHandling()
                // authentication failure
                .authenticationEntryPoint(authenticationEntryPoint())
                // permission denied
                .accessDeniedHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    Map<String, Object> result = resultUtil.customResult(403, "Permission Denied");
                    response.getWriter().write(new ObjectMapper().writeValueAsString(result));
                });

    }

}
