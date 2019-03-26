package com.doubles.jpa09.basic_type;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 기본값 타입
//@Entity
public class Member {

    @Id
    @GeneratedValue
    private String id;

    private String name;

    private int age;

    // getter, setter

}
