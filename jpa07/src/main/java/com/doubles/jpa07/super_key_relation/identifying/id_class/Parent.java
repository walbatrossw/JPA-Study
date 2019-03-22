package com.doubles.jpa07.super_key_relation.identifying.id_class;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// 복합키 : 식별관계 매핑 - @IdClass
@Entity
public class Parent {

    @Id
    @Column(name = "PARENT_ID")
    private String id;

    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
