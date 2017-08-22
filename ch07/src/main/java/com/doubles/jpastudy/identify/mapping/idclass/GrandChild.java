package com.doubles.jpastudy.identify.mapping.idclass;

import javax.persistence.*;

@Entity
@IdClass(GrandChild.class)
public class GrandChild {

    // 식별관계 기본키, 외래키
    @Id
    @ManyToOne  // N:1 매핑
    @JoinColumns({
            @JoinColumn(name = "PARENT_ID"),
            @JoinColumn(name = "CHILD_ID")
    })
    private Child child;

    // 기본키
    @Id
    @Column(name = "GRANDCHILD_ID")
    private String id;

    private String name;

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

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
