package com.example.likelion13th_spring.Repository;

import com.example.likelion13th_spring.domain.Member;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


import java.util.List;
import java.util.Optional;

//Sprint Date JPA가 자동으로 인식해서 내부적으로 JPQL 쿼리를 생성해줌!
//delete, save 등의 메서드는 이미 JpaRepository 안에 포함되어있음
public interface MemberRepository extends JpaRepository<Member, Long>{
    Optional<Member> findByName(String name);
    Optional<Member> findByEmail(String email);
    Page<Member> findByAgeGreaterThanEqual(int age, Pageable pageable);
    List<Member> findByNameStartingWith(String prefix);
}