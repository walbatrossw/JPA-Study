package com.doubles.jpa07.super_key_relation.identifying.id_class;

import javax.persistence.*;

// 복합키 : 식별관계 매핑 - @IdClass
//@Entity
@IdClass(ChildId.class)
public class Child {

    @Id
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    private Parent parent;

    @Id
    @Column(name = "CHILD_ID")
    private String childId;

    private String name;

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getChildId() {
        return childId;
    }

    public void setChildId(String childId) {
        this.childId = childId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
