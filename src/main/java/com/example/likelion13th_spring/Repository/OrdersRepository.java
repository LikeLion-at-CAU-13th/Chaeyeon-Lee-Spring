package com.example.likelion13th_spring.Repository;

import com.example.likelion13th_spring.domain.Orders;
import com.example.likelion13th_spring.domain.ShippingAddress;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrdersRepository extends JpaRepository<Orders, Long> {
    List<Orders> findByBuyerId(Long buyerId);
}

