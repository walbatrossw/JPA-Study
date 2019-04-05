package com.doubles.jpa06.n_to_n.oneway;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

// 다대다 단방향
// 상품 클래스
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
