package com.doubles.jpa06.n_to_n.linked_entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// 다대다 매핑 한계, 극복 / 연결 엔티티 사용
//@Entity
public class Product {

    @Id
    @Column(name = "PRODUCT_ID")
    private String id;

    private String name;

    public Product() {
    }

    public Product(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
