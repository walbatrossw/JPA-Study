package com.doubles.jpa06.n_to_n.linked_entity;

import java.io.Serializable;
import java.util.Objects;

// 다대다 매핑 한계, 극복 / 연결 엔티티 사용
// 식별자 클래스
public class MemberProductId implements Serializable { // Serializable 구현

    private String member;  // MemberProduct.member 연결
    private String product; // MemberProduct.product 연결

    public MemberProductId() {
    }

    public String getMember() {
        return member;
    }

    public void setMember(String member) {
        this.member = member;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
    }

    // equals, hashCode 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberProductId that = (MemberProductId) o;
        return Objects.equals(member, that.member) &&
                Objects.equals(product, that.product);
    }

    @Override
    public int hashCode() {
        return Objects.hash(member, product);
    }
}
