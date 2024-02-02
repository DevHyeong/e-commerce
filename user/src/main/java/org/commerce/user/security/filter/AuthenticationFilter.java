package org.commerce.user.security.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.commerce.user.dto.LoginRequest;
import org.commerce.user.security.jwt.TokenUtils;
import org.commerce.user.security.model.SecuredUser;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import java.io.IOException;
import java.time.LocalDateTime;


public class AuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final String secretKey;
    private final ObjectMapper objectMapper;

    public AuthenticationFilter (
            String secretKey,
            ObjectMapper objectMapper,
            AuthenticationManager authenticationManager){
        this.secretKey = secretKey;
        this.objectMapper = objectMapper;
        super.setAuthenticationManager(authenticationManager);
    }


    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            LoginRequest loginRequest = objectMapper.readValue(request.getInputStream(), LoginRequest.class);
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {
        SecuredUser securedUser = (SecuredUser) authResult.getPrincipal();
        String token = TokenUtils.generateToken(
                secretKey,
                securedUser.getUserId(),
                LocalDateTime.now().plusDays(1));
        response.setHeader(HttpHeaders.AUTHORIZATION, "Bearer " + token);
    }
}
