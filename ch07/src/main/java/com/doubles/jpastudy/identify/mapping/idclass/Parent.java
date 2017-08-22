package com.doubles.jpastudy.identify.mapping.idclass;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

// 부모
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

    @Override
    public String toString() {
        return "Parent{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
