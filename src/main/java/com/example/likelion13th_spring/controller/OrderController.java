package com.example.likelion13th_spring.controller;

import com.example.likelion13th_spring.Service.OrderService;
import com.example.likelion13th_spring.dto.request.OrderRequestDto;
import com.example.likelion13th_spring.dto.request.OrderUpdateAddressRequestDto;
import com.example.likelion13th_spring.dto.response.OrderResponseDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    // 주문 생성
    @PostMapping
    public ResponseEntity<OrderResponseDto.Detail> createOrder(@RequestBody OrderRequestDto requestDto) {
        OrderResponseDto.Detail createdOrder = orderService.createOrder(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdOrder);
    }

    //주문 목록 조회
    @GetMapping
    public ResponseEntity<List<OrderResponseDto.Detail>> getOrdersByMember(@RequestParam Long memberId) {
        // 반환 타입을 List<OrderResponseDto.Detail>로 수정
        List<OrderResponseDto.Detail> orders = orderService.findOrdersByMember(memberId);
        return ResponseEntity.ok(orders);
    }

    //개별 주문 조회
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderResponseDto.Detail> getOrderDetails(@PathVariable Long orderId) {
        OrderResponseDto.Detail orderDetails = orderService.findOrderById(orderId);
        return ResponseEntity.ok(orderDetails);
    }

    //배송정보 수정
    @PatchMapping("/{orderId}/address") // 일부만 수정하므로 PUT보다 PATCH가 더 적합
    public ResponseEntity<String> updateAddress(@PathVariable Long orderId, @RequestBody OrderUpdateAddressRequestDto requestDto) {
        orderService.updateShippingAddress(orderId, requestDto);
        return ResponseEntity.ok("배송지 정보가 성공적으로 수정되었습니다.");
    }

    //개별 주문 삭제
    @DeleteMapping("/{orderId}")
    public ResponseEntity<String> deleteOrder(@PathVariable Long orderId) {
        orderService.deleteOrder(orderId);
        return ResponseEntity.ok("주문이 성공적으로 삭제되었습니다.");
    }

}
