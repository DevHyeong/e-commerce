package org.commerce.product.application.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.commerce.product.dto.ProductRequest;
import org.commerce.product.dto.ProductResponse;
import org.commerce.product.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping(value = "")
    public ResponseEntity<ProductResponse> createProduct(@RequestBody ProductRequest productRequest){
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(productService.createProduct(productRequest));
    }

    @GetMapping(value = "/{productId}")
    public ResponseEntity<ProductResponse> getProduct(@PathVariable String productId){
        return ResponseEntity.status(HttpStatus.OK)
                .body(productService.getProduct(Long.valueOf(productId)));
    }
}
