package com.doubles.jpastudy.join.table.onetomany;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 1:N 단방향 조인 테이블 매핑
@Entity
public class Parent {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @OneToMany
    @JoinTable(name = "PARENT_CHILD",
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
    private List<Child> children = new ArrayList<Child>();
}
