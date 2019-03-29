package com.doubles.jpa06.n_to_n.new_primary_key;

import javax.persistence.*;

// 다대다 : 새로운 기본키 생성
// 주문 엔티티
//@Entity
public class Orders {

    // 기본키 자동 생성
    @Id
    @GeneratedValue
    @Column(name = "ORDER_ID")
    private Long id;

    // 다대일 매핑 : 회원
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 다대일 매핑 : 상품
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    // 주문수량
    private int orderAmount;

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(int orderAmount) {
        this.orderAmount = orderAmount;
    }
}
