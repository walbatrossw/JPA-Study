package com.doubles.jpa09.embedded_type_attribute_override;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

// 회원 전화번호 클래스
@Embeddable
public class PhoneNumber {

    private String areaCode;
    private String localNumber;

    @ManyToOne  // 다대일 매핑, 엔티티 참조
    private PhoneServiceProvider provider; // 전화 서비스 사업자
}
