package com.doubles.jpa07.super_key_relation.non_identifying.embedded_id;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;

// 복합키 비식별 관계 매핑
@Entity
public class Parent {

    // 좀더 객체지향적인 방법
    @EmbeddedId
    private ParentId parentId;

    private String name;

    public ParentId getParentId() {
        return parentId;
    }

    public void setParentId(ParentId parentId) {
        this.parentId = parentId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
