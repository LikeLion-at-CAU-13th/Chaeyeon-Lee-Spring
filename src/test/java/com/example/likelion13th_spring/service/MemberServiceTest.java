package com.example.likelion13th_spring.service;

import com.example.likelion13th_spring.Service.MemberService;
import com.example.likelion13th_spring.domain.Member;
import com.example.likelion13th_spring.enums.Role;
import com.example.likelion13th_spring.Repository.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MemberServiceTest {

    @Autowired
    private MemberService memberService;

    @Autowired
    private MemberRepository memberRepository;

    @BeforeEach
    void setUp() {
        memberRepository.deleteAll();

        IntStream.rangeClosed(1, 30).forEach(i -> {
            Member member = Member.builder()
                    .name("user" + i)
                    .email("user" + i + "@test.com")
                    .address("서울시 테스트동 " + i + "번지")
                    .phoneNumber("010-1234-56" + String.format("%02d", i))
                    .age(i+5) // (user id + 5)로 user age 설정: 6~35세
                    .deposit(1000 * i)
                    .isAdmin(false)
                    .role(Role.BUYER)
                    .build();

            memberRepository.save(member);
        });
    }

    @Test
    void testGetMembersByPage() {
        Page<Member> page = memberService.getMembersByPage(0, 10);

        assertThat(page.getContent()).hasSize(10);
        assertThat(page.getTotalElements()).isEqualTo(30);
        assertThat(page.getTotalPages()).isEqualTo(3);
        assertThat(page.getContent().get(0).getName()).isEqualTo("user1");
    }

    //18주차 과제1 - 20세 이상 멤버를 오름차순 정렬한 페이징 결과 반환
    @Test
    void testGetAdultMembersSortedByName(){
        Page<Member> page1 = memberService.getAdultMembersSortedByName(0, 10); //첫번째 페이지 조회
        Page<Member> page2 = memberService.getAdultMembersSortedByName(1, 10); //두번째 페이지 조회

        //전체 페이지 내용 검증
        assertThat(page1.getTotalElements()).isEqualTo(16); // 20-35세: 16개의 엔티티 존재해야 함
        assertThat(page1.getTotalPages()).isEqualTo(2); // 페이지 총 2개
        //page1 내용 검증
        assertThat(page1.getContent()).hasSize(10); // page1에는 10개의 엔티티(20-29세)
        assertThat(page1.getContent().get(0).getName()).isEqualTo("user15"); // 첫 페이지의 첫 유저가 user15여야 함
        //page2 내용 검증
        assertThat(page2.getContent()).hasSize(6); // page2에는 6개의 엔티티(30-35세)
        assertThat(page2.getContent().get(0).getName()).isEqualTo("user25"); // 두번째 페이지의 첫 유저가 user25여야 함
    }

    @Test
    void testGetMembersByNamePrefix(){
        List<Member> members = memberService.getMembersByNamePrefix("user1"); //'user1'로 시작하는 멤버들 반환

        assertThat(members).hasSize(11); //user1 + user10 ~ user 19: 11개의 엔티티 존재해야 함
        assertThat(members.get(0).getName()).isEqualTo("user1"); //리스트의 첫 멤버가 user1
        assertThat(members.get(10).getName()).isEqualTo("user19"); //리스트의 열번째 멤버가 user10
    }
}