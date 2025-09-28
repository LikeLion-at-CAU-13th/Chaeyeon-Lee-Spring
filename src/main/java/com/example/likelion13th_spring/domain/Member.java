package com.example.likelion13th_spring.domain;

import com.example.likelion13th_spring.enums.Role;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.*;

@Entity
@Getter
@NoArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String address;
    private String email;
    private String phoneNumber;
    private int age; // 18주차 과제1에 사용

    @Enumerated(EnumType.STRING)
    private Role role; // 판매자면 SELLER, 구매자면 BUYER

    private Boolean isAdmin; // 관리자 계정 여부

    private Integer deposit; // 현재 계좌 잔액

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL)
    private Set<Product> products = new HashSet<>();

    //24주차 과제 - member와 배송정보 매핑
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<ShippingAddress> shippingAddresses = new ArrayList<>();

    public void chargeDeposit(int money){
        this.deposit += money;
    }
    public void useDeposit(int money) {
        if(this.deposit < money){ //24주차 과제1 - 잔액 부족 처리
            throw new IllegalArgumentException("계좌 잔액이 부족합니다.");
        }
        this.deposit -= money;
    }

    @Builder
    public Member(String name, String address, String email, String phoneNumber, int age,
                  Role role, Boolean isAdmin, Integer deposit) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.age = age;
        this.role = role;
        this.isAdmin = isAdmin;
        this.deposit = deposit;
    }
}
