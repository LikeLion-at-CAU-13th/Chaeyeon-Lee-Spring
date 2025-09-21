package com.example.likelion13th_spring.dto.request;

import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Product;
import lombok.Getter;

@Getter
public class ProductRequestDto {
    private String name;
    private Integer price;
    private Integer stock;
    private String description;
    private Long memberId; //판매자인지 확인하기 위함

    public Product toEntity(Member seller) { //DTO를 실제 엔티티 객체로 변환
        return Product.builder()
                .name(this.name)
                .price(this.price)
                .stock(this.stock)
                .description(this.description)
                .seller(seller)
                .build();
    }
}
