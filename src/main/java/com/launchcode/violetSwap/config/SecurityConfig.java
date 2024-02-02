package com.launchcode.violetSwap.config;

import com.launchcode.violetSwap.security.CustomAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomAuthenticationProvider customAuthProvider;

    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(customAuthProvider);
        return authBuilder.build();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.authorizeHttpRequests( auth -> {
                    auth.requestMatchers(HttpMethod.GET, "/", "/register", "/error").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/register").permitAll();
                    auth.anyRequest().authenticated();
                });

        http.cors(Customizer.withDefaults());

        http.formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/secured")
                        .permitAll());

        http.oauth2Login((oauth2 -> oauth2
                        .loginPage("/login")
                        .permitAll()));

        http.logout((logout) -> logout
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

}
