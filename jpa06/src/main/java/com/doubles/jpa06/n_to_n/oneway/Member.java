package com.doubles.jpa06.n_to_n.oneway;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 다대다 단방향
// 회원 클래스
@Entity
public class Member {

    @Id
    @Column(name = "MEMBER_ID")
    private String id;

    private String username;

    @ManyToMany // 다대다 매핑
    @JoinTable(name = "MEMBER_PRODUCT", // 연결테이블 지정
            joinColumns = @JoinColumn(name = "MEMBER_ID"), // 현재 방향인 회원과 매핑할 조인 컬럼 정보 지정
            inverseJoinColumns = @JoinColumn(name = "PRODUCT_ID")) // 반대 방향인 상품과 매핑할 조인 컬럼 정보 지정
    private List<Product> products = new ArrayList<Product>();

    public Member() {
    }

    public Member(String id, String username) {
        this.id = id;
        this.username = username;
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
