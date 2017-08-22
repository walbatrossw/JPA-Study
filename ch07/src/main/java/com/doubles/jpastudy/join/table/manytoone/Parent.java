package com.doubles.jpastudy.join.table.manytoone;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// N:1 양방향 테이블 조인 매핑
@Entity
public class Parent {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "parent")
    private List<Child> children = new ArrayList<Child>();
}
