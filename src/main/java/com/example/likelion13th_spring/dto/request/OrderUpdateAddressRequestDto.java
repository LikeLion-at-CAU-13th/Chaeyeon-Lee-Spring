package com.example.likelion13th_spring.dto.request;

import lombok.Getter;

//배송정보 수정 위한 DTO
@Getter
public class OrderUpdateAddressRequestDto {
    private Long newShippingAddressId;
}
