package com.doubles.jpastudy.embeded.attributeoverride;

import javax.persistence.*;

@Entity
public class Member {

    // 회원 아이디
    @Id
    @GeneratedValue
    private Long id;

    // 회원 이름
    private String username;

    // 집 주소
    @Embedded Address homeAddress;

    // 직장 주소 추가
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "city", column = @Column(name = "COMPANY_CITY")),
            @AttributeOverride(name = "street", column = @Column(name = "COMPANY_STREET")),
            @AttributeOverride(name = "zipcode", column = @Column(name = "COMPANY_ZIPCODE"))
    })
    Address companyAddress;
}
