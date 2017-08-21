package com.doubles.jpastudy.inheritancemapping.join;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

//@Entity
@DiscriminatorValue("M")    // 엔티티를 저장할 때 구분 칼럼에 입력할 값을 지정
public class Movie extends Item {

    private String director;    // 감독
    private String actor;       // 배우
}
