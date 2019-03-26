package com.doubles.jpa09.embedded_type_attribute_override;

import javax.persistence.*;

// 임베디드 타입 연관관계
//@Entity
public class Member {

    // 회원 식별자, 이름
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Embedded
    Address homeAddress; // 집 주소

    // 속성 재정의 : 임베디드 타입에 정의한 매핑정보를 재정의
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "COMPANY_ZIPCODE"))
    })
    Address CompanyAddress; // 회사 주소

    @Embedded
    PhoneNumber phoneNumber; // 전화번호

}
