package org.commerce.apigateway.filter;

import io.jsonwebtoken.Jwts;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import static org.commerce.apigateway.jwt.JwtTokenUtils.extractSubject;
import static org.commerce.apigateway.jwt.JwtTokenUtils.isValid;

@Slf4j
@Component
public class AuthenticationFilter extends AbstractGatewayFilterFactory<AuthenticationFilter.Config> {
    private static final String AUTHORIZATION_ID_HEADER = "X-AUTHORIZATION-ID";
    private final String jwtTokenSecretKey;
    public AuthenticationFilter(Environment env){
        super(Config.class);
        this.jwtTokenSecretKey = env.getProperty("token.secret-key");
    }
    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            if(!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION))
                return onError(exchange, "authorization token is empty", HttpStatus.UNAUTHORIZED);
            String authorizationHeader = request.getHeaders().get(HttpHeaders.AUTHORIZATION).get(0);
            String token = authorizationHeader.replace("Bearer", "");
            if(!isValid(token, jwtTokenSecretKey))
                return onError(exchange, "authorization token " + token + " is not valid"
                        , HttpStatus.UNAUTHORIZED);

            String subject = extractSubject(token, jwtTokenSecretKey);
            request.mutate()
                    .header(AUTHORIZATION_ID_HEADER, subject)
                    .build();

            return chain.filter(exchange);
        };
    }

    private Mono<Void> onError(ServerWebExchange exchange, String message, HttpStatus httpStatus){
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(httpStatus);
        log.error(message);
        return response.setComplete();
    }

    public static class Config{

    }
}
