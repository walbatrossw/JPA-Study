package com.doubles.jpa07.join_table.one_to_one;

import javax.persistence.*;

// 일대일 조인 테이블 매핑
// 자식
//@Entity
public class Child {

    @Id
    @GeneratedValue
    @Column(name = "CHILD_ID")
    private Long id;

    private String name;

    // 양방향 매핑
    @OneToOne(mappedBy = "child")
    private Parent parent;

}
