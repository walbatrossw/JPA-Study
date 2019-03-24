package com.doubles.jpa07.super_key_relation.identifying.embedded_id;

import javax.persistence.*;

// 복합키 : @EmbeddedId
//@Entity
public class Child {

    @EmbeddedId
    private ChildId id;

    @MapsId("parentId") // ChildId.parentId 매핑
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    private String name;
}
