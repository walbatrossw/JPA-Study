package com.doubles.jpastudy.identify.mapping.embeddedid;


import javax.persistence.*;

@Entity
public class Child {

    @EmbeddedId
    private ChildId id;

    // 외래키와 매핑한 연관관계를 기본키에도 매핑하겠다는 의미
    @MapsId("parentId") // ChildId.parentId 매핑
    @ManyToOne
    @JoinColumn(name = "PARENT_ID")
    public Parent parent;

    private String name;

    public ChildId getId() {
        return id;
    }

    public void setId(ChildId id) {
        this.id = id;
    }

    public Parent getParent() {
        return parent;
    }

    public void setParent(Parent parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
