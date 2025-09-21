package com.example.likelion13th_spring.dto.request;

import com.example.likelion13th_spring.domain.Coupon;
import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.enums.DeliverStatus;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class OrderRequestDto {
    private Long memberId; //주문자 id
    private Long shippingAddressId; //배송정보 id
    private List<OrderItemRequestDto> orderItems;

    @Getter
    public static class OrderItemRequestDto {
        private Long productId;
        private int quantity;


    }
}
