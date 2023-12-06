//package com.example.springdemo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
//import org.springframework.security.config.annotation.web.oauth2.resourceserver.JwtDsl;
//import org.springframework.security.core.userdetails.UserDetailsService;
//
//import com.example.springdemo.util.JwtUtil;
//
//import jakarta.servlet.Filter;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfiguration {
//    @Autowired
//    private UserDetailsService userDetailsService;
//
//    @Autowired
//    private JwtUtil jwtUtil;
//
//    @Override
//    protected void configure(HttpSecurity http) throws Exception {
//        http.csrf().disable()
//            .authorizeRequests()
//                .requestMatchers("/api/auth/**").permitAll()
//                .anyRequest().authenticated()
//            .and()
//            .addFilter(new JwtAuthenticationFilter(authenticationManager(), jwtUtil))
//            .addFilter(new JwtAuthorizationFilter(authenticationManager(), jwtUtil, userDetailsService));
//    }
//
//    // Additional configurations...
//}
