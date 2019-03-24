package com.doubles.jpa07.join_table.n_to_one;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 조인테이블 다대일 매핑
// 부모
//@Entity
public class Parent {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    @OneToMany(mappedBy = "parent")
    private List<Child> children = new ArrayList<Child>();

}
