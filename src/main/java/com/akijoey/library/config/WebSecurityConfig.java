package com.akijoey.library.config;

import com.akijoey.library.util.TokenUtil;
import com.akijoey.library.util.ResponseBody;
import com.akijoey.library.service.MenuService;
import com.akijoey.library.service.UserService;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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
import org.springframework.web.filter.OncePerRequestFilter;

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
    MenuService menuService;

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
            String token = TokenUtil.generateToken(username);
            ResponseBody body = new ResponseBody(200, "Login Success", Map.of("token", token));
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        });
        authenticationFilter.setAuthenticationFailureHandler((request, response, exception) -> {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(401);
            ResponseBody body = new ResponseBody(401, "Login Failure", null);
            response.getWriter().write(new ObjectMapper().writeValueAsString(body));
        });
        authenticationFilter.setFilterProcessesUrl("/api/user/login");
        authenticationFilter.setAuthenticationManager(authenticationManager());
        return authenticationFilter;
    }

    @Bean
    OncePerRequestFilter authorizationFilter() {
        return new OncePerRequestFilter() {
            @Override
            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
                String token = request.getHeader("Authorization").replace("Bearer ", "");
                if (token != null) {
                    String username = TokenUtil.getSubject(token);
                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                        UserDetails userDetails = userService.loadUserByUsername(username);
                        if (userDetails != null) {
                            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
                            authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                            SecurityContextHolder.getContext().setAuthentication(authRequest);

                            System.out.println(SecurityContextHolder.getContext());
                        }
                    }
                }
                chain.doFilter(request, response);
            }

//            @Autowired
//            AuthenticationEntryPoint authenticationEntryPoint;
//
//            @Override
//            protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
//                if (SecurityContextHolder.getContext().getAuthentication() == null) {
//                    String token = request.getHeader("Authorization").replace("Bearer ", "");
//                    try {
//                        authenticationTokenHandler(request, token);
//                    } catch (AuthenticationException exception) {
//                        authenticationEntryPoint.commence(request, response, exception);
//                    }
//                }
//                chain.doFilter(request, response);
//            }
//
//            private void authenticationTokenHandler(HttpServletRequest request, String token) throws AuthenticationException, IOException {
//                if (token != null && token.length() > 0) {
//                    String username = JsonWebToken.getSubject(token);
//                    if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
//                        UserDetails userDetails = userService.loadUserByUsername(username);
//                        if (userDetails != null) {
//                            // get redis
//                            if (true) {
//
//                            }
//                            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username, null, userDetails.getAuthorities());
//                            authRequest.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
//                            SecurityContextHolder.getContext().setAuthentication(authRequest);
//                        } else {
//                            throw new UsernameNotFoundException("Username Not Found");
//                        }
//                    } else {
//                        throw new BadCredentialsException("Bad Token");
//                    }
//                } else {
//                    throw new AuthenticationCredentialsNotFoundException("Token Not Found");
//                }
//            }
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
        web.ignoring().antMatchers("/css/**", "/js/**", "/index.html", "/img/**", "/fonts/**", "/favicon.ico");
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable()
                .authorizeRequests()
                .anyRequest().authenticated()

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
                    ResponseBody body = new ResponseBody(200, "Logout Success", null);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                }).permitAll()

                .and().exceptionHandling()
                // authentication failure
                .authenticationEntryPoint((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(401);
                    ResponseBody body = new ResponseBody(401, "Authentication Failure", null);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                })
                // permission denied
                .accessDeniedHandler((request, response, exception) -> {
                    response.setContentType("application/json;charset=utf-8");
                    response.setStatus(403);
                    ResponseBody body = new ResponseBody(403, "Permission Denied", null);
                    response.getWriter().write(new ObjectMapper().writeValueAsString(body));
                });

    }

}
