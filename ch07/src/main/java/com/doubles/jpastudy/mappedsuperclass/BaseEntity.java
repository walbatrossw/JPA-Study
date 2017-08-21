package com.doubles.jpastudy.mappedsuperclass;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass   // 공통 속성을 상속하기 위해
public abstract class BaseEntity {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

}
