package com.doubles.jpa09.embedded_type.after;

import javax.persistence.*;

// 임베디드 타입 적용 회원 클래스
//@Entity
public class Member {

    // 회원 식별자, 이름
    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @Embedded // 값 타입을 사용하는 곳에 표시
    private Period workPeriod; // 근무 기간

    @Embedded
    private Address homeAddress; // 주소

    public Member(String name, Period workPeriod, Address homeAddress) {
        this.name = name;
        this.workPeriod = workPeriod;
        this.homeAddress = homeAddress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Period getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Period workPeriod) {
        this.workPeriod = workPeriod;
    }

    public Address getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(Address homeAddress) {
        this.homeAddress = homeAddress;
    }
}
