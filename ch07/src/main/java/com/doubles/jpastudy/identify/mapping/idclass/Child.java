package com.doubles.jpastudy.identify.mapping.idclass;

import javax.persistence.*;

// 자식
@Entity
@IdClass(ChildId.class)
public class Child {

    // 식별관계 기본키, 외래키
    @Id
    @ManyToOne // N:1 매핑
    @JoinColumn(name = "PARENT_ID") // 외래키로 매핑
    public Parent parent;

    // 기본키
    @Id
    @Column(name = "CHILD_ID")
    private String childId;

    private String name;
}
