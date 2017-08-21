package com.doubles.jpastudy.nonidentifymapping.idclass;

import java.io.Serializable;

// @IdClass 식별자 클래스 조건 1 - 식별자 클래스는 public
// @IdClass 식별자 클래스 조건 2 - Serializable 인터페이스 구현
public class ParentId implements Serializable{
    // @IdClass 식별자 클래스 조건 3 - 식별자 클래스의 속성명과 엔티티에서 사용하는 식별자의 속성명이 동일해야 함
    private String id1;
    private String id2;

    // @IdClass 식별자 클래스 조건 4 - 기본생성자
    public ParentId() {
    }

    public ParentId(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
    }

    public String getId2() {
        return id2;
    }

    public void setId2(String id2) {
        this.id2 = id2;
    }

    // @IdClass 식별자 클래스 조건 5 - equals(), hashCode() 구현
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ParentId parentId = (ParentId) o;

        if (!id1.equals(parentId.id1)) return false;
        return id2.equals(parentId.id2);
    }

    @Override
    public int hashCode() {
        int result = id1.hashCode();
        result = 31 * result + id2.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "ParentId{" +
                "id1='" + id1 + '\'' +
                ", id2='" + id2 + '\'' +
                '}';
    }
}
