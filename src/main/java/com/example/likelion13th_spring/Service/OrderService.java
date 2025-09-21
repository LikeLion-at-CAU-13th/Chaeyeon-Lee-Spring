package com.example.likelion13th_spring.Service;

import com.example.likelion13th_spring.Repository.MemberRepository;
import com.example.likelion13th_spring.Repository.OrdersRepository;
import com.example.likelion13th_spring.Repository.ProductRepository;
import com.example.likelion13th_spring.Repository.ShippingAddressRepository;
import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.Product;
import com.example.likelion13th_spring.domain.ShippingAddress;
import com.example.likelion13th_spring.dto.request.OrderRequestDto;
import com.example.likelion13th_spring.dto.request.OrderUpdateAddressRequestDto;
import com.example.likelion13th_spring.dto.response.OrderResponseDto;
import com.example.likelion13th_spring.enums.DeliverStatus;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {
    private final OrdersRepository ordersRepository;
    private final ShippingAddressRepository shippingAddressRepository;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;

    public OrderResponseDto.Detail createOrder(OrderRequestDto requestDto) {
        Member buyer = memberRepository.findById(requestDto.getMemberId())
                .orElseThrow(() -> new EntityNotFoundException("회원을 찾을 수 없습니다: " + requestDto.getMemberId()));
        ShippingAddress address = shippingAddressRepository.findById(requestDto.getShippingAddressId())
                .orElseThrow(() -> new EntityNotFoundException("배송지를 찾을 수 없습니다: " + requestDto.getShippingAddressId()));

        Orders newOrder = Orders.builder()
                .buyer(buyer)
                .shippingAddress(address)
                .deliverStatus(DeliverStatus.PREPARATION)
                .build();

        int totalPrice = 0;
        List<ProductOrders> productOrdersList = new ArrayList<>();

        for (OrderRequestDto.OrderItemRequestDto itemDto : requestDto.getOrderItems()) {
            Product product = productRepository.findById(itemDto.getProductId())
                    .orElseThrow(() -> new EntityNotFoundException("상품을 찾을 수 없습니다: " + itemDto.getProductId()));

            product.decreaseStock(itemDto.getQuantity());
            totalPrice += product.getPrice() * itemDto.getQuantity();

            ProductOrders productOrder = ProductOrders.builder()
                    .orders(newOrder)
                    .product(product)
                    .quantity(itemDto.getQuantity())
                    .build();
            productOrdersList.add(productOrder);
        }

        buyer.useDeposit(totalPrice);

        newOrder.getProductOrders().addAll(productOrdersList); // Orders에 ProductOrders 연결
        Orders savedOrder = ordersRepository.save(newOrder);

        return OrderResponseDto.Detail.from(savedOrder);
    }


    @Transactional(readOnly = true)
    public List<OrderResponseDto.Detail> findOrdersByMember(Long memberId) {
        // 반환 타입을 List<OrderResponseDto.Detail>로 수정
        return ordersRepository.findByBuyerId(memberId).stream()
                .map(OrderResponseDto.Detail::from) // Detail DTO로 변환
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public OrderResponseDto.Detail findOrderById(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderId));
        return OrderResponseDto.Detail.from(order);
    }

    public void updateShippingAddress(Long orderId, OrderUpdateAddressRequestDto requestDto) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderId));

        if (order.getDeliverStatus() != DeliverStatus.PREPARATION) {
            throw new IllegalStateException("배송 준비중인 주문만 배송지를 수정할 수 있습니다.");
        }

        ShippingAddress newAddress = shippingAddressRepository.findById(requestDto.getNewShippingAddressId())
                .orElseThrow(() -> new EntityNotFoundException("새 배송지를 찾을 수 없습니다: " + requestDto.getNewShippingAddressId()));

        order.setShippingAddress(newAddress);
    }

    public void deleteOrder(Long orderId) {
        Orders order = ordersRepository.findById(orderId)
                .orElseThrow(() -> new EntityNotFoundException("주문을 찾을 수 없습니다: " + orderId));

        if (order.getDeliverStatus() != DeliverStatus.COMPLETED) {
            throw new IllegalStateException("배송 완료된 주문만 삭제할 수 있습니다.");
        }

        order.setDeleted(true);
    }
}
