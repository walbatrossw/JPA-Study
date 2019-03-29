package com.doubles.jpa06.n_to_n.new_primary_key;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// 다대다 : 새로운 기본키 사용
//@Entity
public class Product {

    // 상품 식별자
    @Id
    @Column(name = "PRODUCT_ID")
    private String id;

    // 상품명
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
