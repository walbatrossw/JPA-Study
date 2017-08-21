package com.doubles.jpastudy.manytomany.bothside;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT", // 연결테이블을 지정
            joinColumns = @JoinColumn(name = "MEMBER_ID"),  // 현재방향인 회원과 매핑할 조인칼럼 정보
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID"))  // 반대방향인 상품과 매핑할 조인칼럼 정보
    private List<Product> products = new ArrayList<Product>();

    private void addProduct(Product product) {
        products.add(product);
        product.getMembers().add(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
