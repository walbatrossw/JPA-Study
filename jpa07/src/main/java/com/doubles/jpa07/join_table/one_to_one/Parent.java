package com.doubles.jpa07.join_table.one_to_one;

import javax.persistence.*;

// 일대일 조인 테이블 매핑
// 부모
//@Entity
public class Parent {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @OneToOne
    @JoinTable(name = "PARENT_CHILD", // 매핑할 조인테이블 이름
            joinColumns = @JoinColumn(name = "PARENT_ID"),  // 현재 엔티티를 참조하는 외래키
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID") // 반대방향 엔티티를 참조하는 외래키
    )
    private Child child;

}
