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

    //Create an auth bean for spring security to use to create and track auth
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authBuilder = http.getSharedObject(AuthenticationManagerBuilder.class);
        authBuilder.authenticationProvider(customAuthProvider);
        return authBuilder.build();
    }

    //Specifies what permissions are required for pages and valid login methods
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        //Allows any user (unauthenticated) to perform GET requests for the "/", "/register", and "/error" paths
        //Allows any user (unauthenticated) to POST to the "/register" endpoint
        //Requires a user to be authenticated to access any other page
        http.authorizeHttpRequests( auth -> {
                    auth.requestMatchers(HttpMethod.GET, "/", "/register", "/error").permitAll();
                    auth.requestMatchers(HttpMethod.POST, "/register").permitAll();
                    auth.anyRequest().authenticated();
                });

        //Spring security magic to keep cors from shooting us in the foot
        http.cors(Customizer.withDefaults());

        //Allow users to authenticate via a login form, use the custom login page for this purpose and on successful auth route to "/secured"
        http.formLogin((form) -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/user/myDetails")
                        .permitAll());

        //Allow user to authenticate via oauth2, use the custom login page for this purpose
        http.oauth2Login((oauth2 -> oauth2
                        .loginPage("/login")
                        .defaultSuccessUrl("/user/myDetails")
                        .permitAll()));

        http.logout((logout) -> logout
                        .permitAll());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {return new BCryptPasswordEncoder();}

}
