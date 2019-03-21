package com.doubles.jpa07.super_key_relation.non_identifying.id_class;

import java.io.Serializable;

// 식별자 클래스 반드시 public
public class ParentId implements Serializable { // Serializable 반드시 구현

    // 식별자 클래스의 속성명과 사용하는 식별자의 속성명이 일치해야함

    private String id1; // Parent.id1 매핑

    private String id2; // Parent.id2 매핑

    // 기본생성자 반드시 존재
    public ParentId() {
    }

    public ParentId(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    // equals, hashCode 반드시 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentId parentId = (ParentId) o;

        if (id1 != null ? !id1.equals(parentId.id1) : parentId.id1 != null) return false;
        return id2 != null ? id2.equals(parentId.id2) : parentId.id2 == null;
    }

    @Override
    public int hashCode() {
        int result = id1 != null ? id1.hashCode() : 0;
        result = 31 * result + (id2 != null ? id2.hashCode() : 0);
        return result;
    }
}
