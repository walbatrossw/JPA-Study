package com.doubles.jpastudy.join.table.manytoone;

import javax.persistence.*;

// N:1 양방향 테이블 조인 매핑
@Entity
public class Child {

    @Id
    @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    private String name;

    @ManyToOne(optional = false)
    @JoinTable(name = "PARENT_CHILD", joinColumns = @JoinColumn(name = "CHILD_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
    private Parent parent;
}
