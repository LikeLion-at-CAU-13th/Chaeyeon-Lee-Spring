package com.example.likelion13th_spring.Service;

import com.example.likelion13th_spring.Repository.MemberRepository;
import com.example.likelion13th_spring.Repository.ProductRepository;
import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Product;
import com.example.likelion13th_spring.dto.request.ProductRequestDto;
import com.example.likelion13th_spring.dto.response.OrderResponseDto;
import com.example.likelion13th_spring.dto.response.ProductResponseDto;
import com.example.likelion13th_spring.enums.Role;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final MemberRepository memberRepository;

    //상품 등록 메서드
    @Transactional
    public ProductResponseDto.Detail createProduct(ProductRequestDto.Create requestDto, String sellerName) {

        // 1. 판매자 조회
        Member seller = memberRepository.findByName(sellerName)
                .orElseThrow(() -> new EntityNotFoundException("해당 판매자를 찾을 수 없습니다: " + sellerName));

        // 2. 판매자 권한 확인 (SELLER인지)
        if (!seller.getRole().equals(Role.SELLER)) {
            throw new IllegalArgumentException("판매자(SELLER)만 상품을 등록할 수 있습니다.");
        }

        // 3. DTO를 Entity로 변환
        Product product = requestDto.toEntity(seller);

        // 4. 상품 저장
        Product savedProduct = productRepository.save(product);

        // 5. 저장된 정보를 DTO로 변환하여 반환
        return ProductResponseDto.Detail.from(savedProduct);
    }


    @Transactional(readOnly = true)
    public List<ProductResponseDto.Detail> findMyProducts(String sellerName) {
        return productRepository.findBySellerName(sellerName).stream()
                .map(ProductResponseDto.Detail::from) // Detail DTO로 변환
                .collect(Collectors.toList());
    }
}
