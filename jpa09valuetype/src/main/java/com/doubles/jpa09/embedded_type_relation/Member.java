package com.doubles.jpa09.embedded_type_relation;

import javax.persistence.Embedded;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 임베디드 타입 연관관계
//@Entity
public class Member {

    // 회원 식별자, 이름
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Embedded
    Address address; // 주소

    @Embedded
    PhoneNumber phoneNumber; // 전화번호

}
