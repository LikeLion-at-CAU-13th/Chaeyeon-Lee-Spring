package com.example.likelion13th_spring.dto.response;

import com.example.likelion13th_spring.domain.Product;
import lombok.Builder;
import lombok.Getter;

public class ProductResponseDto {
    @Getter
    @Builder
    public static class Detail{
        private Long productId;
        private String productName;
        private Integer productPrice;
        private Integer productStock;
        private String productDescription;
        private Long sellerId;

        public static Detail from(Product product) {
            return Detail.builder()
                    .productId(product.getId())
                    .productName(product.getName())
                    .productPrice(product.getPrice())
                    .productStock(product.getStock())
                    .productDescription(product.getDescription())
                    .sellerId(product.getSeller().getId())
                    .build();
        }

    }
}
