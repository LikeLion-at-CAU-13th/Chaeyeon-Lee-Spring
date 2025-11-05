package com.example.likelion13th_spring.dto.request;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Product;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ProductRequestDto {

    // 상품 등록 요청을 위한 DTO
    @Getter
    @NoArgsConstructor
    public static class Create {
        private String name;
        private Integer price;
        private Integer stock;
        private String description;

        // DTO를 Product 엔티티로 변환하는 메서드
        public Product toEntity(Member seller) {
            return Product.builder()
                    .name(this.name)
                    .price(this.price)
                    .stock(this.stock)
                    .description(this.description)
                    .seller(seller) // 연관관계 설정
                    .build();
        }
    }
}