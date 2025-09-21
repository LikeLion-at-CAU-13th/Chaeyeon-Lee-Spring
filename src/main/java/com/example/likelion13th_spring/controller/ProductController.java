package com.example.likelion13th_spring.controller;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.dto.request.ProductRequestDto;
import com.example.likelion13th_spring.dto.response.ProductResponseDto;
import com.example.likelion13th_spring.Service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.likelion13th_spring.dto.request.ProductUpdateRequestDto;
import com.example.likelion13th_spring.dto.request.ProductDeleteRequestDto;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    //상품 등록
    @PostMapping
    public ResponseEntity<ProductResponseDto> createProduct(@RequestBody ProductRequestDto dto) {
        return ResponseEntity.ok(productService.createProduct(dto));
    }

    //전체 상품 조회
    @GetMapping
    public ResponseEntity<List<ProductResponseDto>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts());
    }

    //특정 상품 조회
    @GetMapping("/{id}") //url에 추가
    public ResponseEntity<ProductResponseDto> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(productService.getProductById(id));
    }

    //특정 상품 수정
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDto> updateProduct(@PathVariable Long id,
                                                            @RequestBody ProductUpdateRequestDto dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    //특정 상품 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id,
                                                @RequestBody ProductDeleteRequestDto dto) {
        productService.deleteProduct(id, dto);
        return ResponseEntity.ok("상품이 성공적으로 삭제되었습니다.");
    }
}
