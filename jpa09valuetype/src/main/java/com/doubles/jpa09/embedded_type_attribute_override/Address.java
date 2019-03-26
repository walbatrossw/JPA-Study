package com.doubles.jpa09.embedded_type_attribute_override;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Embedded;

// 집 주소 클래스
@Embeddable // 값 타입을 정의하는 곳에 표시
public class Address {

    @Column(name = "city") // 매핑할 컬럼 정의 가능
    private String city;
    private String street;

    // 임베디드 타입
    @Embedded
    private Zipcode zipcode;
}
