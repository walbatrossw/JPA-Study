package com.doubles.jpastudy.inheritancemapping.join;

import javax.persistence.*;

//@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 상속 매핑, 조인전략
@DiscriminatorColumn(name = "DTYPE")            // 부모 클래스에 구분 컬럼을 지정
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;    // 이름
    private int price;      // 가격

}
