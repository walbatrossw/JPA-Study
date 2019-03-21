package com.doubles.jpa07.inheritance_mapping.single_table;

import javax.persistence.*;

//@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   // 상속 매핑 - 싱글테이블 전략
@DiscriminatorColumn(name = "DTYPE")
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;
    private String price;

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

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }
}
