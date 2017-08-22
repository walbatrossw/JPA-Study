package com.doubles.jpastudy.join.table.onetoone;

import javax.persistence.*;

// 1:1 조인 테이블 매핑
@Entity
public class Child {

    @Id
    @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    private String name;

    // 양방향 매핑
    // 1:1 매핑
    @OneToOne(mappedBy = "child")
    private Parent parent;

}
