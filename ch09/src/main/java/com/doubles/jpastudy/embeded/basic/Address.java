package com.doubles.jpastudy.embeded.basic;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable // 값타입을 정의하는 곳을 표시
public class Address {

    @Column(name = "CITY")  // 매핑할 컬럼 정의 가능
    private String city;
    private String street;
    private String zipcode;
}
