package com.doubles.jpastudy.embeded.relation;

import javax.persistence.*;

// 임베디드 타입과 연관관계
@Entity
public class RelationEmbeddedMember {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    @Embedded
    private Address address;            // 임베디드 타입 포함

    @Embedded
    private PhoneNumber phoneNumber;    // 임베디드 타입 포함
}
