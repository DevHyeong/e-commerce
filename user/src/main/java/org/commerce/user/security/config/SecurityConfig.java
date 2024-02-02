package org.commerce.user.security.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.commerce.user.security.filter.AuthenticationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final ObjectMapper objectMapper;
    private final AuthenticationConfiguration authenticationConfiguration;
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable();
        http
                .authorizeHttpRequests()
                .requestMatchers("/user/**")
                .permitAll();
                /*.and()
                .authorizeHttpRequests()
                .requestMatchers("/user/**")
                .authenticated();*/
        http.headers().frameOptions().disable();
        return http.build();
    }
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    AuthenticationManager authenticationManagerBean() throws Exception{
        return authenticationConfiguration.getAuthenticationManager();
    }

    @Bean
    public AuthenticationFilter authenticationFilter(Environment env) throws Exception{
        AuthenticationFilter authenticationFilter = new AuthenticationFilter(
                env.getProperty("token.secret-key"),
                objectMapper,
                authenticationManagerBean());
        authenticationFilter.setFilterProcessesUrl("/user/login");
        return authenticationFilter;
    }
}
