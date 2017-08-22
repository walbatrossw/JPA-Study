package com.doubles.jpastudy.join.table.onetoone;

import javax.persistence.*;

// 1:1 조인 테이블 매핑
@Entity
public class Parent {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    // 1:1 매핑
    @OneToOne
    // 매핑할 조인테이블 이름
    @JoinTable(name = "PARENT_CHILD",
            // 현재 엔티티를 참조하는 외래키
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            // 반대방향 엔티티를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
    private Child child;

}
