package com.example.likelion13th_spring.controller;

import com.example.likelion13th_spring.Service.ProductService;
import com.example.likelion13th_spring.config.PrincipalHandler;
import com.example.likelion13th_spring.dto.request.ProductRequestDto;
import com.example.likelion13th_spring.dto.response.ProductResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponseDto.Detail> createProduct(
            @RequestBody ProductRequestDto.Create requestDto
    ) {
        // 1. 현재 로그인한 사용자의 ID를 가져옴 (판매자 ID)
        String sellerName = PrincipalHandler.getUsernameFromPrincipal();

        // 2. 서비스를 호출하여 상품 생성
        ProductResponseDto.Detail createdProduct = productService.createProduct(requestDto, sellerName);

        // 3. 201 Created 상태 코드와 함께 생성된 상품 정보 반환
        return ResponseEntity.status(HttpStatus.CREATED).body(createdProduct);
    }

    @GetMapping("/my")
    public ResponseEntity<List<ProductResponseDto.Detail>> getMyProducts() {
        String sellerName = PrincipalHandler.getUsernameFromPrincipal();
        List<ProductResponseDto.Detail> products = productService.findMyProducts(sellerName);
        return ResponseEntity.ok(products);

    }
}
