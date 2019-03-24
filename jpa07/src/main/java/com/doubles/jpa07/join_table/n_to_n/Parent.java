package com.doubles.jpa07.join_table.n_to_n;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

// 조인테이블 다대다 매핑
//@Entity
public class Parent {

    @Id
    @GeneratedValue
    @Column(name = "PARENT_ID")
    private Long id;

    private String name;

    @ManyToMany
    @JoinTable(name = "PARENT_CHILD",
            joinColumns = @JoinColumn(name = "PARENT_ID"),
            inverseJoinColumns = @JoinColumn(name = "CHILD_ID"))
    private List<Child> children = new ArrayList<Child>();

}
