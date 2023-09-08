package org.commerce.product.application.controller;

import lombok.RequiredArgsConstructor;
import org.commerce.product.dto.ProductRequest;
import org.commerce.product.dto.ProductResponse;
import org.commerce.product.service.ProductReadService;
import org.commerce.product.service.ProductWriteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ProductController {
    private final ProductReadService productReadService;
    private final ProductWriteService productWriteService;

    @PostMapping(value = "/product")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productWriteService.createProduct(productRequest));
    }

    @GetMapping(value = "/product/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String productId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productReadService.getProduct(Long.valueOf(productId)));
    }

    @PostMapping(value = "/products")
    public ResponseEntity<List<ProductResponse>> getProducts(@RequestBody List<Long> ids){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productReadService.getProducts(ids));
    }

}
