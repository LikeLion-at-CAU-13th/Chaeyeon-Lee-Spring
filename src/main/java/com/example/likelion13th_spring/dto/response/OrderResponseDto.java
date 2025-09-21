package com.example.likelion13th_spring.dto.response;

import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.ShippingAddress;
import com.example.likelion13th_spring.enums.DeliverStatus;
import lombok.Builder;
import lombok.Getter;

import java.util.List;
import java.util.stream.Collectors;

public class OrderResponseDto {

    @Getter
    @Builder
    public static class Detail {
        private Long orderId;
        private String buyerName;
        private DeliverStatus deliverStatus;
        private ShippingAddressDto shippingAddress;
        private List<OrderItemDto> orderItems;
        private int totalPrice;

        public static Detail from(Orders order) {
            int totalPrice = order.getProductOrders().stream()
                    .mapToInt(po -> po.getProduct().getPrice() * po.getQuantity())
                    .sum();

            return Detail.builder()
                    .orderId(order.getId())
                    .buyerName(order.getBuyer().getName())
                    .deliverStatus(order.getDeliverStatus())
                    .shippingAddress(ShippingAddressDto.from(order.getShippingAddress()))
                    .orderItems(order.getProductOrders().stream()
                            .map(OrderItemDto::from)
                            .collect(Collectors.toList()))
                    .totalPrice(totalPrice)
                    .build();
        }
    }

    @Getter
    @Builder
    private static class OrderItemDto {
        private Long productId;
        private String productName;
        private int price;
        private int quantity;

        private static OrderItemDto from(ProductOrders productOrder) {
            return OrderItemDto.builder()
                    .productId(productOrder.getProduct().getId())
                    .productName(productOrder.getProduct().getName())
                    .price(productOrder.getProduct().getPrice())
                    .quantity(productOrder.getQuantity())
                    .build();
        }
    }

    @Getter
    @Builder
    private static class ShippingAddressDto {
        private String recipient;
        private String phoneNumber;
        private String streetAddress;
        private String detailAddress;
        private String zipCode;

        private static ShippingAddressDto from(com.example.likelion13th_spring.domain.ShippingAddress address) {
            return ShippingAddressDto.builder()
                    .recipient(address.getRecipient())
                    .phoneNumber(address.getPhoneNumber())
                    .streetAddress(address.getStreetAddress())
                    .detailAddress(address.getDetailAddress())
                    .zipCode(address.getZipCode())
                    .build();
        }
    }
}