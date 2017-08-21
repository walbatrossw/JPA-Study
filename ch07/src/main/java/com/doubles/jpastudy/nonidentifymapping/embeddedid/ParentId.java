package com.doubles.jpastudy.nonidentifymapping.embeddedid;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

// @EmbeddedId 식별자 클래스 조건 1 : @Embeddable 어노테이션
//@Embeddable
public class ParentId implements Serializable { // @EmbeddedId 식별자 클래스 조건 2, 3 : 식별자 클래스는 public, Serializable 인터페이스 구현

    // @IdClass와 다르게 @EmbeddedId를 적용한 식별자 클래스는 식별자 클래스에 기본키를 직접매핑
    @Column(name = "PARENT_ID1")
    private String id1;

    @Column(name = "PARENT_ID2")
    private String id2;

    // @EmbeddedId 식별자 클래스 조건 4 : 기본생성자
    public ParentId() {
    }

    public ParentId(String id1, String id2) {
        this.id1 = id1;
        this.id2 = id2;
    }

    // @EmbeddedId 식별자 클래스 조건 5 : equals(), hashCode() 구현
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
}
