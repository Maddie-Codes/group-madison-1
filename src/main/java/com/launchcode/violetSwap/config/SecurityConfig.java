package com.launchcode.violetSwap.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> auth.anyRequest().permitAll()).csrf((csrf) -> csrf.disable());
        return http.build();
//        return http
//                .authorizeHttpRequests( auth -> {
//                    auth.requestMatchers("/", "/register").permitAll();
//                    auth.requestMatchers("/error").permitAll();
////                    auth.requestMatchers("/favicon.ico").permitAll();
//                    auth.requestMatchers(HttpMethod.POST, "/register").permitAll();
////                    auth.anyRequest().authenticated();
//                })
//                .oauth2Login(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//                .build();
    }
}
