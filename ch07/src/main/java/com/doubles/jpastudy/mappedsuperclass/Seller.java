package com.doubles.jpastudy.mappedsuperclass;

import javax.persistence.Entity;

@Entity
public class Seller extends BaseEntity {

    // 아이디, 이름 (공톡속성) 상속

    // 고유 속성만 추가
    private String shopName;
}
