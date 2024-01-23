package org.commerce.order.api.user.service;

import lombok.extern.slf4j.Slf4j;
import org.commerce.order.api.user.model.User;
import org.springframework.context.annotation.Profile;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Profile("default")
@Service
public class UserRestTemplate implements UserApiService{
    private static final String USER_PATH = "/user/";
    private final String apiGatewayUrl;
    private final RestTemplate restTemplate;
    public UserRestTemplate(RestTemplate restTemplate, Environment env){
        this.restTemplate = restTemplate;
        this.apiGatewayUrl = env.getProperty("order-service.api-gateway-url");
    }

    @Override
    public User getUser(Long userId) {
        String url = apiGatewayUrl + USER_PATH + userId;
        log.info(url);
        ResponseEntity<User> result = restTemplate.getForEntity(url, User.class);
        return result.getStatusCode().equals(HttpStatus.OK) ?
                result.getBody() : null;
    }
}
