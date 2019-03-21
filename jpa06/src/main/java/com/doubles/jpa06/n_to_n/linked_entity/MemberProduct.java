package com.doubles.jpa06.n_to_n.linked_entity;

import javax.persistence.*;

// 다대다 매핑 한계, 극복 / 연결 엔티티 사용
// 연결 엔티티
//@Entity
@IdClass(MemberProductId.class) // 식별자 클래스 지정
public class MemberProduct {

    // 회원 엔티티 : 다대일
    @Id
    @ManyToOne
    @JoinColumn(name = "MEMBER_ID")
    private Member member;

    // 상품 엔티티 : 다대일
    @Id
    @ManyToOne
    @JoinColumn(name = "PRODUCT_ID")
    private Product product;

    // 주문 수량
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
