package com.example.likelion13th_spring.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
public class ShippingAddress extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String recipient; // 수령인

    @Column(nullable = false)
    private String phoneNumber; // 전화번호

    @Column(nullable = false)
    private String streetAddress; // 도로명주소

    @Column
    private String detailAddress; // 상세주소

    @Column(nullable = false)
    private String zipCode; // 우편번호

    @ManyToOne(fetch = FetchType.LAZY) //실제 member 객체 사용하기 전까지는 관련 데이터를 가져오지 않도록 설정
    @JoinColumn(name = "member_id")
    private Member member; //연결된 멤버 객체


}
