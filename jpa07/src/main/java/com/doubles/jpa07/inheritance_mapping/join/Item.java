package com.doubles.jpa07.inheritance_mapping.join;

import javax.persistence.*;

//@Entity
@Inheritance(strategy = InheritanceType.JOINED) // 상속 매핑 - 조인전략
@DiscriminatorColumn(name = "DTYPE") // 부모 클래스에 구분 칼럼 지정
public abstract class Item {

    // 식별자 자동 생성
    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    // 이름
    private String name;

    // 가격
    private int price;

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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

}
