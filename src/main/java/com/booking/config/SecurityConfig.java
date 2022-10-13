package com.booking.config;

import com.booking.constant.CommonConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;

import javax.servlet.http.HttpServletRequest;
import javax.sql.DataSource;
import java.util.Collections;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http.cors().configurationSource((HttpServletRequest request) -> {
                        CorsConfiguration config = new CorsConfiguration();
                        config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                        config.setAllowedMethods(Collections.singletonList("*"));
                        config.setAllowCredentials(true);
                        config.setAllowedHeaders(Collections.singletonList("*"));
                        config.setMaxAge(3600L);
                        return config;
                    }
                )
                .and()

                .csrf().disable()

                .authorizeHttpRequests(auth -> auth
                        .mvcMatchers(HttpMethod.POST,"/api/movie").hasRole(CommonConstant.ROLE_ADMIN)
                        .mvcMatchers(HttpMethod.POST,"/api/theaters").hasRole(CommonConstant.ROLE_ADMIN)
                        .mvcMatchers(HttpMethod.POST,"/api/movie-show").hasRole(CommonConstant.ROLE_ADMIN)
                        .mvcMatchers(HttpMethod.POST,"/api/city").hasRole(CommonConstant.ROLE_ADMIN)
                        .mvcMatchers(HttpMethod.DELETE,"/api/pre-booking-lock/**").hasRole(CommonConstant.ROLE_ADMIN)
                        .mvcMatchers(HttpMethod.POST,"/api/payment/**").hasRole(CommonConstant.ROLE_ADMIN)
                        .antMatchers( "/api/**").authenticated()
                        .antMatchers( "/swagger-ui/**").authenticated()
                        .antMatchers("/").permitAll()
                ).httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService(DataSource dataSource){
        return new JdbcUserDetailsManager(dataSource);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}

