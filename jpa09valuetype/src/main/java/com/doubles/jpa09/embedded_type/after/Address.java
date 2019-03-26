package com.doubles.jpa09.embedded_type.after;

import javax.persistence.Column;
import javax.persistence.Embeddable;

// 집 주소 클래스
@Embeddable // 값 타입을 정의하는 곳에 표시
public class Address {

    @Column(name = "city") // 매핑할 컬럼 정의 가능
    private String city;
    private String street;
    private String zipcode;

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }
}
