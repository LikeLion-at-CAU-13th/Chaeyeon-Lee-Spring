package com.example.likelion13th_spring.Repository;

import com.example.likelion13th_spring.domain.Mapping.ProductOrders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductOrdersRepository extends JpaRepository<ProductOrders, Long> {
}
