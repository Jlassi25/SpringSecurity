package com.aymen.security.config;

import static com.aymen.security.user.Permissions.ADMIN_CREATE;
import static com.aymen.security.user.Permissions.ADMIN_DELETE;
import static com.aymen.security.user.Permissions.ADMIN_READ;
import static com.aymen.security.user.Permissions.ADMIN_UPDATE;
import static com.aymen.security.user.Permissions.MANAGER_CREATE;
import static com.aymen.security.user.Permissions.MANAGER_DELETE;
import static com.aymen.security.user.Permissions.MANAGER_READ;
import static com.aymen.security.user.Permissions.MANAGER_UPDATE;
import static com.aymen.security.user.Role.ADMIN;
import static com.aymen.security.user.Role.MANAGER;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableMethodSecurity
public class SecurityConfiguration {

    private final JwtAuthenticationFilter jwtAuthFilter;
    private final AuthenticationProvider authenticationProvider;
    

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(req->
                req
                .requestMatchers("/api/v1/auth/**")    
                .permitAll()

                .requestMatchers("/api/v1/managment/**").hasAnyRole(ADMIN.name(),MANAGER.name())  
                .requestMatchers(HttpMethod.GET,"/api/v1/managment/**").hasAnyRole(ADMIN_READ.name(),MANAGER_READ.name())  
                .requestMatchers(HttpMethod.POST,"/api/v1/managment/**").hasAnyRole(ADMIN_CREATE.name(),MANAGER_CREATE.name())  
                .requestMatchers(HttpMethod.PUT,"/api/v1/managment/**").hasAnyRole(ADMIN_UPDATE.name(),MANAGER_UPDATE.name())  
                .requestMatchers(HttpMethod.DELETE,"/api/v1/managment/**").hasAnyRole(ADMIN_DELETE.name(),MANAGER_DELETE.name())  

                //we secure admin EndPoints using Annotation in controller
                // .requestMatchers("/api/v1/admin/**").hasRole(ADMIN.name())  
                // .requestMatchers(HttpMethod.GET,"/api/v1/admin/**").hasAnyRole(ADMIN_READ.name())  
                // .requestMatchers(HttpMethod.POST,"/api/v1/admin/**").hasAnyRole(ADMIN_CREATE.name())  
                // .requestMatchers(HttpMethod.PUT,"/api/v1/admin/**").hasAnyRole(ADMIN_UPDATE.name())  
                // .requestMatchers(HttpMethod.DELETE,"/api/v1/admin/**").hasAnyRole(ADMIN_DELETE.name())  

                .anyRequest()
                .authenticated()
                
            )
            .sessionManagement(session -> 
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .authenticationProvider(authenticationProvider)
            .addFilterBefore(jwtAuthFilter , UsernamePasswordAuthenticationFilter.class);
            
        return http.build();
    }
}
