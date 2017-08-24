package com.doubles.jpastudy.entity.item;

import com.doubles.jpastudy.entity.Category;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   // 단일테이블 전략
@DiscriminatorColumn(name = "DTYPE")    // 구분칼럼
public abstract class Item {

    @Id
    @GeneratedValue
    @Column(name = "ITEM_ID")
    private Long id;

    private String name;        // 상품명
    private int price;          // 가격
    private int stockQuantity;  // 재고수량

    // 카테고리
    // N:N 매핑
    // 주인아님
    @ManyToMany(mappedBy = "items")
    private List<Category> categories = new ArrayList<Category>();

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

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }
}