package com.doubles.jpa06.n_to_n.twoway;


import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 다대다 양방향
@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToMany
    @JoinTable(name = "MEMBER_PRODUCT", // 연결테이블 지정
            joinColumns = @JoinColumn(name = "MEMBER_ID"),  // 현재방향인 회원과 매핑할 조인 칼럼정보 지정
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")) // 반대방행인 상품과 매핑할 조인 컬럼정보 지정
    private List<Product> products = new ArrayList<Product>();

    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public void addProduct(Product product) {
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
