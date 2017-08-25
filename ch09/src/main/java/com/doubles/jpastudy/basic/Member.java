package com.doubles.jpastudy.basic;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 기본값 타입
@Entity
public class Member {

    @Id
    @GeneratedValue
    private Long id;

    // 값 타입
    private String name;

    // 값 타입
    private int age;

    // name, age 속성은 식별자 값X, 생명주기 회원엔티티에 의존
    // 값 타입은 공유X
}
