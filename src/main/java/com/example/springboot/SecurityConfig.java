//package com.example.springboot;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
//import org.springframework.security.web.authentication.www.DigestAuthenticationEntryPoint;
//import org.springframework.security.web.authentication.www.DigestAuthenticationFilter;
//import org.springframework.security.web.authentication.www.NonceExpiredException;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig extends WebSecurityConfigurerAdapter {
//
//    @Bean
//    public CookieSerializer cookieSerializer() {
//        DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
//        cookieSerializer.setSameSite("None"); // Hoặc "Lax" hoặc "Strict" tùy thuộc vào yêu cầu của bạn
//        cookieSerializer.setUseHttpOnlyCookie(true);
//        cookieSerializer.setUseSecureCookie(true); // Yêu cầu HTTPS
//        return cookieSerializer;
//    }
//}
