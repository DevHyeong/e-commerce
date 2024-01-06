package org.commerce.order.api.product.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.commerce.order.api.product.model.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Profile("default")
@Service
@RequiredArgsConstructor
public class ProductApiServiceImpl implements ProductApiService{
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private static final String url = "http://localhost:1234";
    @Override
    public List<Product> getProducts(List<Long> ids) {
        URI uri = UriComponentsBuilder.fromUriString(url)
                        .path("/products")
                        .build()
                        .encode()
                        .toUri();
        RequestEntity requestEntity = RequestEntity
                .post(uri)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ids);

        try{
            ResponseEntity<List<Product>> response =  restTemplate.exchange(
                    uri,
                    HttpMethod.POST,
                    requestEntity,
                    new ParameterizedTypeReference<List<Product>>() {}
            );
            return response.getBody();
        }catch(RestClientException e){
            log.error("", e);
            return new ArrayList<>();
        }
    }
}
